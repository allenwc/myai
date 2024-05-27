package net.ryian.flow.common.workflow.task.tools.meta;

import lombok.Builder;
import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/26 21:42
 */
@Builder
@Data
public class SearchResult {
    String name;
    String url;
    String snippet;
}
