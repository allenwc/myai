package net.ryian.flow.common.workflow.task;

import java.util.function.UnaryOperator;

/**
 * @author allenwc
 * @date 2024/5/5 20:54
 */
public class AbstractTask implements Task {
    private final String name;
    private final UnaryOperator<Object> function;

    public AbstractTask(String name, UnaryOperator<Object> function) {
        this.name = name;
        this.function = function;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object apply(Object input) {
        return function.apply(input);
    }
}
