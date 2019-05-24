package tools.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.mail
 * @Author: csn
 * @Description: Mail
 */
public class Mail {
    private String from;//发件人
    private StringBuilder to = new StringBuilder();//收件人
    private StringBuilder cc = new StringBuilder();//optional，抄送
    private StringBuilder bcc = new StringBuilder();//optional，暗送

    private String subject;//主题
    private String content;//正文

    private List<Attachment> attachmentList = new ArrayList<>();//optional，附件

    public Mail() {}

    public Mail(String from, String to) {
        this(from,to,null,null);
    }

    public Mail(String from, String to, String subject, String content) {
        this.from = from;
        this.to.append(to);
        this.subject = subject;
        this.content = content;
    }
    //设置发件人
    public String getFrom() {
        return from;
    }
    //获取发件人
    public void setFrom(String from) {
        this.from = from;
    }

    //添加收件人
    public void addTo(String to) {
        if(this.to.length() > 0) this.to.append(",");
        this.to.append(to);
    }
    //获取收件人
    public String getTo() {
        return to.toString();
    }

    //获取抄送
    public String getCc() {
        return cc.toString();
    }
    //添加抄送
    public void addCc(String cc) {
        if(this.cc.length() > 0) this.cc.append(",");
        this.cc.append(cc);
    }

    //获取暗送
    public String getBcc() {
        return bcc.toString();
    }
    //添加暗送
    public void addBcc(String bcc) {
        if(this.bcc.length() > 0) this.bcc.append(",");
        this.bcc.append(bcc);
    }

    //获取附件列表
    public List<Attachment> getAttachmentList() {
        return this.attachmentList;
    }
    //添加附件
    public void addAttachment(Attachment attachment) {
        this.attachmentList.add(attachment);
    }

    //获取主题
    public String getSubject() {
        return subject;
    }
    //设置主题
    public void setSubject(String subject) {
        this.subject = subject;
    }

    //获取正文
    public String getContent() {
        return content;
    }
    //设置正文
    public void setContent(String content) {
        this.content = content;
    }


}
