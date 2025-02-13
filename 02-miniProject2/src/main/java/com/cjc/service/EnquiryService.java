package com.cjc.service;

import java.util.List;

import com.cjc.binding.DashboardResponse;
import com.cjc.binding.EnquiryForm;
import com.cjc.binding.EnquirySearchCriteria;

public interface EnquiryService {

	public DashboardResponse getDashboardData(Integer userId);

	public Boolean addEnquiry(EnquiryForm form);

	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria criteria);

	public List<String> getCourseName();

	public EnquiryForm getEnquiry(Integer enqId);

	public List<String> getEnquiryStatuses();
}
