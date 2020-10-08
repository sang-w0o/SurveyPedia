package com.surveypedia.members.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.withdrawed.Withdrawed;
import com.surveypedia.domain.withdrawed.WithdrawedRepository;
import com.surveypedia.members.dto.MemberInsertRequestDto;
import com.surveypedia.members.dto.MemberPassUpdateRequestDto;
import com.surveypedia.members.exception.*;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MembersRepository membersRepository;
    private final WithdrawedRepository withdrawedRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject login(String email, String pass, HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        if(email.equals("admin@surveypro.com")) {
            jsonObject.put("errno", 2);
            jsonObject.put("message", "관리자 페이지로 이동합니다.");
            return jsonObject;
        }
        try {
            Withdrawed withdrawed = withdrawedRepository.findByEmail(email);
            if (withdrawed != null) throw new MemberWithdrawLoginException();
            Members member = membersRepository.login(email, pass);
            if(member != null) {
                request.getSession().setAttribute("userInfo", member);
                request.getSession().setAttribute("result", true);
                jsonObject.put("errno", 0);
                jsonObject.put("message", "로그인 성공");
            } else throw new MemberLoginException();
        } catch(MemberException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject logout(HttpServletRequest request){
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            HttpSession session = request.getSession(false);
            session.setAttribute("result", false);
            session.invalidate();
            jsonObject.put("errno", 0);
            jsonObject.put("message", "로그아웃이 완료되었습니다.");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject changePassword(MemberPassUpdateRequestDto requestDto, HttpServletRequest request) {
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            membersRepository.changePass(requestDto.getChangepass(), member.getEmail());
            jsonObject.put("result", true);
            jsonObject.put("message", "비밀번호가 정상 변경되었습니다. 다시 로그인 해 주세요.");
            request.getSession(false).invalidate();
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
            jsonObject.put("result", false);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject checkEmail(String email) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Members member = membersRepository.findByEmail(email);
            if(member != null) throw new MemberEmailAlreadyInUseException();
            jsonObject.put("result", true);
            jsonObject.put("message", "사용 가능한 이메일 입니다.");
        } catch(MemberEmailAlreadyInUseException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject checkNick(String nick) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Members member = membersRepository.findByNick(nick);
            if(member != null) throw new MemberNickAlreadyInUseException();
            jsonObject.put("result", true);
            jsonObject.put("message", "사용 가능한 별명 입니다.");
        } catch(MemberNickAlreadyInUseException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }
    public org.json.simple.JSONObject signUp(MemberInsertRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            membersRepository.signUp(requestDto.getEmail(), requestDto.getPass(), requestDto.getNick());
            jsonObject.put("result", true);
            jsonObject.put("message", "회원 가입에 성공했습니다. 로그인 해 주세요!");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new Exception("알 수 없는 오류로 회원 가입에 실패했습니다."));
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject getPoint(HttpServletRequest request) {
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        Integer point = membersRepository.getPoint(member.getEmail());
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        jsonObject.put("result", true);
        jsonObject.put("point", point);
        return jsonObject;
    }

    public org.json.simple.JSONObject checkLogin(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            HttpSession session = request.getSession(false);
            if(session == null || session.getAttribute("userInfo") == null)
                throw new MemberLoginCheckException();
            jsonObject.put("result", true);
            } catch(MemberLoginCheckException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
            jsonObject.put("message", exception.getMessage());
        }
        return jsonObject;
    }

    private static void sendEmail(String email, String pass) {
        final String bodyEncoding = "UTF-8";
        String subject = "Temporary Password Email";
        String fromEmail = "vc.system.noreply@gmail.com";
        String fromUsername = "VC SYSTEM MANAGER";
        String toEmail = email;

        final String username = "vc.system.noreply";
        final String password = "xjaitzlgbrodjvth";

        StringBuffer sendBuffer = new StringBuffer();
        sendBuffer.append("<h3>안녕하세요.</h3>\n");
        sendBuffer.append("<h4>다음은 발급해드린 임시 비밀번호입니다.</h4>\n");
        sendBuffer.append("<h1>").append(pass).append("</h1>\n");
        sendBuffer.append("<h4>위 임시 비밀번호로 로그인 후 비밀번호를 변경해주시길 바랍니다.</h4>");
        String html = sendBuffer.toString();

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.quitwait", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        try {
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            Session session = Session.getInstance(props, auth);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, fromUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject);
            message.setSentDate(new Date());

            Multipart mParts = new MimeMultipart();
            MimeBodyPart mTextPart = new MimeBodyPart();

            mTextPart.setText(html, bodyEncoding, "html");
            mParts.addBodyPart(mTextPart);

            message.setContent(mParts);

            MailcapCommandMap MailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            MailcapCmdMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            MailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            MailcapCmdMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            MailcapCmdMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            MailcapCmdMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");

            CommandMap.setDefaultCommandMap(MailcapCmdMap);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public org.json.simple.JSONObject sendTempPassToEmail(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        String email = request.getParameter("email");
        StringBuffer passBuilder = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int rIndex = random.nextInt(3);
            switch (rIndex) {
                case 0:
                    passBuilder.append((char) ((int) (random.nextInt(26)) + 97));
                    break;
                case 1:
                    passBuilder.append((char) ((int) (random.nextInt(26)) + 65));
                    break;
                case 2:
                    passBuilder.append(random.nextInt(10));
                    break;
            }
        }
        String pass = passBuilder.toString().substring(0, 6);
        sendEmail(email, pass);
        membersRepository.changePass(pass, email);
        jsonObject.put("result", true);
        jsonObject.put("message", "이메일이 발송되었습니다.\n만약 이메일이 오지 않았다면 스팸함을 확인해 주세요!");
        return jsonObject;
    }
}
