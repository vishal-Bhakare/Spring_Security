package com.cjc.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.cjc.entity.CitizenPlan;
import com.cjc.repository.CitizenPlanRepository;
import com.cjc.request.SearchRequest;
import com.cjc.service.ReportService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planRepo;

	@Override
	public List<String> getPlaneName() {

		return planRepo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {

		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {

		CitizenPlan entity = new CitizenPlan();

		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {

			entity.setPlanName(request.getPlanName());
		}

		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {

			entity.setPlanStatus(request.getPlanStatus());
		}

		if (null != request.getGender() && !"".equals(request.getGender())) {

			entity.setGender(request.getGender());
		}

		if (null != request.getStartDate() && !"".equals(request.getStartDate())) {

			String startDate = request.getStartDate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

			LocalDate localDate = LocalDate.parse(startDate, formatter);

			entity.setPlanStartdate(localDate);
		}

		if (null != request.getEndDate() && !"".equals(request.getEndDate())) {

			String EndDate = request.getEndDate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

			LocalDate localDate = LocalDate.parse(EndDate, formatter);

			entity.setPlanEnddate(localDate);
		}

		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {
		// Workbook workbook = new XSSFWorkbook();
		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet("Plans-Data");
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Id");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("plan Name");
		headerRow.createCell(3).setCellValue("plan status");
		headerRow.createCell(4).setCellValue("plan start Date");
		headerRow.createCell(5).setCellValue("plan End Date");
		headerRow.createCell(6).setCellValue("Benifit Amount");

		List<CitizenPlan> records = planRepo.findAll();
		int dataRowIndex = 1;

		for (CitizenPlan plan : records) {

			Row dataRow = sheet.createRow(dataRowIndex);

			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getCitizenName());
			dataRow.createCell(2).setCellValue(plan.getPlanName());
			dataRow.createCell(3).setCellValue(plan.getPlanStatus());

			if (null != plan.getPlanStartdate()) {
				dataRow.createCell(4).setCellValue(plan.getPlanStartdate() + "");
			} else {
				dataRow.createCell(4).setCellValue("N/A");

			}

			if (null != plan.getPlanEnddate()) {
				dataRow.createCell(5).setCellValue(plan.getPlanEnddate() + "");
			} else {
				dataRow.createCell(5).setCellValue("N/A");

			}
			if (null != plan.getBenefitAmount()) {
				dataRow.createCell(6).setCellValue(plan.getBenefitAmount());
			} else {
				dataRow.createCell(6).setCellValue("N/A");
			}

			dataRowIndex++;
		}

		ServletOutputStream outputStream = response.getOutputStream();

		workbook.write(outputStream);
		workbook.close();

		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph

		Paragraph p = new Paragraph("Citizen plan Info", fontTiltle);

		// Aligning the paragraph in document
		p.setAlignment(p.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(6);
		
		table.setSpacingBefore(6);

		table.addCell("id");
		table.addCell("citizen Name");
		table.addCell("plan Name");
		table.addCell("plan Staus");
		table.addCell("start date");
		table.addCell("End date");

		List<CitizenPlan> plans = planRepo.findAll();

		for (CitizenPlan plan : plans) {

			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			table.addCell(plan.getPlanStartdate() + "");
			table.addCell(plan.getPlanEnddate() + "");

		}

		document.add(table);

		document.close();

		return true;
	}

}
