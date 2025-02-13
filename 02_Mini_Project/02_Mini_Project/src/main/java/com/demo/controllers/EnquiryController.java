package com.demo.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.binding.DashboardResponse;
import com.demo.binding.EnquiryForm;
import com.demo.binding.EnquirySearchCriteria;
import com.demo.constants.AppConstants;
import com.demo.entity.StudentEnqEntity;
import com.demo.repo.StudentEnqRepo;
import com.demo.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enquiryService;

	@Autowired
	private StudentEnqRepo studentEnqRepo;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		DashboardResponse dashboardData = enquiryService.getDashBoardData(userId);
		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";
	}

	private void initForm(Model model) {
		// TODO : Get cources for drop-down
		List<String> courses = enquiryService.getCourseName();

		// TODO : Get Enq-Status for drop-down
		List<String> enqStatus = enquiryService.getEnqStatus();

		// TODO : create binding class obj
		EnquiryForm formObj = new EnquiryForm();

		// TODO : set data in model obj
		model.addAttribute("courseName", courses);
		model.addAttribute("statusName", enqStatus);
		model.addAttribute("formObj", formObj);

	}

	@GetMapping("/addEnquiry")
	public String addEnquiryPage(Model model) {
		initForm(model);

		return "addEnquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm form, Model model) {
		// TODO : Save Enquiry Data
		boolean status = enquiryService.addEnquiry(form);
		if (status) {
			model.addAttribute(AppConstants.STR_SUCCESS_MSG, "Enquiry Added Successfully");
			initForm(model);
		} else {
			model.addAttribute(AppConstants.STR_ERROR_MSG, "Problem Occured");
		}

		return "addEnquiry";
	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		List<StudentEnqEntity> enquiries = enquiryService.getEnquiries();
		model.addAttribute("enquiries", enquiries);

		return "viewEnquiry";
	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnquiryStatus(status);
		criteria.setClassMode(mode);

		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);

		List<StudentEnqEntity> filteredEnqs = enquiryService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquiries", filteredEnqs);

		return "filter-enquiries";
	}

	@GetMapping("/edit")
	public String editEnq(@RequestParam("userId") Integer userId, Model model) {
		Optional<StudentEnqEntity> findById = studentEnqRepo.findById(userId);

		if (findById.isPresent()) {
			StudentEnqEntity studentEnqEntity = findById.get();

			// get courses for the drop down
			List<String> courseNames = enquiryService.getCourseName();

			// get enq status for drop down
			List<String> enqStatus = enquiryService.getEnqStatus();

			// create binding class object
			EnquiryForm formObj = new EnquiryForm();
			BeanUtils.copyProperties(studentEnqEntity, formObj);

			// set data in model object
			model.addAttribute("courseName", courseNames);
			model.addAttribute("statusName", enqStatus);
			model.addAttribute("formObj", formObj);
		}

		return "addEnquiry";
	}

}
