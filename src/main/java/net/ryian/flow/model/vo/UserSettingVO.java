package net.ryian.flow.model.vo;


import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/16 09:07
 */
@Data
public class UserSettingVO {

    Long id;
    JsonNode data;

}
