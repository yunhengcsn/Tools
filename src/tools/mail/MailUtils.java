package tools.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.mail
 * @Author: csn
 * @Description: send email; rely on mail.jar & activation.jar
 */
public class MailUtils {
    //获取session
    public static Session createSession(String host , String username, String password) {
        Properties props = new Properties();
        props.setProperty("mail.host",host);
        props.setProperty("mail.smtp.auth","true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
        return Session.getInstance(props,auth);
    }

    //发送邮件
    public static void send(Session session, Mail mail) throws MessagingException, IOException {
        //创建MImeMessage
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mail.getFrom()));
        msg.setRecipients(Message.RecipientType.TO,mail.getTo());
        if(mail.getCc() != null) msg.setRecipients(Message.RecipientType.CC,mail.getCc());
        if(mail.getBcc() != null) msg.setRecipients(Message.RecipientType.BCC,mail.getBcc());
        msg.setSubject(mail.getSubject());

        //带附件的邮件
        MimeMultipart content = new MimeMultipart();
        //设置正文
        MimeBodyPart part = new MimeBodyPart();
        part.setContent(mail.getContent(),"text/html;charset=utf-8");
        content.addBodyPart(part);

        //设置附件
        List<Attachment> attachmentList = mail.getAttachmentList();
        if(attachmentList != null) {
            for(Attachment attachment : attachmentList) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(attachment.getFile());
                attachPart.setFileName(MimeUtility.encodeText(attachment.getFilename()));
                String atId = attachment.getAtId();
                if(atId != null) attachPart.setContentID(atId);
                content.addBodyPart(attachPart);
            }
        }

        msg.setContent(content);

        //发送邮件
        Transport.send(msg);
    }
}
