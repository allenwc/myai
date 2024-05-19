package net.ryian.flow.common.workflow.task;

import java.util.function.Function;

/**
 * @author allenwc
 * @date 2024/5/5 20:47
 */
public interface Task extends Function<Object, Object> {

    String getName();

}
