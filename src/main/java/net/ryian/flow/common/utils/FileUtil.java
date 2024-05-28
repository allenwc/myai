package net.ryian.flow.common.utils;

import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import io.minio.GetObjectArgs;
import lombok.Data;
import lombok.SneakyThrows;
import net.ryian.flow.integration.ServiceFactory;

/**
 * @author allenwc
 * @date 2024/5/10 09:50
 */
@Component
public class FileUtil {

    private ServiceFactory serviceFactory;

    @Autowired
    public FileUtil(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public String getFileContent(JSONArray array) {
        StringBuilder stringBuffer = new StringBuilder();
        //将array中JsonObject转成FileObject
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            FileObject fileObject = jsonObject.toJavaObject(FileObject.class);
            //获取文件内容
            stringBuffer.append(getFileContent(fileObject));
        }
        return stringBuffer.toString();
    }

    @SneakyThrows
    private String getFileContent(FileObject fileObject) {
        String text = null;
        InputStream stream = this.serviceFactory.getMinioClient().getObject(
            GetObjectArgs.builder().bucket("flow").object(fileObject.getResponse()).build());

        if ("application/pdf".equals(fileObject.type)) {
            PDDocument document = PDDocument.load(stream);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
            document.close();
        }
        return text;
    }


    @Data
    public class FileObject {
        private String response;
        private String name;
        private String type;
    }

}
