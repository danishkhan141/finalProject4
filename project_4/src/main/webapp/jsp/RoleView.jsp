<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="in.co.rays.project_4.controller.RoleCtl"%>
<%@page import="in.co.rays.project_4.controller.BaseCtl"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Role</title>
</head>
<body>
 <form action="<%=ORSView.ROLE_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.rays.project_4.bean.RoleBean"
            scope="request"></jsp:useBean>

        <center>
        
        <h1>
		<%
			if (bean != null && bean.getId() > 0) {
		%>
		<tr>
			<th>Update Role</th>
			</tr>
		<%
			} else {
		%>	
		<tr>
			<th>Add Role</th>
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

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table>
                <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td><input type="text" name="name" placeholder="Enter Your Name"
                        value="<%=DataUtility.getStringData(bean.getName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
                <tr><th style="padding: 3px"></th></tr>
                <tr>
                    <th align="left">Description<span style="color: red">*</span></th>
                    <td><input type="text" name="description" placeholder="Enter the Description"
                        value="<%=DataUtility.getStringData(bean.getDescription())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
                </tr>
                <tr><th style="padding: 3px"></th></tr>
                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=RoleCtl.OP_SAVE%>">&emsp; <input type="submit"
                        name="operation" value="<%=RoleCtl.OP_CANCEL%>"></td>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>

</body>
</html>