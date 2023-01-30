<%@page import="com.ncs.controller.ORSView"%>
<%@page import="com.ncs.controller.LoginCtl"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.LOGIN_CTL%>" method="post">

		<jsp:useBean id="bean" class="com.ncs.beans.UserBean" scope="request"></jsp:useBean>

		<center style="margin-top: 5%">
			<h1>Login</h1>

			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				<font color="red"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</H2>



			<%
				String msg = (String) request.getAttribute("message");
				if (msg != null) {
			%>
			<h2 align="center">
				<font style="color: red"><%=msg%></font>
			</h2>
			<%
				}
			%>

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
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" size=30
						placeholder="Enter LoginId"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"> <font
						color="red" style="position: fixed;"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr style="text-align: justify;">
					<th>Password<font color="red">*</font></th>
					<td><input type="password" name="password" size=30
						placeholder="Enter password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
						color="red" style="position: fixed;"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp; <input
						type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
						&nbsp;</td>
				</tr>
				<tr>
					<th></th>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget
								my password</b></a>&nbsp;</td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>