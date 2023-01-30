<%@page import="com.ncs.utils.DataUtility"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.controller.CollegeCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>College</title>
</head>
<form action="CollegeCtl" method="POST">
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="com.ncs.beans.CollegeBean"
		scope="request"></jsp:useBean>

	<center>
		<h1>
			<%
				if (bean != null && bean.getId() > 0) {
			%>
			<tr>
				<th><font>Update College</font></th>
			</tr>

			<%
				} else {
			%>

			<tr>
				<th><font>Add College</font></th>
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
			value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

		<table>
			<tr style="text-align: justify;">
				<th>Name<span style="color: red">*</span></th>
				<td><input type="text" name="name"
					value="<%=DataUtility.getStringData(bean.getName())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
			</tr>
			<tr style="text-align: justify;">
				<th>Address<span style="color: red">*</span></th>
				<td><input type="text" name="address"
					value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
			</tr>
			<tr style="text-align: justify;">
				<th>State<span style="color: red">*</span></th>
				<td><input type="text" name="state"
					value="<%=DataUtility.getStringData(bean.getState())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
			</tr>
			<tr style="text-align: justify;">
				<th>City<span style="color: red">*</span></th>
				<td><input type="text" name="city"
					value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
			</tr>
			<tr style="text-align: justify;">
				<th>PhoneNo<span style="color: red">*</span></th>
				<td><input type="text" name="phoneNo"
					value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
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