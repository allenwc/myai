package net.ryian.flow.common.workflow.task.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.integration.ServiceFactory;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/15 09:28
 */
@Component
@Configuration
@Slf4j
public class Email {

    private final ServiceFactory serviceFactory;

    @Autowired
    public Email(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        String toEmail = (String) workflow.getNodeFieldValue(nodeId, "to_email");
        String subject = (String) workflow.getNodeFieldValue(nodeId, "subject");
        String contentHtml = (String) workflow.getNodeFieldValue(nodeId, "content_html");

        JavaMailSender sender = serviceFactory.getJavaMailSender();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(serviceFactory.getSetting().getEmailUser());
            //邮件接收人,设置多个收件人地址
            InternetAddress[] internetAddressTo = InternetAddress.parse(toEmail);
            messageHelper.setTo(internetAddressTo);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(contentHtml, true);
            //发送
            sender.send(message);
            //日志信息
            log.info("邮件已经发送。");
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
        }

        return workflow.getData();
    }

}
