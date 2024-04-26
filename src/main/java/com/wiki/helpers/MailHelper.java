package com.wiki.helpers;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.wiki.config.Config;
import com.wiki.models.UserModel;

public class MailHelper {
  /**
   * Sends an email with the specified recipient, subject, and content using the
   * configured SMTP server settings.
   *
   * @param recipientEmail The email address of the recipient.
   * @param subject        The subject of the email.
   * @param content        The content of the email.
   */
  private static void send(String recipientEmail, String subject, String content) {
    // Setup mail server properties
    Properties properties = new Properties();
    properties.put("mail.smtp.host", Config.SMTP_HOST);
    properties.put("mail.smtp.port", String.valueOf(Config.SMTP_PORT));
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.ssl.enable", "true"); // Enable SSL/TLS
    properties.put("mail.smtp.ssl.trust", Config.SMTP_HOST); // Trust the SMTP server's SSL certificate

    // Get the Session object
    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(Config.SMTP_USERNAME, Config.SMTP_PASSWORD);
      }
    });

    try {
      // Create a default MimeMessage object
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header
      message.setFrom(new InternetAddress(Config.SMTP_USERNAME));

      // Set To: header field of the header
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

      // Set Subject: header field
      message.setSubject(subject);

      // Now set the actual message
      message.setContent(content, "text/html; charset=utf-8");

      // Send message
      Transport.send(message);
      System.out.println("Email sent successfully.");
    } catch (MessagingException e) {
      System.out.println("Error sending email: " + e.getMessage());
    }
  }

  /**
   * Sends a forgot password email to the user containing the new password.
   *
   * @param data     The UserModel containing user information.
   * @param password The new password to be included in the email.
   */
  public static void sendForgotPasswordEmail(UserModel data, String password) {
    String content = getForgotPasswordMailTemplate(data.fullname, password);

    send(data.email, "Password Reset Request", content);
  }

  /**
   * Generates the HTML content for the forgot password email template.
   *
   * @param fullname The user's full name.
   * @param password The new password.
   * @return The HTML content for the email template.
   */
  private static String getForgotPasswordMailTemplate(String fullname, String password) {
    String htmlContent = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <meta charset=\"UTF-8\">\n" +
        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
        "    <title>Password Reset Email</title>\n" +
        "</head>\n" +
        "<body style=\"font-family: Arial, sans-serif;\">\n" +
        "\n" +
        "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">\n" +
        "        <h2 style=\"color: #333;\">Password Reset Request</h2>\n" +
        "        <p>Hello " + fullname + ",</p>\n" +
        "        <p>We received a request to reset your password. Your new password is: <strong>" + password
        + "</strong></p>\n"
        +
        "        <p>If you didn't request a password reset, please contact us immediately.</p>\n" +
        "        <p>Best regards,<br>Your App Team</p>\n" +
        "    </div>\n" +
        "\n" +
        "</body>\n" +
        "</html>";

    return htmlContent;
  }
}
