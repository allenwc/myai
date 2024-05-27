package net.ryian.flow.common.workflow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.SneakyThrows;
import net.ryian.flow.common.workflow.task.Task;
import net.ryian.flow.common.workflow.task.TaskFactory;

/**
 * @author allenwc
 * @date 2024/5/5 10:48
 */
@SpringBootTest
public class WorkflowTest {

    private TaskFactory taskFactory;

    @Autowired
    public WorkflowTest(TaskFactory TaskFactory) {
        this.taskFactory = TaskFactory;
    }

    public static String readResourceFileAsString(String fileName) throws IOException {
        return Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).getPath()))
            .collect(Collectors.joining("\n"));
    }

    @Test
    @SneakyThrows
    void test() {
        String fileName = "simple.json";
        String json = readResourceFileAsString(fileName);
        Workflow workflow = new Workflow(json);
        assertEquals(8, workflow.getNodes().size());

        Task[] tasks = workflow.getSortedTaskOrder().stream().map(taskNode -> {
            System.out.println(taskNode);
            try {
                return taskFactory.createTask(taskNode.getString("node_id"),taskNode.getString("task_name"));
            } catch (NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Task[]::new);
//        workflow.updateNodeFieldValue("596290f9-5c27-4447-be26-0c7e2f6d89b3","text",readResourceFileAsString("37f97d57-f07e-45c0-8c36-38c089926b58"));
//        workflow.updateNodeFieldValue("46e0b2ee-ebf1-4efe-b10d-91171e103efb","text","allenwc@126.com");
//        System.out.println(Chain.chain(tasks).apply(workflow.getData()));
    }

}
