<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Create a Trip -->

<form:form action="tripApplication/explorer/cancel.do" modelAttribute="tripApplication">

	<jstl:if test="${tripApplication.id!=0}">
	
		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="explorer"/>
		<form:hidden path="moment"/>
		<form:hidden path="trip"/>
		<form:hidden path="status"/>
		<form:hidden path="rejectionReason"/>
		<form:hidden path="trip"/>
		
		<jstl:if test="${tripApplication.status == 'ACCEPTED'}">
			<input type="submit" value="<spring:message code="tripApplication.cancel"/>">
		</jstl:if>
		
		<input type="button" name="cancel" value="<spring:message code="goBack"/>" onclick="javascript: relativeRedir('tripApplication/explorer/list.do');" />
		
	</jstl:if>
		
</form:form>