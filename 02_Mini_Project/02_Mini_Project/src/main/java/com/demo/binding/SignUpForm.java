package com.demo.binding;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class SignUpForm {

	private String name;

	@Email
	private String email;

	private Long phno;

}
