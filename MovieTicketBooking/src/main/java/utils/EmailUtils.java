package utils;

import java.util.Properties;
import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtils {
    
    // Tạo mã OTP ngẫu nhiên 6 số
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static void sendEmail(String toEmail, String subject, String body) throws Exception {
        // Cấu hình SMTP (Ví dụ dùng Gmail)
        final String fromEmail = "23130079@st.hcmuaf.edu.vn";
        final String password = "qykfkkqsjtmvwqsf"; // Đảm bảo App Password này còn hạn nhé!

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // Bỏ try-catch ở đây để ném lỗi thẳng ra cho RegisterServlet bắt
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(subject, "UTF-8");
        msg.setText(body, "UTF-8");

        Transport.send(msg);
    }

}