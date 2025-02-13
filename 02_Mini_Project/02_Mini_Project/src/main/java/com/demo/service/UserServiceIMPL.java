package com.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.binding.LoginForm;
import com.demo.binding.SignUpForm;
import com.demo.binding.UnlockForm;
import com.demo.constants.AppConstants;
import com.demo.entity.UserDtlsEntity;
import com.demo.repo.UserDtlsRepo;
import com.demo.util.EmailUtils;
import com.demo.util.PwdUtils;

@Service
public class UserServiceIMPL implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private HttpSession session;

	@Override
	public boolean signUp(SignUpForm form) {

		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		if (user != null) {
			System.out.println("ll");
			return false;
		}
		System.out.println("hello");
		// TODO : Copy data form binding obj to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
		System.out.println("hello1");
		// TODO : generate random pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		System.out.println("hello2");
		// TODO : Set account status as LOCKED
		entity.setAccStatus(AppConstants.STR_LOCKED);
		System.out.println("hello3");
		// TODO : Insert record
		userDtlsRepo.save(entity);
		System.out.println("hello4");
		// TODO : Send email to unlock the account
		String to = form.getEmail();
		String subject = AppConstants.STR_EMAIL_SUBJECT1;
		System.out.println("hello5");
		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use below temporary password to unlock your account </h1>");
		body.append("<h3> Temp Pwd : " + tempPwd + "</h3>");
		body.append(" <br/> ");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock Your Account </a>");
		System.out.println("hello6");
		emailUtils.sendEmail(to, subject, body.toString());
		System.out.println("hello7");
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlock) {
		UserDtlsEntity entity = userDtlsRepo.findByEmail(unlock.getEmail());

		if (entity.getPwd().equals(unlock.getTempPwd())) {
			entity.setPwd(unlock.getNewPwd());
			entity.setAccStatus(AppConstants.STR_UNLOCKED);
			userDtlsRepo.save(entity);
			return true;
		} else {
			return false;

		}
	}

	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return AppConstants.STR_INVALID_CREDENTIALS;
		}
		if (entity.getAccStatus().equals(AppConstants.STR_LOCKED)) {
			return AppConstants.STR_ACCOUNT_LOCKED;
		}

		session.setAttribute(AppConstants.STR_USER_ID, entity.getUserId());
		return AppConstants.STR_SUCCESS;
	}

	@Override
	public boolean forgotPwd(String email) {
		// TODO : Find the the Account by Email
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		// Check Email is Present Or Not
		if (entity == null) {
			return false;
		}

		// If Email is Present then Send the Pwd to Mail
		String subject = AppConstants.STR_EMAIL_SUBJECT2;
		String body = "<h2> Your Password : " + entity.getPwd() + "</h2>";
		emailUtils.sendEmail(email, subject, body);

		return true;
	}

}
