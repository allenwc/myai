package net.ryian.flow.common.workflow.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ryian.flow.model.vo.UserSettingVO;

/**
 * @author allenwc
 * @date 2024/5/16 09:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskParams {

    String nodeId;
    String workflowData;
    UserSettingVO userSetting;

}
