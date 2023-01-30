<%-- <%@page import="java.text.NumberFormat.Style"%> --%>
<%@page import="com.ncs.beans.UserBean"%>
<%@page import="com.ncs.utils.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@page import="com.ncs.controller.MyProfileCtl"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MyProfile</title>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#date").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
<body>

	<%-- <%
		UserBean bean = (UserBean) session.getAttribute("user");
	%> --%>
	<jsp:useBean id="bean" class="com.ncs.beans.UserBean"
		scope="request"></jsp:useBean>
	<form method="post" action="<%=ORSView.MY_PROFILE_CTL%>">
		<%@ include file="Header.jsp"%>
		<center>
			<table>
				<h1>My Profile</h1>

				<tr style="text-align: justify;">
					<H2>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>

						<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
						</font>

					</H2>
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" readonly="readonly"
						size=25 value="<%=DataUtility.getStringData(bean.getLogin())%>"
						readonly="readonly"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr style="text-align: justify;">
					<th>First Name<font color="red">*</font></th>
					<td><input type="text" name="firstName" size=25
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr style="text-align: justify;">
					<th>Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName" size=25
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr style="text-align: justify;">
					<th>Gender<font color="red">*</font></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					</td>
				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr style="text-align: justify;">
					<th>Mobile No<font color="red">*</font></th>
					<td><input type="text" name="mobile" size=25
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr style="text-align: justify;">
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text" name="dob" id="date" size="25"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td><font style="position: fixed" ; color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<%-- <tr>
			<th>Date Of Birth (mm/dd/yyyy)</th>
			<td><input type="text" name="dob" readonly="readonly"
				value="<%=DataUtility.getDateString(bean.getDob())%>"> <a
				href="javascript:getCalendar(document.forms[0].dob);"> <img
					src="../img/cal.jpg" width="16" height="15" border="0"
					alt="Calender">
			</a><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
		</tr> --%>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>"> &nbsp; <input
						type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE%>"></input>
						&nbsp;</td>
				</tr>

			</table>

		</center>

	</form>

	<%@ include file="Footer.jsp"%>
</body>
</html>