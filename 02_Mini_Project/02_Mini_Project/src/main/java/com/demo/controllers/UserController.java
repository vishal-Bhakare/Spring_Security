package com.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.binding.LoginForm;
import com.demo.binding.SignUpForm;
import com.demo.binding.UnlockForm;
import com.demo.constants.AppConstants;
import com.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userService.signUp(form);
		if (status) {
			model.addAttribute(AppConstants.STR_SUCCESS_MSG, "Account Created | Please Check Your Email");

		} else {
			model.addAttribute(AppConstants.STR_ERROR_MSG, "Account Already Exists | Please Try Unique Email");
		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);

		model.addAttribute(AppConstants.STR_UNLOCK, unlockFormObj);
		return AppConstants.STR_UNLOCK;
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);
			if (status) {
				model.addAttribute(AppConstants.STR_SUCCESS_MSG, "Your Account UNLOCKED Successfully | Please Login");
			} else {
				model.addAttribute(AppConstants.STR_ERROR_MSG, "Given Temporary Pwd is incorrect | Check Your Email");
			}
		} else {
			model.addAttribute(AppConstants.STR_ERROR_MSG, "New Pwd and Confirm Pwd should be same");
		}

		return AppConstants.STR_UNLOCK;
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String handleLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		String status = userService.login(loginForm);

		if (status.contains(AppConstants.STR_SUCCESS)) {
			// To Load Dashbard Page
			// return "dashboard";

			// redirect req to dashboard method
			return "redirect:/dashboard";
		}
		model.addAttribute(AppConstants.STR_ERROR_MSG, status);
		return "login";
	}

	@GetMapping("/forgotPwd")
	public String forgotPage() {
		return "forgotPwd";
	}

	@PostMapping("/forgotPwd")
	public String handleForgotPage(@RequestParam("email") String email, Model model) {
		boolean status = userService.forgotPwd(email);
		if (status) {
			model.addAttribute(AppConstants.STR_SUCCESS_MSG, "Please Check Your Email");
		} else {
			model.addAttribute(AppConstants.STR_ERROR_MSG, "Please Enter Valid Email");
		}

		return "forgotPwd";
	}

}
