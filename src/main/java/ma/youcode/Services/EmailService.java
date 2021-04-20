package ma.youcode.Services;

public interface EmailService {
    void sendConfirmationEmail(String to, String subject, String text);
}
