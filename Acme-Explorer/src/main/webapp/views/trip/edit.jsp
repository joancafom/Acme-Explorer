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

<form:form action="trip/manager/edit.do" modelAttribute="trip">
	
	<!-- Hidden inputs -->
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="price"/>
	<jstl:if test="trip.id == 0">
		<form:hidden path="cancelationReason"/>
	</jstl:if>
	<form:hidden path="sponsorships"/>
	<form:hidden path="stories"/>
	<form:hidden path="notes"/>
	<form:hidden path="audits"/>
	<form:hidden path="tripApplications"/>
	<form:hidden path="tagValues"/>
	<form:hidden path="stages"/>
	<form:hidden path="survivalClasses"/>
	<form:hidden path="manager"/>
	
	<form:label path="title">
		<spring:message code="trip.title"/>
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />
	
	<form:label path="description">
		<spring:message code="trip.description"/>	
	</form:label>
	<form:textarea path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<form:label path="publicationDate">
		<spring:message code="trip.publicationDate"/>
	</form:label>
	<form:input path="publicationDate" placeholder="dd/MM/YYYY HH:mm" />
	<form:errors cssClass="error" path="publicationDate"/>
	<br />
	
	<form:label path="startingDate">
		<spring:message code="trip.startingDate"/>
	</form:label>
	<form:input path="startingDate" placeholder="dd/MM/YYYY HH:mm" />
	<form:errors cssClass="error" path="startingDate"/>
	<br />
	
	<form:label path="endingDate">
		<spring:message code="trip.endingDate"/>
	</form:label>
	<form:input path="endingDate" placeholder="dd/MM/YYYY HH:mm" />
	<form:errors cssClass="error" path="endingDate"/>
	<br />
	
	<form:label path="requirements">
		<spring:message code="trip.requirements"/>
	</form:label>
	<form:textarea path="requirements"/>
	<form:errors cssClass="error" path="requirements"/>
	<br />
	
	<form:label path="ranger">
		<spring:message code="trip.ranger"/>
	</form:label>
	<form:select path="ranger">
		<form:option value="0" label="---"/>
		<form:options items="${rangers}"
			itemValue="id"
			itemLabel="name"/>
	</form:select>
	<form:errors cssClass="error" path="ranger"/>
	<br />
	
	<form:label path="legalText">
		<spring:message code="trip.legalText"/>
	</form:label>
	<form:select path="legalText">
		<form:option value="0" label="---"/>
		<form:options items="${legalTexts}"
			itemValue="id"
			itemLabel="title"/>
	</form:select>
	<form:errors cssClass="error" path="legalText"/>
	<br />
	
	<form:label path="category">
		<spring:message code="trip.category"/>
	</form:label>
	<form:select path="category">
		<form:option value="0" label="---"/>
		<form:options items="${categories}"
			itemValue="id"
			itemLabel="name"/>
	</form:select>
	<form:errors cssClass="error" path="category"/>
	<br />
	
	<jstl:if test="${trip.id != 0}">
		<!-- If the trip is not new a manager can cancel it -->
		<form:label path="cancelationReason">
			<spring:message code="trip.cancelationReason"/>
		</form:label>
		<form:textarea path="cancelationReason"/>
		<form:errors cssClass="error" path="cancelationReason"/>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="trip.save"/>">
	<input type="button" name="cancel" value="<spring:message	code="trip.cancel" />" onclick="javascript: relativeRedir('list.do');" />
	
	
</form:form>