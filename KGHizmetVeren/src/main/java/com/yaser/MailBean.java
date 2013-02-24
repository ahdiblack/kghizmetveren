package com.yaser;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.yaser.app.SessionUtil;
import com.yaser.data.model.TblPoi;
import com.yaser.data.model.TblSubscriber;


@ManagedBean
@Component
public class MailBean {

	
	@Inject
	JavaMailSender javaMailSender;
	
	@Inject
	private VelocityEngine velocityEngine;
	
	@Inject
	private SessionUtil sessionUtil;
	
	private String email;
	
	private TblPoi provider = new TblPoi();
	
	
	public void shareMailSuggestion(){
		String delims = "[\n\r]";
		String[] tokens = email.split(delims);
		
		for (String string : tokens) {
			try {
				if (!string.isEmpty()) {
					preapareProps(string);
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail gönderirken hata oldu. Lütfen e-posta adreslerini kontrol edip tekrar deneyin"));
			}
		}
		email = "";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail istekleri baþarýlý bir þekilde gönderildi."));
	}	

	
	private void preapareProps(String email) {
		TblSubscriber userContext = sessionUtil.getUser();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", userContext.getName());
		model.put("surname", userContext.getSurname());
		model.put("profile",userContext.getImageUrl());
		model.put("logo","http://web1.kimegitsem.com/img/suggestion/mail_logo.png");
		model.put("footer","http://web1.kimegitsem.com/img/suggestion/mail_footer.png");
		model.put("description", "desription");
		model.put("poi", "poi");
		model.put("url", "poi");
		sendMail(model, "invitation", email, "Davetiye");
	}
	
	public boolean sendMail(final Map attributes, final String template, final String email,final String subject) {

		boolean sendingResult=true;
		MimeMessagePreparator mimepreparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage);
						// mail sending parameters
						message.setTo(email);
						message.setSubject(subject);
						message.setFrom("ylcnarsln@gmail.com");
						String mailContent = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										template+".vm", "UTF-8", attributes);
						message.setText(mailContent, true);

					}
				};
		try {
			javaMailSender.send(mimepreparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendingResult;
	}

	public TblPoi getProvider() {
		return provider;
	}

	public void setProvider(TblPoi provider) {
		this.provider = provider;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}
