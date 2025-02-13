package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.binding.DashboardResponse;
import com.demo.binding.EnquiryForm;
import com.demo.binding.EnquirySearchCriteria;
import com.demo.constants.AppConstants;
import com.demo.entity.CourseEntity;
import com.demo.entity.EnqStatusEntity;
import com.demo.entity.StudentEnqEntity;
import com.demo.entity.UserDtlsEntity;
import com.demo.repo.CourseRepo;
import com.demo.repo.EnqStatusRepo;
import com.demo.repo.StudentEnqRepo;
import com.demo.repo.UserDtlsRepo;

@Service
public class EnquiryServiceIMPL implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private CourseRepo courcesRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private StudentEnqRepo studentEnqRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashBoardData(Integer userId) {
		DashboardResponse response = new DashboardResponse();
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity entity = findById.get();
			List<StudentEnqEntity> enquiries = entity.getEnquiries();

			int totalEnquiries = enquiries.size();

			int enrolledCnt = enquiries.stream().filter(e -> AppConstants.STR_ENROLLED.equals(e.getEnquiryStatus()))
					.collect(Collectors.toList()).size();

			int lostCnt = enquiries.stream().filter(e -> AppConstants.STR_LOST.equals(e.getEnquiryStatus()))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiries(totalEnquiries);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
		}
		return response;
	}

	@Override
	public List<String> getCourseName() {
		List<CourseEntity> findAll = courcesRepo.findAll();

		ArrayList<String> names = new ArrayList<String>();
		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();

		ArrayList<String> statusList = new ArrayList<String>();
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());
		}
		return statusList;
	}

	@Override
	public boolean addEnquiry(EnquiryForm form) {
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, entity);

		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);

		UserDtlsEntity userDtlsEntity = userDtlsRepo.findById(userId).get();
		entity.setUser(userDtlsEntity);

		studentEnqRepo.save(entity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}

		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
		
		UserDtlsEntity userDtlsEntity = findById.get();
		
		List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
		
			//filter Logic
			
			if (null !=criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
				enquiries = enquiries.stream()
									 .filter(e -> e.getCourseName().equals(criteria.getCourseName()))
									 .collect(Collectors.toList());
			}
			
			if (null !=criteria.getEnquiryStatus() && !"".equals(criteria.getEnquiryStatus())) {
				enquiries = enquiries.stream()
									 .filter(e -> e.getEnquiryStatus().equals(criteria.getEnquiryStatus()))
									 .collect(Collectors.toList());
			}
			
			if (null !=criteria.getClassMode() && !"".equals(criteria.getClassMode() )) {
				enquiries = enquiries.stream()
									 .filter(e -> e.getClassMode() .equals(criteria.getClassMode() ))
									 .collect(Collectors.toList());
			}
		
			return enquiries;
		}
		return null;
	}
	
}
