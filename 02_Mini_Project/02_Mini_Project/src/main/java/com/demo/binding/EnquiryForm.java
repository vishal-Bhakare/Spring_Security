package com.demo.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class EnquiryForm {

	@NotNull
	private String studentName;

	@Pattern(regexp = "^\\d{10}$")
	private Long studentphno;

	private String classMode;

	private String courseName;

	private String enquiryStatus;

}
