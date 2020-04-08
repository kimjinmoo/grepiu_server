package com.grepiu.www.process.common.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * AWS SES 메일 보내기
 * ref. https://docs.aws.amazon.com/ko_kr/ses/latest/DeveloperGuide/send-using-sdk-java.html
 *      https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
 */
public class AwsSESMailUtils {

    static final String SUPPORT_EMAIL = "support@grepiu.com";

    public static void sendPassResetMail(String recMail, String newPassword) throws IOException {
        // The subject line for the email.
        final String SUBJECT = "[그렙 플레이스] 비밀번호 초기화 메일입니다.";
        // The HTML body for the email.
        final String HTMLBODY =
                "<h2>안녕하세요. 그렙 플레이스 입니다.</h2>\n" +
                "<p style=\"font-size: 1em;\">해당 메일은 비밀번호 리셋 메일입니다.</p>\n" +
                "<br>\n" +
                "<p style=\"font-size: 1em;\">귀하가 만약 요청하지 않으셨다면 메일은 무시 하셔도 됩니다.</p>\n" +
                "<p style=\"font-size: 1em;\">요청하신 메일이라면 아래 안내드린 비밀번호를 통해 로그인 하시면 됩니다. </p>\n" +
                "<br>\n" +
                "<strong>"+newPassword+"</string>\n" +
                "<br>\n" +
                "<br>\n" +
                "<p>서비스 이용을 더욱 편하게 해드리도록 노력 하겠습니다.</p>\n" +
                "<p>감사합니다.</p>";
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.AP_SOUTH_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(recMail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(HTMLBODY))
                                    )
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(SUPPORT_EMAIL);
            client.sendEmail(request);
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
