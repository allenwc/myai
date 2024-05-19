package net.ryian.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.ryian.flow.common.workflow.Chain;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.Task;
import net.ryian.flow.common.workflow.task.TaskFactory;
import net.ryian.flow.model.po.WorkflowRunRecordPO;
import net.ryian.flow.service.WorkflowEngineService;
import net.ryian.flow.service.WorkflowRunRecordService;

/**
 * @author allenwc
 * @date 2024/5/5 08:02
 */
@Slf4j
@Service
public class WorkflowEngineServiceImpl implements WorkflowEngineService {

    TaskFactory taskFactory;

    WorkflowRunRecordService workflowRunRecordService;

    @Autowired
    public WorkflowEngineServiceImpl(TaskFactory taskFactory,WorkflowRunRecordService workflowRunRecordService) {
        this.taskFactory = taskFactory;
        this.workflowRunRecordService = workflowRunRecordService;
    }

    @Async
    @Override
    public void run(String rid) {
        log.info("start run workflow: {}", rid);
        WorkflowRunRecordPO workflowRunRecordPO = workflowRunRecordService.getByRid(rid);
        Workflow workflow = new Workflow(workflowRunRecordPO.getData());
        Task[] tasks = workflow.getSortedTaskOrder().stream().map(taskNode -> {
            log.info(taskNode.toString());
            try {
                return taskFactory.createTask(taskNode.getString("node_id"),taskNode.getString("task_name"));
            } catch (NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Task[]::new);

        String data = (String)Chain.chain(tasks).apply(workflow.getData());
        log.info("finish run workflow: {}", rid);
        workflowRunRecordService.finishRun(rid,data);
    }
}
