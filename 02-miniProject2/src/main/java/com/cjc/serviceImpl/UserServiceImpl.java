package com.cjc.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjc.binding.LoginForm;
import com.cjc.binding.SingUpForm;
import com.cjc.binding.UnlockForm;
import com.cjc.entity.UserDtlEntity;
import com.cjc.repository.UserDtlsRepo;
import com.cjc.service.UserService;
import com.cjc.utility.EmailUtils;
import com.cjc.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailutils;

	@Autowired
	private HttpSession Session;

	@Override
	public String login(LoginForm form) {

		UserDtlEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return "Invalid Credentials";

		}

		if (entity.getAccStatus().equals("LOCKED")) {

			return "Your Account LOCKED";
		}

		// create session and store user data in session

		Session.setAttribute("userId", entity.getUserId());

		return "success";
	}

	@Override
	public Boolean signup(SingUpForm form) {

		UserDtlEntity user = userDtlsRepo.findByEmail(form.getEmail());

		if (user != null) {

			return false;

		}

		// TODO: copy data from binding obj to entity obj

		UserDtlEntity entity = new UserDtlEntity();

		BeanUtils.copyProperties(form, entity);

		// TODO: generate Ramdom pwd and set to object

		String temPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(temPwd);

		// TODO: set Account status Locked

		entity.setAccStatus("LOCKED");

		// TODO: Insert Record

		userDtlsRepo.save(entity);

		// TODO: Send email to unlock account

		String to = form.getEmail();
		String subject = "unlock Your Account";
		// String body="<h1>Use below temporary pwd to unlocked your account</h1>";

		StringBuffer body = new StringBuffer("");

		body.append("<h1>Use below temporary pwd to unlocked your account</h1>");
		body.append("Temporary pwd : " + temPwd);
		body.append("</br>");
		body.append(
				"<a href=\"http://localhost:9090/unlock?email=" + to + "\">  Click Here To Unlock Your Account</a>");

		emailutils.sendEmail(to, subject, body.toString());
		return true;
	}

	@Override
	public Boolean unlockAccount(UnlockForm form) {

		UserDtlEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {

			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");

			userDtlsRepo.save(entity);

			return true;

		} else {

			return false;
		}
	}

	@Override
	public Boolean forgotPwd(String email) {

		// check record present in DB with given mail
		UserDtlEntity entity = userDtlsRepo.findByEmail(email);

		// if record not present then send error msg

		if (entity == null) {

			return false;
		}

		// if record is present then send pwd to email and send success msg

		String subject = "Recover PWD";

		String body = "Your Pwd :: " + entity.getPwd();

		emailutils.sendEmail(email, subject, body);

		return true;
	}

}
