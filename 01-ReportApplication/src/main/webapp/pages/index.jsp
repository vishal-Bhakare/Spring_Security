<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap demo</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
</head>
<body>

	<div class="container">
		<h3 class="pd-3  pt-3">Report Application</h3>
		<form:form action="search" modelAttribute="search" method="POST">

			<table>
				<tr>
					<td>Plan Name</td>
					<td><form:select path="planName">
							<form:option value="">-select-</form:option>
							<form:options items="${names}" />
						</form:select></td>

					<td>Plan status</td>
					<td><form:select path="PlanStatus">
							<form:option value="">-select-</form:option>
							<form:options items="${statuses}" />

						</form:select></td>

					<td>Gender</td>
					<td><form:select path="gender">
							<form:option value="">-select-</form:option>
							<form:option value="Male">Male</form:option>
							<form:option value="Fe-male">Fe-male</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><form:input path="startDate" type="date" /></td>
					<td>End Date</td>
					<td><form:input path="EndDate" type="date" /></td>
				</tr>
				<tr>
				<td><a href="/" class="btn btn-secondary">Reset</a></td>
					<td><input type="submit" value="search"
						class="btn btn-primary" /></td>
				</tr>
			</table>

		</form:form>
		<hr />
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Sr no</th>
					<th>citizen Name</th>
					<th>Gender</th>
					<th>plane Name</th>
					<th>plane staus</th>
					<th>start date</th>
					<th>End date</th>
					<th>Benefit Amount</th>

				</tr>
			</thead>
			<tbody>

				<c:forEach items="${plans}" var="plan" varStatus="index">

					<tr>
						<td>${index.count}</td>
						<td>${plan.citizenName}</td>
						<td>${plan.gender}</td>
						<td>${plan.planName}</td>
						<td>${plan.planStatus}</td>
						<td>${plan.planStartdate}</td>
						<td>${plan.planEnddate}</td>
						<td>${plan.benefitAmount}</td>
					</tr>

				</c:forEach>
				<tr>
					<td colspan="8" style="text-align: center;"><c:if test="${empty plans }">No Record Found</c:if></td>
				</tr>
			</tbody>

		</table>
		<hr />

		Export : <a href="excel">Excel</a> <a href="pdf">Pdf</a>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
		crossorigin="anonymous"></script>
</body>
</html>
