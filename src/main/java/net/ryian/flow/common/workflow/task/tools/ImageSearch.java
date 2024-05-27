package net.ryian.flow.common.workflow.task.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.microsoft.bing.imagesearch.ImageSearchClient;
import com.microsoft.bing.imagesearch.models.ImageObject;
import com.microsoft.bing.imagesearch.models.Images;
import com.microsoft.bing.websearch.WebSearchClient;
import com.microsoft.bing.websearch.models.SearchResponse;
import com.microsoft.bing.websearch.models.WebPage;

import lombok.extern.slf4j.Slf4j;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;
import net.ryian.flow.common.workflow.task.tools.meta.ImageResult;
import net.ryian.flow.common.workflow.task.tools.meta.SearchResult;
import net.ryian.flow.integration.ServiceFactory;

/**
 * @author allenwc
 * @date 2024/5/23 20:12
 */
@Component
@Slf4j
public class ImageSearch {

    @Autowired
    private ServiceFactory serviceFactory;
    @Autowired
    private TaskExecutor taskExecutor;
    private Semaphore semaphore = new Semaphore(1);

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        Object inputSearchText = workflow.getNodeFieldValue(nodeId, "search_text");
        String searchEngine = (String)workflow.getNodeFieldValue(nodeId,"search_engine");
        Integer count = (Integer)workflow.getNodeFieldValue(nodeId,"count");
        String outputType = (String)workflow.getNodeFieldValue(nodeId,"output_type");


        List<String> searchTextList = new ArrayList<>();
        if(inputSearchText instanceof List) {
            searchTextList = (List<String>) inputSearchText;
        } else {
            searchTextList.add((String) inputSearchText);
        }

        Map<CompletableFuture<List<ImageResult>>,Integer> futures = new HashMap();
        for (int i = 0; i < searchTextList.size(); i++) {
            String searchText = searchTextList.get(i);
            int index = i;
            CompletableFuture<List<ImageResult>> future = CompletableFuture.supplyAsync(() -> {
                try{
                    semaphore.acquire();
                    return search(searchEngine, searchText,count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                return null;
            }, taskExecutor);
            futures.put(future,index);
        }

        List<ImageResult>[] searchResults = new List[searchTextList.size()];
        for (CompletableFuture<List<ImageResult>> future : futures.keySet()) {
            int index = futures.get(future);
            try {
                searchResults[index] = future.get();
            } catch (Exception e) {
                System.err.println("Generated an exception: " + e);
                System.err.println("SearchText: " + searchTextList.get(index));
            }
        }


        if("text".equals(outputType)) {

        } else if ("markdown".equals(outputType)) {

        }

        if(inputSearchText instanceof List<?>) {
            workflow.updateNodeFieldValue(nodeId, "output", new ArrayList<>());
        } else {
            workflow.updateNodeFieldValue(nodeId, "output", new ArrayList<>());
        }

        return workflow.getData();
    }

    private List<ImageResult> search(String type,String searchText, int count) {
        if("bing".equals(type)) {
            return bingSearch(serviceFactory.getImageSerchClient(),searchText,count);
        }
        return null;
    }

    private List<ImageResult> bingSearch(ImageSearchClient client,String searchText,int count) {
        log.info("Searching for: " + searchText);
        Images imageResults = client.images().search(searchText);

        List<ImageResult> results = new ArrayList<>();

        if (imageResults != null && imageResults.value().size() > 0) {
            for(int i = 0;i < Math.min(imageResults.value().size(),count);i++) {
                ImageObject image = imageResults.value().get(i);
                if (image != null) {
                    results.add(ImageResult.builder().name(image.name()).url(image.url()).build());
                }
            }
        } else {
            System.out.println("Didn't see any Web data..");
        }
        return results;
    }

}
