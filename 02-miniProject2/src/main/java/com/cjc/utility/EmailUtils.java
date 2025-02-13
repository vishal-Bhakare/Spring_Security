package com.cjc.utility;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailsender;

	public boolean sendEmail(String to, String subject, String body) {

		boolean ispresent = false;

		try {

			MimeMessage mimeMessage = mailsender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailsender.send(mimeMessage);

			ispresent = true;
		} catch (Exception e) {

			e.getStackTrace();
		}

		return ispresent;

	}

}
