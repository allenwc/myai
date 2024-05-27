package net.ryian.flow.common.workflow.task.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.microsoft.bing.websearch.WebSearchClient;
import com.microsoft.bing.websearch.models.SearchResponse;
import com.microsoft.bing.websearch.models.WebPage;

import lombok.extern.slf4j.Slf4j;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;
import net.ryian.flow.common.workflow.task.tools.meta.SearchResult;
import net.ryian.flow.integration.ServiceFactory;

/**
 * @author allenwc
 * @date 2024/5/22 22:15
 */
@Component
@Slf4j
public class TextSearch {

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
        boolean isCombineResultInText = (Boolean)workflow.getNodeFieldValue(nodeId,"combine_result_in_text");

        List<String> searchTextList = new ArrayList<>();
        if(inputSearchText instanceof List) {
            searchTextList = (List<String>) inputSearchText;
        } else {
            searchTextList.add((String) inputSearchText);
        }

        Map<CompletableFuture<List<SearchResult>>,Integer> futures = new HashMap();
        for (int i = 0; i < searchTextList.size(); i++) {
            String searchText = searchTextList.get(i);
            int index = i;
            CompletableFuture<List<SearchResult>> future = CompletableFuture.supplyAsync(() -> {
                try{
                    semaphore.acquire();
                    return search(searchEngine, searchText,count,index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                return null;
            }, taskExecutor);
            futures.put(future,index);
        }

        List<SearchResult>[] searchResults = new List[searchTextList.size()];
        for (CompletableFuture<List<SearchResult>> future : futures.keySet()) {
            int index = futures.get(future);
            try {
                searchResults[index] = future.get();
            } catch (Exception e) {
                System.err.println("Generated an exception: " + e);
                System.err.println("SearchText: " + searchTextList.get(index));
            }
        }

        if(inputSearchText instanceof List<?>) {
            if(isCombineResultInText) {
                List<String> titles = new ArrayList<>();
                List<String> urls = new ArrayList<>();
                List<String> snippets = new ArrayList<>();
                for(List<SearchResult> results : searchResults) {
                    titles.add(String.join("\n",results.stream().map(SearchResult::getName).collect(Collectors.toList())));
                    urls.add(String.join("\n",results.stream().map(SearchResult::getUrl).collect(Collectors.toList())));
                    snippets.add(String.join("\n",results.stream().map(SearchResult::getSnippet).collect(Collectors.toList())));
                }
                workflow.updateNodeFieldValue(nodeId, "output_page_title",titles);
                workflow.updateNodeFieldValue(nodeId, "output_page_url" , urls);
                workflow.updateNodeFieldValue(nodeId, "output_page_snippet", snippets);
            } else {
                //TODO
            }
        } else {
            List<SearchResult> results = searchResults[0];
            if (isCombineResultInText) {
                workflow.updateNodeFieldValue(nodeId, "output_page_title",String.join("\n",results.stream().map(SearchResult::getName).collect(Collectors.toList())));
                workflow.updateNodeFieldValue(nodeId, "output_page_url" , String.join("\n",results.stream().map(SearchResult::getUrl).collect(Collectors.toList())));
                workflow.updateNodeFieldValue(nodeId, "output_page_snippet", String.join("\n",results.stream().map(SearchResult::getSnippet).collect(Collectors.toList())));
            } else {
                workflow.updateNodeFieldValue(nodeId, "output_page_title",results.stream().map(SearchResult::getName).toArray());
                workflow.updateNodeFieldValue(nodeId, "output_page_url" , results.stream().map(SearchResult::getUrl).toArray());
                workflow.updateNodeFieldValue(nodeId, "output_page_snippet", results.stream().map(SearchResult::getSnippet).toArray());
            }
        }

        return workflow.getData();
    }

    private List<SearchResult> search(String type,String searchText, int count,int index) {
        if("bing".equals(type)) {
            return bingSearch(serviceFactory.getWebSearchClient(),searchText,count);
        }
        return null;
    }

    private List<SearchResult> bingSearch(WebSearchClient client,String searchText,int count) {
        log.info("Searching for: " + searchText);
        SearchResponse webData = client.webs().search(searchText);

        List<SearchResult> results = new ArrayList<>();

        //WebPages
        if (webData != null && webData.webPages() != null && webData.webPages().value() != null &&
            webData.webPages().value().size() > 0) {
            for(int i = 0;i < Math.min(webData.webPages().value().size(),count);i++) {
                WebPage page = webData.webPages().value().get(i);
                if (page != null) {
                    results.add(SearchResult.builder().name(page.name()).url(page.url()).snippet(page.snippet()).build());
                }
            }
        } else {
            System.out.println("Didn't see any Web data..");
        }
        return results;
    }

}
