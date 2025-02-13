package com.demo.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {

	private PwdUtils() {

	}

	
	public static String generateRandomPwd() {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return RandomStringUtils.random(6, characters);

		// Apache commons library provide RandomStringUtils class
		// in that class we have random() method

	}

}
