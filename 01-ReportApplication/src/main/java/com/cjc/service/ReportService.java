package com.cjc.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cjc.entity.CitizenPlan;
import com.cjc.request.SearchRequest;

public interface ReportService {

	public List<String> getPlaneName();

	public List<String> getPlanStatus();

	public List<CitizenPlan> search(SearchRequest request);

	public boolean exportExcel(HttpServletResponse response) throws Exception;

	public boolean exportPdf(HttpServletResponse response) throws Exception;

}
