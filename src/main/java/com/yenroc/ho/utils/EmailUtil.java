package com.yenroc.ho.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.yenroc.ho.vo.EmailReqtVO;
import com.yenroc.ho.vo.ReceivePeopleVO;

/**
 * java发送邮件，参考地址：http://blog.csdn.net/xietansheng/article/details/51673073
 * @author 和彦鹏
 * @date 2018年6月1日23:58:29
 * 
 */
public class EmailUtil {
    public static void sendEmail(EmailReqtVO emailReqtVO) throws Exception{
     // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailReqtVO.getSendEmailSMTPHost());   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        
        */
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, emailReqtVO);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        // 
        //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(emailReqtVO.getSendEmailAccount(), emailReqtVO.getSendEmailPassword());

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }
    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, EmailReqtVO emailReqtVO) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(emailReqtVO.getSendEmailAccount(), emailReqtVO.getSendPeople(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        if (emailReqtVO.getSendPeoples() != null && emailReqtVO.getSendPeoples().size() > 0) {
            InternetAddress[] sendTo = new InternetAddress[emailReqtVO.getSendPeoples().size()];
            for (int i = 0; i < emailReqtVO.getSendPeoples().size(); i++) {  
                sendTo[i] = new InternetAddress(emailReqtVO.getSendPeoples().get(i).getReceiveMailAccount(),
                        emailReqtVO.getSendPeoples().get(i).getReceivePeople(), "UTF-8");  
            }  
            message.setRecipients(RecipientType.TO, sendTo);
        } else {
            System.out.println("收件人不可为空");
        }
        
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(emailReqtVO.getEmailTheme(), "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(emailReqtVO.getEmailContent(), "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
    
    
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            demoQQEmail();
            Thread.sleep(1000*30);
        }
        
        
        // demo163Email();
//        final Base64.Decoder decoder = Base64.getDecoder();
//        final Base64.Encoder encoder = Base64.getEncoder();
//        final String text = "HYP7698820..";
//        final byte[] textByte = text.getBytes("UTF-8");
//        //编码
//        final String encodedText = encoder.encodeToString(textByte);
//        System.out.println(encodedText);
//        //解码
//        System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
        
    }
    /**
     * 测试qq邮箱-->发送给qq邮箱,163邮箱
     * @throws Exception
     */
    private static void demoQQEmail() throws Exception{
        EmailReqtVO email01ReqtM01 = new EmailReqtVO();
        
        email01ReqtM01.setSendEmailAccount("1623718584@qq.com");
        email01ReqtM01.setSendEmailPassword("phhfklipssxkejja");//使用授权码
        email01ReqtM01.setSendEmailSMTPHost("smtp.qq.com");
        email01ReqtM01.setSendPeople("blog社交博客");
        ReceivePeopleVO vo1 = new ReceivePeopleVO();
        vo1.setReceiveMailAccount("linr.louh7@qq.com");
        vo1.setReceivePeople("娄浩");
        ReceivePeopleVO vo11 = new ReceivePeopleVO();
        vo11.setReceiveMailAccount("1065613730@qq.com");
        vo11.setReceivePeople("董格瑞");
        ReceivePeopleVO vo111 = new ReceivePeopleVO();
        vo111.setReceiveMailAccount("1643420488@qq.com");
        vo111.setReceivePeople("张琦");
        ReceivePeopleVO vo1111 = new ReceivePeopleVO();
        vo1111.setReceiveMailAccount("1138884009@qq.com");
        vo1111.setReceivePeople("张送");
        
        ReceivePeopleVO vo2 = new ReceivePeopleVO();
        vo2.setReceiveMailAccount("17621421619@163.com");
        vo2.setReceivePeople("和彦鹏");
        
        ReceivePeopleVO vo3 = new ReceivePeopleVO();
        vo3.setReceiveMailAccount("luanbing@kmpro.cn");
        vo3.setReceivePeople("大冰冰");
        
        ArrayList<ReceivePeopleVO> list = new ArrayList<ReceivePeopleVO>();
        list.add(vo1);list.add(vo11);list.add(vo111);list.add(vo1111);
//        list.add(vo2);
        //list.add(vo3);
        email01ReqtM01.setSendPeoples(list);
        
        email01ReqtM01.setEmailContent("这是一个blog系统的一个邮件,勿回!,qq叫大哥,不然邮件不间断<h1>哈哈哈</h1><a href=\"https://heyanpenggit.github.io/blog-web/blog/login.html\">看我的静态网站</a>");
        email01ReqtM01.setEmailTheme("BLog系统邮件");
        sendEmail(email01ReqtM01);
    }
    /**
     * 163邮箱-->发送给qq,163邮箱的demo
     * @throws Exception
     */
    private static void demo163Email() throws Exception{
        EmailReqtVO email01ReqtM01 = new EmailReqtVO();
        
        email01ReqtM01.setSendEmailAccount("17621421619@163.com");
        email01ReqtM01.setSendEmailPassword("HYP7698820..");
        email01ReqtM01.setSendEmailSMTPHost("smtp.163.com");
        email01ReqtM01.setSendPeople("blog社交博客");// 发件人
        ReceivePeopleVO vo1 = new ReceivePeopleVO();// qq邮箱收件人
        vo1.setReceiveMailAccount("1600727964@qq.com");
        vo1.setReceivePeople("和彦鹏");
        
        ReceivePeopleVO vo2 = new ReceivePeopleVO();
        vo2.setReceiveMailAccount("17621421619@163.com");// 163邮箱收件人
        vo2.setReceivePeople("和彦鹏");
        
        ArrayList<ReceivePeopleVO> list = new ArrayList<ReceivePeopleVO>();
        //list.add(vo1);
        list.add(vo2);
        email01ReqtM01.setSendPeoples(list);
        
        email01ReqtM01.setEmailContent("这是一个blog系统的一个邮件,勿回!");
        email01ReqtM01.setEmailTheme("BLog系统邮件");
        sendEmail(email01ReqtM01);
    }
}
