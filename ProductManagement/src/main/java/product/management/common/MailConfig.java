package product.management.common;

import java.util.random.RandomGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.user.dto.UserTB;
import product.management.user.mapper.UserTBMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailConfig {
	
	private final JavaMailSender javaMailSender;
	private final UserTBMapper userTBMapper; 
	
	@Value("${spring.mail.username}")
    private String fromEmail;
	
	public boolean idEmail(String user_email) {
		boolean result = false;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        
        UserTB userTB = userTBMapper.selectByUserEmail(user_email);
        if(userTB == null) {
        		result = false;
        		return false;
        }
        
        String text = "<div style='padding:24px; border:0.125rem solid #ab9191; text-align:center;'>" +
                "<p style='font-size:24px; font-weight:bold; margin-top:10px; color:#e8505a;'>Product Mangement</p>" +
                "<p style='font-size:16px; margin-top:40px;'>Product Mangement 아이디 찾기 이메일 입니다</p>" +
                "<p style='font-size:16px; margin-top:20px;'>고객님의 아이디는 "+userTB.getUser_id()+" 입니다</p>" +
                "</div>";
        
        try{

            helper.setSubject("Product Mangement - 아이디 찾기 이메일 발송");    // 제목 설정
            helper.setFrom(new InternetAddress(fromEmail, "ItemRecord", "UTF-8"));  // 보내는 이
            helper.setTo(user_email);
            helper.setText(text, true);

            javaMailSender.send(message);

            result = true;
        }catch(MessagingException me){
            result = false;
            log.info("MessagingException = {}", me);
        }catch(Exception e){
            result = false;
            log.info("Exception = {}", e);
        }

        return result;
	}
	
	public int passEmail(String user_email){
		UserTB userTB = userTBMapper.selectByUserEmail(user_email);
        if(userTB == null) {
        		return 0;
        }
		
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        int ranNum = randomNumber();
        String text = "<div style='padding:24px; border:0.125rem solid #ab9191; text-align:center;'>" +
                "<p style='font-size:24px; font-weight:bold; margin-top:10px; color:#e8505a;'>Product Mangement</p>" +
                "<p style='font-size:16px; margin-top:40px;'>Product Mangement 비밀번호 변경 이메일 입니다</p>" +
                "<p style='font-size:16px; margin-top:20px;'>인증코드 "+ranNum+"</p>" +
                "</div>";
        
        try{

            helper.setSubject("Product Mangement - 비밀번호 변경 이메일 발송");    // 제목 설정
            helper.setFrom(new InternetAddress(fromEmail, "ItemRecord", "UTF-8"));  // 보내는 이
            helper.setTo(user_email);
            helper.setText(text, true);

            javaMailSender.send(message);
            return ranNum;
        }catch(MessagingException me){
            log.info("MessagingException = {}", me);
            return 0;
        }catch(Exception e){
            log.info("Exception = {}", e);
            return 0;
        }

        
    }
	
	public int randomNumber() {
		RandomGenerator generator = RandomGenerator.getDefault();
		int randomNum = generator.nextInt(1000, 10000);
		return randomNum;
	}

}
