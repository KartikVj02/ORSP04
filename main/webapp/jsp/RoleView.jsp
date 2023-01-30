
<%@page import="com.ncs.controller.RoleCtl"%>
<%@page import="com.ncs.utils.ServletUtility"%>
<%@page import="com.ncs.utils.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Role</title>
</head>
<body>
	<form action="<%=ORSView.ROLE_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.ncs.beans.RoleBean" scope="request"></jsp:useBean>

		<center>
			<%
				if (bean != null && bean.getId() > 0) {
			%>
			<h1>Update Role</h1>
			<%
				} else {
			%>
			<h1>Add Role</h1>
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
				<tr>
					<th style="text-align: justify;">Name<font color="red">*</font></th>
					<td><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th style="text-align: justify;">Description<font color="red">*</font></th>
					<td><input type="text" name="description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr><th></th>                
           
                <%if(bean.getId()>0) {%>    
                <td colspan="2">
                     &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>"> 
                   
                    &nbsp;  &nbsp; <input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>"></td>
                <%}else{ %>
                	<td colspan="2">
                     &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>"> 
                     &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>"></td>
                <%} %>
                </tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>