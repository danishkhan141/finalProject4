<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="in.co.rays.project_4.controller.CollegeCtl"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>College View</title>
</head>
<body>
	<form action="CollegeCtl" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.project_4.bean.CollegeBean"
			scope="request"></jsp:useBean>

		<center>

			<h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<tr>
					<th>Update College</th>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th>Add College</th>
				</tr>
				<%
					}
				%>
			</h1>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Your Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Address<span style="color: red">*</span></th>
					<td><input type="text" name="address"
						placeholder="Enter Your Address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">State<span style="color: red">*</span></th>
					<td><input type="text" name="state"
						placeholder="Enter Your State"
						value="<%=DataUtility.getStringData(bean.getState())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">City<span style="color: red">*</span></th>
					<td><input type="text" name="city"
						placeholder="Enter Your City"
						value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">PhoneNo<span style="color: red">*</span></th>
					<td><input type="text" name="phoneNo"
						placeholder="Enter Your Phone No"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=CollegeCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=CollegeCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>

			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>

</body>
</html>