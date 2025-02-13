package com.cjc.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cjc.entity.CourseEntity;
import com.cjc.entity.EnquiryStatusEntity;
import com.cjc.repository.CourseEnqRepo;
import com.cjc.repository.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private EnquiryStatusRepo enqstatusrepo;

	@Autowired
	private CourseEnqRepo courserepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		 enqstatusrepo.deleteAll();

		 courserepo.deleteAll();

		CourseEntity c1 = new CourseEntity();

		c1.setCourseName("java");
		CourseEntity c2 = new CourseEntity();

		c2.setCourseName("Python");
		CourseEntity c3 = new CourseEntity();

		c3.setCourseName("DevOps");
		CourseEntity c4 = new CourseEntity();

		c4.setCourseName("AWS");

		courserepo.saveAll(Arrays.asList(c1, c2, c3, c4));

		EnquiryStatusEntity e1 = new EnquiryStatusEntity();

		e1.setStatusName("New");

		EnquiryStatusEntity e2 = new EnquiryStatusEntity();

		e2.setStatusName("Enrolled");

		EnquiryStatusEntity e3 = new EnquiryStatusEntity();

		e3.setStatusName("Lost");

		enqstatusrepo.saveAll(Arrays.asList(e1, e2, e3));
	}
}
