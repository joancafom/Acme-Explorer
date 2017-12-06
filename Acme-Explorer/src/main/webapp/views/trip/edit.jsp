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

<form:form action="/trip/manager/edit.do" modelAttribute="trip">
	
	<!-- Hidden inputs -->
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="price"/>
	<form:hidden path="publicationDate"/>
	<jstl:if test="trip.id==0">
		<form:hidden path="cancelationReason"/>
	</jstl:if>
	<form:hidden path="manager"/>
	<form:hidden path="sponsorship"/>
	<form:hidden path="stories"/>
	<form:hidden path="tripApplications"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="auditions"/>
	<form:hidden path="notes"/>
	<form:hidden path="sponsorships"/>
	
	<form:label path="title">
		<spring:message code="trip.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="errors" path="title"/>
	
	<form:label path="description">
		<spring:message code="trip.description"/>	
	</form:label>
	<form:textarea path="description"/>
	<form:errors cssClass="errors" path="title"/>
	
	<form:label path="publicationDate">
		<spring:message code="trip.publicationDate"/>
	</form:label>
	<form:input path="publicationDate"/>
	<form:errors cssClass="errors" path="publicationDate"/>
	
	
	<form:label path="startingDate">
		<spring:message code="trip.startingDate"/>
	</form:label>
	<form:input path="startingDate"/>
	<form:errors cssClass="errors" path="startingDate"/>
	
	
	<form:label path="endingDate">
		<spring:message code="trip.endingDate"/>
	</form:label>
	<form:input path="endingDate"/>
	<form:errors cssClass="errors" path="endingDate"/>
	
	<form:label path="ranger">
		<spring:message code="trip.ranger"/>
	</form:label>
	<form:select path="ranger">
		<form:option value="0" label="---"/>
		<form:options items="${rangers}"
			itemValue="id"
			itemLabel="name"/>
	</form:select>
	<form:errors cssClass="errors" path="ranger"/>
	
	<form:label path="legalText">
		<spring:message code="trip.legalText"/>
	</form:label>
	<form:select path="legalText">
		<form:option value="0" label="---"/>
		<form:options items="${legalTexts}"
			itemValue="id"
			itemLabel="name"/>
	</form:select>
	<form:errors cssClass="errors" path="legalText"/>
	
	<form:label path="tags">
		<spring:message code="trip.tags"/>
	</form:label>
	<form:checkboxes items="${tags}" path="tags"/>
	<form:errors cssClass="errors" path="tags"/>
	
	<form:label path="stages">
		<spring:message code="trip.stages"/>
	</form:label>
	<form:checkboxes items="${stages}" path="stages"/>
	<form:errors cssClass="errors" path="stages"/>
	
	<form:label path="category">
		<spring:message code="trip.category"/>
	</form:label>
	<form:select path="category">
		<form:option value="0" label="---"/>
		<form:options items="${categories}"
			itemValue="id"
			itemLabel="name"/>
	</form:select>
	<form:errors cssClass="errors" path="category"/>
	
	<jstl:if test="${trip.id!=0}">
		<!-- If the trip is not new a manager can cancel it -->
		<form:label path="cancelationReason">
			<spring:message code="trip.cancelationReason"/>
		</form:label>
		<form:textarea path="cancelationReason"/>
		<form:errors cssClass="errors" path="cancelationReason"/>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="trip.create"/>">
	<input type="button" name="cancel" value="<spring:message	code="trip.cancel" />" onclick="javascript: relativeRedir('trip/manager/list.do');" />
	
	
</form:form>