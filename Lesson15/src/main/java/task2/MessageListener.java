package task2;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MessageListener<T> implements DataListener<T> {

    private String recipients = "";

    @Override
    public void onEvent(T result) throws DataListenerException {
        if (result.getClass() == String.class){
            String resultingHtml = (String) result;
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            // we're going to use google mail to send this message
            mailSender.setHost("mail.google.com");
            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            try {
                helper = new MimeMessageHelper(message, true);
                helper.setTo(recipients);
                helper.setText(resultingHtml, true);
                // setting message text, last parameter 'true' says that it is HTML format
                helper.setSubject("Monthly department salary report");
            } catch (MessagingException e) {
                throw new DataListenerException(e);
            }
            mailSender.send(message);
            // send the message
        }
    }

    @Override
    public <D> void additionalData(D data) {
        if (data.getClass() == String.class) {
            recipients = (String) data;
        }
    }
}
