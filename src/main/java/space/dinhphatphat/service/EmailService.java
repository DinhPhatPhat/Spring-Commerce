package space.dinhphatphat.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.url}")
    private String appUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Xác nhận tài khoản Our stories";
        String verificationUrl = appUrl + "/user/verify?token=" + token;
        String message = "<h2>Xin chào,</h2>"
                + "<p>Email của bạn vừa thực hiện đăng ký tài khoản ourstories.space, vui lòng nhấn vào link dưới đây để kích hoạt tài khoản Our stories của bạn:</p>"
                + "<a href='" + verificationUrl + "'>" + verificationUrl + "</a>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }

    public void sendChangePasswordEmail(String toEmail, String token) {
        String subject = "Đổi mật khẩu Our stories";
        String changePasswordUrl = appUrl + "/user/change-password?token=" + token;
        String message = "<h2>Xin chào,</h2>"
                + "<p>Email của bạn vừa thực hiện yêu cầu đổi mật khẩu ourstories.space," +
                " vui lòng nhấn vào link dưới đây để đổi mật khẩu cho tài khoản Our stories của bạn:</p>"
                + "<a href='" + changePasswordUrl + "'>" + changePasswordUrl + "</a>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}
