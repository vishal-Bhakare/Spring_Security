package com.cjc.runner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cjc.entity.CitizenPlan;
import com.cjc.repository.CitizenPlanRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CitizenPlanRepository repo;

	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		repo.deleteAll();
		
		
		// cash plan data
		CitizenPlan c = new CitizenPlan();

		c.setCitizenName("Pravin");
		c.setGender("male");
		c.setPlanName("Cash");
		c.setPlanStatus("Approved");
		c.setPlanStartdate(LocalDate.now());
		c.setPlanEnddate(LocalDate.now().plusMonths(6));
		c.setBenefitAmount(5000.00);

		CitizenPlan c1 = new CitizenPlan();

		c1.setCitizenName("Saurabh");
		c1.setGender("male");
		c1.setPlanName("Cash");
		c1.setPlanStatus("Deined");
		c1.setPlanStartdate(LocalDate.now());
		c1.setPlanEnddate(LocalDate.now().plusMonths(6));
		c1.setDenialReson("Rent Income");

		CitizenPlan c2 = new CitizenPlan();

		c2.setCitizenName("Priyanka");
		c2.setGender("Fe-male");
		c2.setPlanName("Cash");
		c2.setPlanStatus("Teminated");
		c2.setPlanStartdate(LocalDate.now().minusMonths(4));
		c2.setPlanEnddate(LocalDate.now().plusMonths(6));
		c2.setBenefitAmount(15000.00);
		c2.setTerminateDate(LocalDate.now());
		c2.setTerminationReason("Employeed");

		// Food plan data
		CitizenPlan c3 = new CitizenPlan();

		c3.setCitizenName("Vishal");
		c3.setGender("male");
		c3.setPlanName("Food");
		c3.setPlanStatus("Approved");
		c3.setPlanStartdate(LocalDate.now());
		c3.setPlanEnddate(LocalDate.now().plusMonths(6));
		c3.setBenefitAmount(5000.00);

		CitizenPlan c4 = new CitizenPlan();

		c4.setCitizenName("Sahil");
		c4.setGender("male");
		c4.setPlanName("Food");
		c4.setPlanStatus("Deined");
		c4.setPlanStartdate(LocalDate.now());
		c4.setPlanEnddate(LocalDate.now().plusMonths(6));
		c4.setDenialReson("Property Income");

		CitizenPlan c5 = new CitizenPlan();

		c5.setCitizenName("Shital");
		c5.setGender("Fe-male");
		c5.setPlanName("Food ");
		c5.setPlanStatus("Teminated");
		c5.setPlanStartdate(LocalDate.now().minusMonths(4));
		c5.setPlanEnddate(LocalDate.now().plusMonths(6));
		c5.setBenefitAmount(15000.00);
		c5.setTerminateDate(LocalDate.now());

		c5.setTerminationReason("Employeed");

		// medical plan data
		CitizenPlan c6 = new CitizenPlan();

		c6.setCitizenName("Shubham");
		c6.setGender("male");
		c6.setPlanName("Medical");
		c6.setPlanStatus("Approved");
		c6.setPlanStartdate(LocalDate.now());
		c6.setPlanEnddate(LocalDate.now().plusMonths(6));
		c6.setBenefitAmount(5000.00);

		CitizenPlan c7 = new CitizenPlan();

		c7.setCitizenName("Sudhanshu");
		c7.setGender("male");
		c7.setPlanName("Medical");
		c7.setPlanStatus("Deined");
		c7.setPlanStartdate(LocalDate.now());
		c7.setPlanEnddate(LocalDate.now().plusMonths(6));
		c7.setDenialReson("Gov Job");

		CitizenPlan c8 = new CitizenPlan();

		c8.setCitizenName("Devta");
		c8.setGender("Fe-male");
		c8.setPlanName("Cash");
		c8.setPlanStatus("Teminated");
		c8.setPlanStartdate(LocalDate.now().minusMonths(4));
		c8.setPlanEnddate(LocalDate.now().plusMonths(6));
		c8.setBenefitAmount(15000.00);
		c8.setTerminateDate(LocalDate.now());
		c8.setTerminationReason("Employeed");

		// Employement plan data
		CitizenPlan c9 = new CitizenPlan();

		c9.setCitizenName("Shubham");
		c9.setGender("male");
		c9.setPlanName("Employement");
		c9.setPlanStatus("Approved");
		c9.setPlanStartdate(LocalDate.now());
		c9.setPlanEnddate(LocalDate.now().plusMonths(6));
		c9.setBenefitAmount(5000.00);

		CitizenPlan c10 = new CitizenPlan();

		c10.setCitizenName("Dhiraj");
		c10.setGender("male");
		c10.setPlanName("Employement");
		c10.setPlanStatus("Deined");
		c10.setPlanStartdate(LocalDate.now());
		c10.setPlanEnddate(LocalDate.now().plusMonths(6));
		c10.setDenialReson("Rent Income");

		CitizenPlan c11 = new CitizenPlan();

		c11.setCitizenName("Priti");
		c11.setGender("Fe-male");
		c11.setPlanName("Employement");
		c11.setPlanStatus("Teminated");
		c11.setPlanStartdate(LocalDate.now().minusMonths(4));
		c11.setPlanEnddate(LocalDate.now().plusMonths(6));
		c11.setBenefitAmount(15000.00);
		c11.setTerminateDate(LocalDate.now());
		c11.setTerminationReason("Employeed");

		List<CitizenPlan> list = Arrays.asList(c, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);
		repo.saveAll(list);

	}

}
