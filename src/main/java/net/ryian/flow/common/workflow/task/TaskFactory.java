package net.ryian.flow.common.workflow.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.base.CaseFormat;

import net.ryian.flow.model.vo.UserSettingVO;
import net.ryian.flow.model.vo.UserSettingVOConvertor;
import net.ryian.flow.service.UserSettingService;

/**
 * @author allenwc
 * @date 2024/5/5 21:20
 */
@Component
public class TaskFactory {

    private final ApplicationContext context;
    private final UserSettingService userSettingService;

    @Autowired
    public TaskFactory(ApplicationContext context,UserSettingService userSettingService) {
        this.context = context;
        this.userSettingService = userSettingService;
    }

    public Task createTask(String nodeId,String taskName) throws NoSuchMethodException, ClassNotFoundException {
        UserSettingVO userSetting = UserSettingVOConvertor.INSTANCE.toVO(userSettingService.get());

        String[] parts = taskName.split("\\.");
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, parts[parts.length - 1]);
        className = "net.ryian.flow.common.workflow.task." + parts[0]+"."+className;

        String methodName = "execute";

        Object bean = this.context.getBean(Class.forName(className));

        UnaryOperator<Object> function = input -> {

            try {
                Method method = bean.getClass().getMethod(methodName,TaskParams.class);
                TaskParams params = TaskParams.builder().nodeId(nodeId).userSetting(userSetting).workflowData((String)input).build();
                return method.invoke(bean, params);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };

        return new AbstractTask(taskName, function);
    }
}
