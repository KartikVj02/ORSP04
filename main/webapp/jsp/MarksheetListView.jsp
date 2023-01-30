<%@page import="com.ncs.controller.MarksheetListCtl"%>
<%@page import="com.ncs.utils.HTMLUtility"%>
<%@page import="com.ncs.beans.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marksheet List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>
</head>
<body>
	<jsp:useBean id="bean" class="com.ncs.beans.MarksheetBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">
		<%@include file="Header.jsp"%>

		<%
			List l = (List) request.getAttribute("rollNo");

			int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
		%>

		<center>
			<div align="center">
				<h1>Marksheet List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
				<h3>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<MarksheetBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<table width="100%" align="center">
				<tr>
					<td align="center"><label> Student Name :</font></label> <input
						type="text" name="name" placeholder="Enter Student Name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&emsp; <label>RollNo :</label> <%=HTMLUtility.getList("rollNo123", ServletUtility.getParameter("rollNo123", request), l)%>
						&nbsp; <input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>





			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: skyblue">
					<th><input type="checkbox" id="select_all" name="select">
						Select All.</th>
					<th>ID</th>
					<th>RollNo</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Total</th>
					<th>Percentage</th>
					<th>Result</th>
					<th>Edit</th>
				</tr>
				<%
					while (it.hasNext()) {
							bean = it.next();

							int phy = DataUtility.getInt(DataUtility.getStringData(bean.getPhysics()));
							int chem = DataUtility.getInt(DataUtility.getStringData(bean.getChemistry()));
							int math = DataUtility.getInt(DataUtility.getStringData(bean.getMaths()));
							int total = (phy + chem + math);
							float perc = total / 3;
				%>

				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>">
					<td><%=index++%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>
					<td><%=total%></td>
					<td><%=((perc) + "%")%></td>

					<td>
						<%
							if (phy >= 33 && chem >= 33 && math >= 33) {
						%> <span style="color: green"> Pass</span> <%
 	} else {
 %> <span style="color: red"> Fail</span> <%
 	}
 %>
					</td>
					<td><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>

				</tr>

				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						style="margin-bottom: 30px"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						style="margin-bottom: 30px"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						style="margin-bottom: 30px"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						style="margin-bottom: 30px" value="<%=MarksheetListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						style="margin-bottom: 30px" value="<%=MarksheetListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>

				</tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=MarksheetListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</br>
	</br>
	</center>


	<%@include file="Footer.jsp"%>
</body>
</html>