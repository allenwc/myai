package net.ryian.flow.common.workflow.task.control_flows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/14 20:16
 */
@Component
public class JsonProcess {

    @SuppressWarnings("unused")
    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        Object rawInput = workflow.getNodeFieldValue(nodeId, "input");

        List<Object> inputList;
        if (rawInput instanceof String) {
            inputList = new ArrayList<>();
            inputList.add(tryParseJson((String) rawInput));
        } else if (rawInput instanceof JSONObject) {
            inputList = new ArrayList<>();
            inputList.add(rawInput);
        } else {
            inputList = (List<Object>) rawInput;
        }

        List<Object> parsedInputData = new ArrayList<>();
        for (Object inputItem : inputList) {
            if (!(inputItem instanceof String stringInputItem)) {
                parsedInputData.add(inputItem);
            } else {
                parsedInputData.add(tryParseJson(stringInputItem));
            }
        }

        String processMode = (String) workflow.getNodeFieldValue(nodeId, "process_mode");
        Object key = workflow.getNodeFieldValue(nodeId, "key");
        Object defaultValue = workflow.getNodeFieldValue(nodeId, "default_value");
        List<String> keys = (List<String>) workflow.getNodeFieldValue(nodeId, "keys", new ArrayList<>());

        boolean inputFieldsHasList = (rawInput instanceof List) || (key instanceof List);
        List<Object> output = new ArrayList<>();
        Map<String, List<Object>> outputKeys = new HashMap<>();
        for (String k : keys) {
            outputKeys.put(k, new ArrayList<>());
        }

        if (!(key instanceof List)) {
            key = Arrays.asList(key);
        }

        if (((List) key).size() < parsedInputData.size()) {
            key = Collections.nCopies(parsedInputData.size(), ((List) key).get(0));
        } else if (((List) key).size() > parsedInputData.size()) {
            parsedInputData = Collections.nCopies(((List) key).size(), parsedInputData.get(0));
        }

        if (processMode.equals("get_value")) {
            for (int i = 0; i < parsedInputData.size(); i++) {
                output.add(((JSONObject) parsedInputData.get(i)).getString((String) ((List) key).get(i)));
            }
        } else if (processMode.equals("get_multiple_values")) {
            for (Object inputItem : parsedInputData) {
                for (String k : keys) {
                    outputKeys.get(k).add(((JSONObject) inputItem).getString(k));
                }
            }
        } else if (processMode.equals("list_values")) {
            for (Object inputItem : parsedInputData) {
                output.add(new ArrayList<>(((JSONObject) inputItem).values()));
            }
        } else if (processMode.equals("list_keys")) {
            for (Object inputItem : parsedInputData) {
                output.add(new ArrayList<>(((JSONObject) inputItem).keySet()));
            }
        }

        if (!processMode.equals("get_multiple_values")) {
            if (!inputFieldsHasList) {
                output = Arrays.asList(output.get(0));
            }
            workflow.updateNodeFieldValue(nodeId, "output", output);
        } else {
            for (String k : keys) {
                if (!inputFieldsHasList) {
                    outputKeys.put(k, Arrays.asList(outputKeys.get(k).get(0)));
                }
                workflow.updateNodeFieldValue(nodeId, "output-" + k, outputKeys.get(k).get(0));
            }
        }
        return workflow.getData();
    }

    private JSONObject tryParseJson(String input) throws JSONException {
        try {
            return JSONObject.parseObject(input);
        } catch (JSONException e) {
            Pattern pattern = Pattern.compile("```.*?\n(.*?)\n```", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(input);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid JSON format");
            }
            return JSONObject.parse(matcher.group(1));
        }
    }

}
