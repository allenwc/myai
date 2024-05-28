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

import com.microsoft.bing.imagesearch.ImageSearchClient;
import com.microsoft.bing.imagesearch.implementation.ImageSearchClientImpl;
import com.microsoft.bing.imagesearch.models.Images;
import com.microsoft.bing.websearch.WebSearchClient;
import com.microsoft.bing.websearch.implementation.WebSearchClientImpl;
import com.microsoft.rest.credentials.ServiceClientCredentials;

import io.minio.MinioClient;
import lombok.Getter;
import net.ryian.flow.model.bo.Setting;
import okhttp3.Request;

/**
 * @author allenwc
 * @date 2024/5/16 20:18
 */
@Component
public class ServiceFactory {

    @Getter
    private Setting setting;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(setting.getEmailSmtpHost());
        sender.setPort(setting.getEmailSmtpPort());
        sender.setDefaultEncoding("UTF-8");
        String from = setting.getEmailUser();
        sender.setUsername(from);
        sender.setPassword(setting.getEmailPassword());
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", setting.getEmailSmtpSsl().toString());
        properties.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(properties);
        return sender;
    }

    public OpenAiChatClient getOpenAiChatClient() {
        ClientHttpRequestFactorySettings requestFactorySettings = new ClientHttpRequestFactorySettings(
            Duration.ZERO , Duration.ZERO , SslBundle.of(null));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(requestFactorySettings);
        Builder clientBuilder = RestClient.builder().requestFactory(requestFactory);
        OpenAiApi openAiApi = new OpenAiApi(setting.getOpenaiApiBase(), setting.getAzureApiKey(), clientBuilder);
        return new OpenAiChatClient(openAiApi);
    }

    public OpenAiChatClient getMoonshotChatClient() {
        ClientHttpRequestFactorySettings requestFactorySettings = new ClientHttpRequestFactorySettings(
            Duration.ZERO , Duration.ZERO , SslBundle.of(null));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(requestFactorySettings);
        Builder clientBuilder = RestClient.builder().requestFactory(requestFactory);
        OpenAiApi openAiApi = new OpenAiApi(setting.getMoonshotApiBase(), setting.getMoonshotApiKey(), clientBuilder);
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
            .endpoint(setting.getMinioBaseUrl())
            .credentials(setting.getMinioKey(), setting.getMinioSecret())
            .build();
    }

    public WebSearchClient getWebSearchClient() {
        String endpoint = "https://api.bing.microsoft.com/v7.0";

        ServiceClientCredentials credentials = builder -> builder.addNetworkInterceptor(chain -> {
            Request request = null;
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Ocp-Apim-Subscription-Key", setting.getBingApiKey());
            request = requestBuilder.build();
            return chain.proceed(request);
        });
        return new WebSearchClientImpl(endpoint,credentials);
    }

    public ImageSearchClient getImageSearchClient() {
        String endpoint = "https://api.bing.microsoft.com/v7.0";

        ServiceClientCredentials credentials = builder -> builder.addNetworkInterceptor(chain -> {
            Request request = null;
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Ocp-Apim-Subscription-Key", setting.getBingApiKey());
            request = requestBuilder.build();
            return chain.proceed(request);
        });
        return new ImageSearchClientImpl(endpoint,credentials);
    }

    public void updateSetting(Setting setting) {
        this.setting = setting;
    }

    public static void main(String[] args) {
        ImageSearchClient client = new ServiceFactory().getImageSearchClient();
        Images images = client.images().search("杭州小草信息技术有限公司");
        System.out.println(images);
    }

}
