package com.yaser;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
//
//import com.persona.kg.action.MimeMessage;
//import com.persona.kg.action.MimeMessageHelper;
//import com.persona.kg.action.MimeMessagePreparator;

import com.yaser.data.model.TblPoi;


@ManagedBean
@Component
public class TestMail {

	
	@Inject
	JavaMailSender javaMailSender;
	
	private TblPoi provider = new TblPoi();
	
	public void testMail() {
		
//		MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
//			public void prepare(MimeMessage mimeMessage) throws Exception {
//				MimeMessageHelper message = new MimeMessageHelper(
//						mimeMessage);
//				// mail sending parameters
//				message.setTo(email);
//				message.setSubject(subject);
//				message.setFrom("ylcnarsln@gmail.com@gmail.com");
//				String mailContent = VelocityEngineUtils
//						.mergeTemplateIntoString(velocityEngine,"invitation.vm", "UTF-8", attributes);
//				message.setText(mailContent, true);
//
//			}
//		};
		
		System.out.println(provider.getPoiName());
		
		SimpleMailMessage m = new SimpleMailMessage();
		m.setFrom("ylcnarslan@windowslive.com");
		m.setTo("yaser_iztech@gmail.com");
		m.setText("test iþte");
		
		javaMailSender.send(m);
	}

	public TblPoi getProvider() {
		return provider;
	}

	public void setProvider(TblPoi provider) {
		this.provider = provider;
	}
	
}
