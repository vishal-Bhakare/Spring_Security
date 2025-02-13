package com.demo.service;

import java.util.List;

import com.demo.binding.DashboardResponse;
import com.demo.binding.EnquiryForm;
import com.demo.binding.EnquirySearchCriteria;
import com.demo.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashboardResponse getDashBoardData(Integer userId);

	public List<String> getCourseName();

	public List<String> getEnqStatus();

	public boolean addEnquiry(EnquiryForm form);

	public List<StudentEnqEntity> getEnquiries();

	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId);

}
