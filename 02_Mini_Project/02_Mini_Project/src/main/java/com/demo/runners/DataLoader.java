package com.demo.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.demo.entity.CourseEntity;
import com.demo.entity.EnqStatusEntity;
import com.demo.repo.CourseRepo;
import com.demo.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepo.deleteAll();
		// TODO : save data to Course
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java");
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Python");
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("DevOps");

		courseRepo.saveAll(Arrays.asList(c1, c2, c3));

		statusRepo.deleteAll();
		// TODO : save data to Enqstatus
		EnqStatusEntity e1 = new EnqStatusEntity();
		e1.setStatusName("New");
		EnqStatusEntity e2 = new EnqStatusEntity();
		e2.setStatusName("Enrolled");
		EnqStatusEntity e3 = new EnqStatusEntity();
		e3.setStatusName("Lost");

		statusRepo.saveAll(Arrays.asList(e1, e2, e3));

	}

}
