package com.live.pollprojectrestapi.domain.service.up;

import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.SendMailForCreateGroupUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.SendMailFormInformResults;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailService implements SendMailForCreateGroupUseCase, SendMailFormInformResults {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

    public String build(String groupId) {
        Context context = new Context();
        context.setVariable("groupId", groupId);
        return templateEngine.process("mailTemplate", context);
    }

    public String buildForResult(UUID sessionId) {
        Context context = new Context();
        context.setVariable("sessionId", sessionId);
        return templateEngine.process("mailTemplateResults", context);
    }

    @Override
    public void sendMailForCreateGroup(Person person, UUID groupId, UUID sessionId) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String content = build(groupId.toString());

            helper.setTo(person.getEmail().getValue());
            helper.setSubject("Créer votre groupe !");
            helper.setFrom("pollproject502@gmail.com", "POLL PROJECT");
            message.setContent(content, "text/html");
            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMail(Person person, UUID sessionId) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String content = buildForResult(sessionId);

            helper.setTo(person.getEmail().getValue());
            helper.setSubject("Les résultat sont disponible !");
            helper.setFrom("pollproject502@gmail.com", "POLL PROJECT");
            message.setContent(content, "text/html");
            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
