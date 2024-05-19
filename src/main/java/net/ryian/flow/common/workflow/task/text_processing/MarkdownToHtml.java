package net.ryian.flow.common.workflow.task.text_processing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/15 09:23
 */
@Component
public class MarkdownToHtml {

    public Object execute(TaskParams taskParams) {
        Workflow workflow = new Workflow(taskParams.getWorkflowData());
        String nodeId = taskParams.getNodeId();
        Object markdownObject = workflow.getNodeFieldValue(nodeId, "markdown");
        List<String> markdowns;
        if (markdownObject instanceof List) {
            markdowns = (List<String>) markdownObject;
        } else {
            markdowns = new ArrayList<>();
            markdowns.add((String) markdownObject);
        }
        List<String> htmls = new ArrayList<>();
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        for (String markdown: markdowns) {
            Document document = parser.parse(markdown);
            String html = renderer.render(document);
            htmls.add(html);
        }
        Object finalOutput;
        if (markdownObject instanceof List) {
            finalOutput = htmls;
        } else {
            finalOutput = htmls.get(0);
        }
        workflow.updateNodeFieldValue(nodeId, "html", finalOutput);
        return workflow.getData();
    }

}
