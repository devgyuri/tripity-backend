package me.gyuri.tripity.domain.auth.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.global.exception.CustomException;
import me.gyuri.tripity.global.exception.ErrorCode;
import me.gyuri.tripity.global.redis.service.RedisService;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;
    private final RedisService redisService;

    private static String createKey() {
        StringBuilder key = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 'a'));
                case 1 -> key.append((char) (random.nextInt(26) + 'A'));
                case 2 -> key.append(random.nextInt(10));
            }
        }
        return key.toString();
    }

    public void sendMail(String mail) throws Exception {
        String authCode = createKey();
        MimeMessage message = createMessage(authCode, mail);
        redisService.setDataExpire(mail, authCode, 60 * 5L);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            log.error("+++++ Mail Service +++++");
            log.error(es.getMessage());
            es.printStackTrace();
            throw new CustomException(ErrorCode.FAILED_SENDING_VERIFY_CODE);
        }
    }

    private MimeMessage createMessage(String code, String mail) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, mail);
        message.setSubject("[Tripity] 인증 번호입니다.");
        String msgString = "인증 코드: <strong>" + code + "</strong>";
        message.setText(msgString, "utf-8", "html");
        message.setFrom("gksmf2910@naver.com");

        return message;
    }
}
