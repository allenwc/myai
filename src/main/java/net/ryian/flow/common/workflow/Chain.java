package net.ryian.flow.common.workflow;

import net.ryian.flow.common.workflow.task.Task;

/**
 * @author allenwc
 * @date 2024/5/5 20:46
 */


public class Chain {
    private final Task[] tasks;

    public Chain(Task... tasks) {
        this.tasks = tasks;
    }

    public Object apply(Object initialData) {
        Object result = initialData;
        for (Task task : tasks) {
            try {
                result = task.apply(result);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error in task: " + task.getName(), e);
            }
        }
        return result;
    }

    public static Chain chain(Task... tasks) {
        return new Chain(tasks);
    }
}
