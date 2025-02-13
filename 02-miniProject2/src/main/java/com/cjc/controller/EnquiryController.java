package com.cjc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cjc.binding.DashboardResponse;
import com.cjc.binding.EnquiryForm;
import com.cjc.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession Session;

	@Autowired
	private EnquiryService enqservice;

	@GetMapping("/logout")
	public String logOut() {

		Session.invalidate();

		return "index";

	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) Session.getAttribute("userId");

		DashboardResponse dashboardData = enqservice.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		// get course for drop down

		List<String> courseName = enqservice.getCourseName();

		// get enq status for drop down

		List<String> enquiryStatuses = enqservice.getEnquiryStatuses();

		// create bindind class object

		EnquiryForm form = new EnquiryForm();

		model.addAttribute("courseName", courseName);
		model.addAttribute("StatusNames", enquiryStatuses);
		model.addAttribute("formObj", form);

		return "add-enquiry";

	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm form, Model model) {

		Boolean status = enqservice.addEnquiry(form);

		if (status) {
			model.addAttribute("succMsg", "Enquiry Added");
		} else {

			model.addAttribute("errMsg", "problem Occured");
		}

		return "add-enquiry";
	}

	@GetMapping("/enquires")
	public String viewEnquirypage() {

		return "view-enquiries";

	}

}
