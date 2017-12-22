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

<form:form action="trip/manager/cancel.do" modelAttribute="trip">

		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="ticker"/>
		<form:hidden path="title"/>
		<form:hidden path="description"/>
		<form:hidden path="price"/>
		<form:hidden path="publicationDate"/>
		<form:hidden path="startingDate"/>
		<form:hidden path="endingDate"/>
		<form:hidden path="requirements"/>
		<form:hidden path="sponsorships"/>
		<form:hidden path="stories"/>
		<form:hidden path="notes"/>
		<form:hidden path="audits"/>
		<form:hidden path="tripApplications"/>
		<form:hidden path="tagValues"/>
		<form:hidden path="legalText"/>
		<form:hidden path="stages"/>
		<form:hidden path="category"/>
		<form:hidden path="ranger"/>
		<form:hidden path="survivalClasses"/>
		<form:hidden path="manager"/>
		
		<form:label path="cancelationReason">
				<spring:message code="trip.cancelationReason"/>
		</form:label>
		<form:textarea path="cancelationReason" required="required"/>
		<form:errors cssClass="error" path="cancelationReason"/>
		
		<br/>
		
		<input type="submit" value="<spring:message code="trip.cancelTrip"/>">
		<input type="button" name="cancel" value="<spring:message code="trip.cancel"/>" onclick="javascript: relativeRedir('trip/manager/list.do');" />
		
</form:form>