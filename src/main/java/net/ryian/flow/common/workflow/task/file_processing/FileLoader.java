package net.ryian.flow.common.workflow.task.file_processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONArray;

import net.ryian.flow.common.utils.FileUtil;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/14 20:18
 */
@Component
public class FileLoader {

    FileUtil fileUtil;

    @Autowired
    public FileLoader(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        JSONArray files = (JSONArray) workflow.getNodeFieldValue(nodeId, "files");
        String result = fileUtil.getFileContent(files);
        workflow.updateNodeFieldValue(nodeId,"output",result);
        return workflow.getData();
    }

}
