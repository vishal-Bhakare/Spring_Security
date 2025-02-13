package com.cjc.service;

import com.cjc.binding.LoginForm;
import com.cjc.binding.SingUpForm;
import com.cjc.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm form);
	
	public Boolean signup(SingUpForm form);
	
	public Boolean unlockAccount(UnlockForm form);
	
	public Boolean forgotPwd(String email);
	
	

}
