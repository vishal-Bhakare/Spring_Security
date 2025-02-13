package com.demo.service;

import com.demo.binding.LoginForm;
import com.demo.binding.SignUpForm;
import com.demo.binding.UnlockForm;

public interface UserService {

	public boolean signUp(SignUpForm form);

	public boolean unlockAccount(UnlockForm unlock);

	public String login(LoginForm form);

	public boolean forgotPwd(String email);

}
