<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.controller.ForgetPasswordCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forget password</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
		<div>
			<div align="center">
				<h1>Forgot your password?</h1>
				<h3 style="margin-left: 10%;">Submit your email address and we
					will send you password.</h3>
			</div>
			<h3 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>

			<table align="center">
				<tr>
					<td>Email Id :</td>
					<td><input type="text" name="login" value="" size=27
						placeholder="Enter Id here"></td>
					<td><input type="submit" name="operation"
						value="<%=ForgetPasswordCtl.OP_GO%>"></td>
				</tr>
				<tr>
					<td style="padding: 5px"></td>
				</tr>
			</table>
	</form>

	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>