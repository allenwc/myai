package net.ryian.flow.common.workflow.task.text_processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/14 20:10
 */
@Component
public class TemplateCompose {

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        String template = (String)workflow.getNodeFieldValue(nodeId, "template");
        Set<String> fields = workflow.getNodeFields(nodeId);

        boolean fieldsHasList = false;
        int listLength = 1;
        for (String field : fields) {
            if (field.equals("output")) {
                continue;
            }
            Object fieldValue = workflow.getNodeFieldValue(nodeId, field);
            if (!(fieldValue instanceof List)) {
                continue;
            }
            fieldsHasList = true;
            if (listLength == 1) {
                listLength = ((List) fieldValue).size();
            } else if (listLength != ((List) fieldValue).size()) {
                throw new IllegalArgumentException("Input fields have different list length");
            }
        }

        Map<String, List<String>> fieldsValues = new HashMap<>();
        fieldsValues.put("output", new ArrayList<>());
        for (String field : fields) {
            if (field.equals("output")) {
                continue;
            }
            Object fieldValue = workflow.getNodeFieldValue(nodeId, field);
            if (!(fieldValue instanceof List)) {
                fieldsValues.put(field, Collections.nCopies(listLength, fieldValue.toString()));
            } else {
                fieldsValues.put(field, (List<String>) fieldValue);
            }
        }

        for (int index = 0; index < fieldsValues.get("template").size(); index++) {
            template = fieldsValues.get("template").get(index);
            for (String field : fields) {
                if (field.equals("output")) {
                    continue;
                }
                template = template.replace("{{" + field + "}}", fieldsValues.get(field).get(index).toString());
            }
            fieldsValues.get("output").add(template);
        }

        if (!fieldsHasList) {
            fieldsValues.put("template", Collections.singletonList(fieldsValues.get("template").get(0)));
        }
        workflow.updateNodeFieldValue(nodeId, "template", fieldsValues.get("template"));

        if (!fieldsHasList) {
            fieldsValues.put("output", Collections.singletonList(fieldsValues.get("output").get(0)));
        }
        workflow.updateNodeFieldValue(nodeId, "output", fieldsValues.get("output"));

        return workflow.getData();
    }

}
