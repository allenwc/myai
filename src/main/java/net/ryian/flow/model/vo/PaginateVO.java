package net.ryian.flow.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 12:07
 */
@Data
public class PaginateVO {

    long page;
    @JsonProperty("page_size")
    int pageSize = 10;
    long total;
}
