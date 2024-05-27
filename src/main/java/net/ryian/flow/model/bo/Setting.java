package net.ryian.flow.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/25 15:07
 */
@Data
public class Setting {

    @JsonProperty("openai_api_type")
    private String openaiApiType;

    @JsonProperty("openai_api_key")
    private String openaiApiKey;

    @JsonProperty("openai_api_base")
    private String openaiApiBase;

    @JsonProperty("azure_api_key")
    private String azureApiKey;

    @JsonProperty("azure_endpoint")
    private String azureEndpoint;

    @JsonProperty("azure_gpt_35_deployment_id")
    private String azureGpt35DeploymentId;

    @JsonProperty("azure_gpt_4_deployment_id")
    private String azureGpt4DeploymentId;

    @JsonProperty("azure_gpt_4v_deployment_id")
    private String azureGpt4vDeploymentId;

    @JsonProperty("openai_embedding_engine")
    private String openaiEmbeddingEngine;

    @JsonProperty("moonshot_api_base")
    private String moonshotApiBase;

    @JsonProperty("moonshot_api_key")
    private String moonshotApiKey;

    @JsonProperty("ollama_api_base")
    private String ollamaApiBase;

    @JsonProperty("output_folder")
    private String outputFolder;

    @JsonProperty("email_user")
    private String emailUser;

    @JsonProperty("email_password")
    private String emailPassword;

    @JsonProperty("email_smtp_host")
    private String emailSmtpHost;

    @JsonProperty("email_smtp_port")
    private int emailSmtpPort;

    @JsonProperty("email_smtp_ssl")
    private Boolean emailSmtpSsl;

    @JsonProperty("pexels_api_key")
    private String pexelsApiKey;

    @JsonProperty("stable_diffusion_base_url")
    private String stableDiffusionBaseUrl;

    @JsonProperty("use_system_proxy")
    private boolean useSystemProxy;

    @JsonProperty("oss_type")
    private String ossType;

    @JsonProperty("minio_base_url")
    private String minioBaseUrl;

    @JsonProperty("minio_key")
    private String minioKey;

    @JsonProperty("minio_secret")
    private String minioSecret;

    @JsonProperty("minio_bucket")
    private String minioBucket;

    @JsonProperty("bing_api_key")
    private String bingApiKey;

}
