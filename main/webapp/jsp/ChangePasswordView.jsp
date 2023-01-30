<%@page import="com.ncs.controller.ChangePasswordCtl"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ChangePassword</title>
</head>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.ncs.beans.UserBean" scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>


			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>



				<tr style="text-align: justify;">
					<th>Old Password<font color="red">*</font></th>
					<td><input type="password" name="oldPassword" size="23.5"
						value=<%=DataUtility.getStringData(request.getParameter("oldPassword"))%>>
						<%-- value=<%=DataUtility.getString(
		request.getParameter("oldPassword") == null ? "" : DataUtility.getString(request.getParameter("oldPassword")))%>> --%>
						<font style="position: fixed" ;	color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr style="text-align: justify;">
					<th>New Password<font color="red">*</font></th>
					<td><input type="password" name="newPassword" size="23.5"
						value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
					: DataUtility.getString(request.getParameter("newPassword")))%>><font
						style="position: fixed" ;	color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>

				<tr style="text-align: justify;">
					<th>Confirm Password<font color="red">*</font></th>
					<td><input type="password" name="confirmPassword" size="23.5"
						value=<%=DataUtility.getStringData(request.getParameter("confirmPassword"))%>>
						<%-- value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? ""
		: DataUtility.getString(request.getParameter("confirmPassword")))%>> --%>
						<font style="position: fixed" ;	color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr style="justify-content: flex-end;">
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
						&nbsp; <input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> &nbsp;</td>
				</tr>

			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>