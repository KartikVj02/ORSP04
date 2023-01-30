<%@page import="com.ncs.beans.MarksheetBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ncs.utils.HTMLUtility"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@page import="com.ncs.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marksheet</title>
</head>
<body>

	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%@ include file="Header.jsp"%>


		<jsp:useBean id="bean" class="com.ncs.beans.MarksheetBean"
			scope="request"></jsp:useBean>

		<%
			List arrList = (List) request.getAttribute("studentList");
			//System.out.println(arrList.size());
		%>

		<center>
			<%
				if (bean != null && bean.getId() > 0) {
			%>
			<h1>Update Marksheet</h1>
			<%
				} else {
			%>
			<h1>Add Marksheet</h1>
			<%
				}
			%>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
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
					<th>Rollno<font style="color: red">*</font></th>
					<td><input type="text" name="rollNo" size=23
						value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>> <font
						style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>
				<tr style="text-align: justify;">
					<th align="left">Name<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("name", String.valueOf(bean.getStudentId()), arrList)%>

						<font color="red" style="position: fixed;"><%=ServletUtility.getErrorMessage("name", request)%></font>
					</td>
				</tr>
				<tr style="text-align: justify;">
					<th>Physics<span style="color: red">*</span></th>
					<td><input type="text" name="physics" size=23
						value="<%=(DataUtility.getStringData(bean.getPhysics()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPhysics()))%>"><font
						color="red" style="position: fixed;"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
				</tr>
				<tr style="text-align: justify;">
					<th>Chemistry<span style="color: red">*</span></th>
					<td><input type="text" name="chemistry" size=23
						value="<%=(DataUtility.getStringData(bean.getChemistry()).equals("0") ? ""
					: DataUtility.getStringData(bean.getChemistry()))%>"><font
						color="red" style="position: fixed;"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
				</tr>
				<tr style="text-align: justify;">
					<th>Maths<span style="color: red">*</span></th>
					<td><input type="text" name="maths" size=23
						value="<%=(DataUtility.getStringData(bean.getMaths()).equals("0") ? ""
					: DataUtility.getStringData(bean.getMaths()))%>">
						<font color="red" style="position: fixed;"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>

				</tr>
				<tr>
					<th></th>
					<%
						if (bean.getId() > 0 && bean != null) {
					%>
					<td><input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_UPDATE%>"> &nbsp; <input
						type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>">
					</td>


					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=MarksheetCtl.OP_SAVE%>">
						&nbsp;&nbsp; <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_RESET%>">
					</td>

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