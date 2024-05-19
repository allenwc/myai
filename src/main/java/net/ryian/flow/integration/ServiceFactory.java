package net.ryian.flow.integration;

import java.time.Duration;
import java.util.Properties;

import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

import com.fasterxml.jackson.databind.JsonNode;

import io.minio.MinioClient;
import lombok.Getter;

/**
 * @author allenwc
 * @date 2024/5/16 20:18
 */
@Component
public class ServiceFactory {

    @Getter
    private JsonNode setting;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(setting.get("email_smtp_host").asText());
        sender.setPort(setting.get("email_smtp_port").asInt());
        sender.setDefaultEncoding("UTF-8");
        String from = setting.get("email_user").asText();
        sender.setUsername(from);
        sender.setPassword(setting.get("email_password").asText());
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", setting.get("email_smtp_ssl").asText());
        properties.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(properties);
        return sender;
    }

    public OpenAiChatClient getOpenAiChatClient() {
        ClientHttpRequestFactorySettings requestFactorySettings = new ClientHttpRequestFactorySettings(
            Duration.ZERO , Duration.ZERO , SslBundle.of(null));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(requestFactorySettings);
        Builder clientBuilder = RestClient.builder().requestFactory(requestFactory);
        OpenAiApi openAiApi = new OpenAiApi(setting.get("openai_api_base").asText(), setting.get("openai_api_key").asText(), clientBuilder);
        return new OpenAiChatClient(openAiApi);
    }

    public OpenAiChatClient getMoonshotChatClient() {
        ClientHttpRequestFactorySettings requestFactorySettings = new ClientHttpRequestFactorySettings(
            Duration.ZERO , Duration.ZERO , SslBundle.of(null));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(requestFactorySettings);
        Builder clientBuilder = RestClient.builder().requestFactory(requestFactory);
        OpenAiApi openAiApi = new OpenAiApi(setting.get("moonshot_api_base").asText(), setting.get("moonshot_api_key").asText(), clientBuilder);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
            .withModel("moonshot-v1-8k")
            .withTemperature(0.4F)
            .build();
        return new OpenAiChatClient(openAiApi,options);
    }

    public OllamaChatClient getOllamaChatClient() {
        ClientHttpRequestFactorySettings requestFactorySettings = new ClientHttpRequestFactorySettings(
            Duration.ZERO , Duration.ZERO , SslBundle.of(null));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(requestFactorySettings);
        Builder clientBuilder = RestClient.builder().requestFactory(requestFactory);
        OllamaApi ollamaApi = new OllamaApi("http://localhost:11434", clientBuilder);
        OllamaOptions options = OllamaOptions.create();
        options.setModel("llama2-chinese:13b");
        return new OllamaChatClient(ollamaApi);
    }

    public MinioClient getMinioClient() {
        return MinioClient.builder()
            .endpoint(setting.get("minio_base_url").asText())
            .credentials(setting.get("minio_key").asText(), setting.get("minio_secret").asText())
            .build();
    }

    public void updateSetting(JsonNode setting) {
        this.setting = setting;
    }
}
