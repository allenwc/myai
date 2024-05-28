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

import lombok.extern.slf4j.Slf4j;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;
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

        Map<CompletableFuture<List<String>>,Integer> futures = new HashMap();
        for (int i = 0; i < searchTextList.size(); i++) {
            String searchText = searchTextList.get(i);
            int index = i;
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
                try{
                    semaphore.acquire();
                    return search(searchEngine, searchText,count,outputType);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                return null;
            }, taskExecutor);
            futures.put(future,index);
        }

        List<String>[] searchResults = new List[searchTextList.size()];
        for (CompletableFuture<List<String>> future : futures.keySet()) {
            int index = futures.get(future);
            try {
                searchResults[index] = future.get();
            } catch (Exception e) {
                System.err.println("Generated an exception: " + e);
                System.err.println("SearchText: " + searchTextList.get(index));
            }
        }

        if(inputSearchText instanceof List<?>) {
            workflow.updateNodeFieldValue(nodeId, "output", searchResults[0]);
        } else {
            workflow.updateNodeFieldValue(nodeId, "output", searchResults);
        }

        return workflow.getData();
    }

    private List<String> search(String type,String searchText, int count,String outputType) {
        if("bing".equals(type)) {
            return bingSearch(serviceFactory.getImageSearchClient(),searchText,count,outputType);
        }
        return null;
    }

    private List<String> bingSearch(ImageSearchClient client,String searchText,int count,String outputType) {
        log.info("Searching for: " + searchText);
        Images imageResults = client.images().search(searchText);

        List<String> results = new ArrayList<>();

        if (imageResults != null && !imageResults.value().isEmpty()) {
            for(int i = 0;i < Math.min(imageResults.value().size(),count);i++) {
                ImageObject image = imageResults.value().get(i);
                if ("text".equals(outputType)) {
                    results.add(image.url());
                } else if ("markdown".equals(outputType)) {
                    results.add(String.format("![%s](%s)",image.name(),image.url()));
                }
            }
        } else {
            System.out.println("Didn't see any Image data..");
        }
        return results;
    }

}
