package com.cjc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjc.binding.LoginForm;
import com.cjc.binding.SingUpForm;
import com.cjc.binding.UnlockForm;
import com.cjc.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public String handleSingUp(@ModelAttribute("user") SingUpForm form, Model model) {

		// System.out.println(form);

		boolean status = userService.signup(form);

		if (status) {

			model.addAttribute("succMsg", "Check Your Email, Password will be Send");

		} else {
			model.addAttribute("errMsg", "Please Enter Unique Email");
		}

		return "signup";

	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {

		model.addAttribute("user", new SingUpForm());

		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm form = new UnlockForm();

		form.setEmail(email);

		model.addAttribute("unlock", form);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlockform, Model model) {

		System.out.println(unlockform);

		if (unlockform.getNewPwd().equals(unlockform.getConfirmPwd())) {

			Boolean status = userService.unlockAccount(unlockform);

			if (status) {

				model.addAttribute("succMsg", "Your Account Unlocked SuccessFully");
			} else {
				model.addAttribute("errMsg", "Given Temp Password Is incorrect, Please Check Your mail");
			}

		} else {

			model.addAttribute("errMsg", "New Pwd and tem Pwd should be Same");

		}

		return "unlock";

	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());

		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form, Model model) {

		System.out.println(form);

		String status = userService.login(form);

		if (status.contains("success")) {

			return "redirect:/dashboard";
		}

		model.addAttribute("errMsg", status);

		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {

		return "forgotPwd";
	}

	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model) {

		System.out.println(email);
		
		Boolean status = userService.forgotPwd(email);
		
		if(status) {
			
			model.addAttribute("succMsg","Pwd send to the mail");
		}else {
			
			
			model.addAttribute("errMsg","Invalid  mail");
		}
		
		
		
		
		
		
		
		
		return "forgotPwd";
	}
}
