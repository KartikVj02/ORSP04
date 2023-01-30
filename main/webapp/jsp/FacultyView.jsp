<%@page import="java.util.ArrayList"%>
<%@page import="com.ncs.controller.FacultyCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ncs.utils.HTMLUtility"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="java.awt.List"%>
<%@page import="com.ncs.beans.SubjectBean"%>
<%@page import="com.ncs.beans.CourseBean"%>
<%@page import="com.ncs.beans.CollegeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Faculty Registration Page</title>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
</head>

<body>
	<jsp:useBean id="bean" class="com.ncs.beans.FacultyBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.FACULTY_CTL%>" method="post">

		<%
			ArrayList<CollegeBean> colist = (ArrayList<CollegeBean>) request.getAttribute("CollegeList");
		ArrayList<CourseBean> clist = (ArrayList<CourseBean>) request.getAttribute("CourseList");
		ArrayList<SubjectBean> slist = (ArrayList<SubjectBean>) request.getAttribute("SubjectList");

		/* System.out.println(colist.size());
		System.out.println(slist.size());
		System.out.println(clist.size()); */
		%>

		<center>
			<h1>


				<tr>
					<th>Add Faculty</th>
				</tr>

			</h1>

			<div>
				<h3>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
			</div>

			<input type="hidden" name="id" value=<%=bean.getId()%>> <input
				type="hidden" name="createdby" value=<%=bean.getCreatedBy()%>>
			<input type="hidden" name="modifiedby"
				value=<%=bean.getModifiedBy()%>> <input type="hidden"
				name="createdDatetime"
				value=<%=DataUtility.getStringData(bean.getCreatedDateTime())%>>
			<input type="hidden" name="modifiedDatetime"
				value=<%=DataUtility.getStringData(bean.getModifiedDateTime())%>>

			<table>

				<tr>
					<th align="left">First Name<font color="red">*</font> :
					</th>
					</th>
					<td><input type="text" name="firstname"
						placeholder=" Enter First Name" size="25"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstname", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">Last Name<font color="red">*</font> :
					</th>
					<td><input type="text" name="lastname"
						placeholder=" Enter last Name" size="25"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastname", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">Gender<font color="red">*</font> :</th>
					<td>
						<%
							HashMap map = new HashMap();
						map.put("","--------------Select---------------");
						map.put("Male", "Male");
						map.put("Female", "Female");

						String hlist = HTMLUtility.getList("gender", String.valueOf(bean.getGender()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">CollegeName<font color="red">*</font> :</th>
					<td><%=HTMLUtility.getList("collegeid", String.valueOf(bean.getCollegeId()), colist)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("collegeid", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">CourseName<font color="red">*</font> :</th>
					<td><%=HTMLUtility.getList("courseid", String.valueOf(bean.getCourseId()), clist)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("courseid", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">SubjectName<font color="red">*</font> :</th>
					<td><%=HTMLUtility.getList("subjectid", String.valueOf(bean.getSubjectId()), slist)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("subjectid", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">Date Of Birth<font color="red">*</font> :</th>
					<td><input type="text" name="dob"
						placeholder="Enter Date Of Birth" size="25" readonly="readonly"
						id="date" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>


				<tr>
					<th align="left">LoginId<font color="red">*</font> :</th>
					<td><input type="text" name="loginid"
						placeholder=" Enter Login Id" size="25"
						value="<%=DataUtility.getStringData(bean.getEmailId())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("loginid", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">MobileNo <font color="red">*</font> :</th>
					<td><input type="text" name="mobileno" size="25"
						maxlength="10" placeholder=" Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobileno", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>

					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</center>

	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>