package com.cjc.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjc.binding.DashboardResponse;
import com.cjc.binding.EnquiryForm;
import com.cjc.binding.EnquirySearchCriteria;
import com.cjc.entity.CourseEntity;
import com.cjc.entity.EnquiryStatusEntity;
import com.cjc.entity.StudentEnqEntity;
import com.cjc.entity.UserDtlEntity;
import com.cjc.repository.CourseEnqRepo;
import com.cjc.repository.EnquiryStatusRepo;
import com.cjc.repository.StudentEnqrepo;
import com.cjc.repository.UserDtlsRepo;
import com.cjc.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userrepo;

	@Autowired
	private CourseEnqRepo courserepo;

	@Autowired
	private EnquiryStatusRepo enqstatusrepo;

	@Autowired
	private StudentEnqrepo sturepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlEntity> findById = userrepo.findById(userId);

		if (findById.isPresent()) {

			UserDtlEntity userDtlEntity = findById.get();

			List<StudentEnqEntity> enquiries = userDtlEntity.getEnquiries();

			int totalcnt = enquiries.size();

			int enrolledcnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			int lostcnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost")).collect(Collectors.toList())
					.size();

			response.setTotalEnquiries(totalcnt);
			response.setEnrolledcnt(enrolledcnt);
			response.setLostcnt(lostcnt);

		}

		return response;
	}

	@Override
	public Boolean addEnquiry(EnquiryForm form) {

		StudentEnqEntity entity = new StudentEnqEntity();

		BeanUtils.copyProperties(form, entity);

		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlEntity userDtlEntity = userrepo.findById(userId).get();

		entity.setUser(userDtlEntity);

		sturepo.save(entity);

		return true;
	}

	@Override
	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCourseName() {

		List<CourseEntity> findAll = courserepo.findAll();

		List<String> names = new ArrayList<String>();

		for (CourseEntity entity : findAll) {

			names.add(entity.getCourseName());

		}

		return names;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getEnquiryStatuses() {

		List<EnquiryStatusEntity> findAll = enqstatusrepo.findAll();

		List<String> statusList = new ArrayList<String>();

		for (EnquiryStatusEntity entity : findAll) {

			statusList.add(entity.getStatusName());

		}

		return statusList;
	}

}
