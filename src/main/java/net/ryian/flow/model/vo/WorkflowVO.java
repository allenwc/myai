package net.ryian.flow.model.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;

import lombok.Data;
import net.ryian.flow.common.utils.UUIDUtil;

/**
 * @author allenwc
 * @date 2024/5/3 11:53
 */
@Data
public class WorkflowVO {

    String wid;
    String status;
    String title;
    String brief;
    String language;
    String version;
    @JsonProperty("is_fast_access")
    boolean isFastAccess;
    @JsonProperty("create_time")
    String createTime;
    @JsonProperty("update_time")
    String updateTime;
    @JsonProperty("tool_call_data")
    String toolCallData;
    JsonNode data;
    List tags;
    List images;
    @JsonProperty("browser_settings")
    String browserSettings;
    @JsonProperty("use_in_wechat")
    boolean useInWechat;
    @JsonProperty("wechat_settings")
    String wechatSettings;

    public void fill() {
        String wid = this.getWid();
        if(wid == null) {
            wid = UUIDUtil.gen();
            this.setWid(UUIDUtil.gen());
        }
        if(this.getTitle() == null) {
            this.setTitle("新流程"+wid.substring(0,8));
        }
        if(this.getBrief() == null) {
            this.setBrief("");
        }
        if(this.getImages() == null) {
            this.setImages(Lists.newArrayList());
        }
        if(this.getTags() == null) {
            this.setTags(Lists.newArrayList());
        }
        ObjectNode data = (ObjectNode) this.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        if(data == null) {
            data = new ObjectMapper().createObjectNode();
        }
        if(!data.has("nodes")) {
            data.set("nodes",objectMapper.createArrayNode());
        }
        if(!data.has("edges")) {
            data.set("edges",objectMapper.createArrayNode());
        }
        this.setData(data);
    }

}
