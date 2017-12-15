<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="/curriculum/ranger/edit.do" modelAttribute="curriculum">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	<form:hidden path="ranger"/>
	
	<h1><spring:message code="curriculum.personalRecord"/></h1>
	
	<form:label path="personalRecord.fullName">
		<spring:message code="personalRecord.fullName"/>
	</form:label>
	<form:input path="personalRecord.fullName"/>
	<form:errors cssClass="errors" path="personalRecord.fullName"/>
	
	<form:label path="personalRecord.photo">
		<spring:message code="personalRecord.photo"/>
	</form:label>
	<form:input path="personalRecord.photo"/>
	<form:errors cssClass="errors" path="personalRecord.photo"/>
	
	<form:label path="personalRecord.email">
		<spring:message code="personalRecord.email"/>
	</form:label>
	<form:input path="personalRecord.email"/>
	<form:errors cssClass="errors" path="personalRecord.email"/>
	
	<form:label path="personalRecord.phoneNumber">
		<spring:message code="personalRecord.phoneNumber"/>
	</form:label>
	<form:input path="personalRecord.phoneNumber"/>
	<form:errors cssClass="errors" path="personalRecord.phoneNumber"/>
	
	<form:label path="personalRecord.linkedInProfile">
		<spring:message code="personalRecord.linkedInProfile"/>
	</form:label>
	<form:input path="personalRecord.linkedInProfile"/>
	<form:errors cssClass="errors" path="personalRecord.linkedInProfile"/>
	
	<h1><spring:message code="curriculum.educationRecords"/></h1>
	
	<form:label path="educationRecords.titleOfDiploma">
		<spring:message code="educationRecord.titleOfDiploma"/>
	</form:label>
	<form:input path="educationRecords.titleOfDiploma"/>
	<form:errors cssClass="errors" path="educationRecords.titleOfDiploma"/>
	
	<form:label path="educationRecords.startingDate">
		<spring:message code="educationRecord.startingDate"/>
	</form:label>
	<form:input path="educationRecords.startingDate"/>
	<form:errors cssClass="errors" path="educationRecords.startingDate"/>
	
	<form:label path="educationRecords.endingDate">
		<spring:message code="educationRecord.endingDate"/>
	</form:label>
	<form:input path="educationRecords.endingDate"/>
	<form:errors cssClass="errors" path="educationRecords.endingDate"/>
	
	<form:label path="educationRecords.institution">
		<spring:message code="educationRecord.institution"/>
	</form:label>
	<form:input path="educationRecords.institution"/>
	<form:errors cssClass="errors" path="educationRecords.institution"/>
	
	<form:label path="educationRecords.attachment">
		<spring:message code="educationRecord.attachment"/>
	</form:label>
	<form:input path="educationRecords.attachment"/>
	<form:errors cssClass="errors" path="educationRecords.attachment"/>
	
	<form:label path="educationRecords.comments">
		<spring:message code="educationRecord.comments"/>
	</form:label>
	<form:input path="educationRecords.comments"/>
	<form:errors cssClass="errors" path="educationRecords.comments"/>
	
	<h1><spring:message code="curriculum.professionalRecords"/></h1>
	
	<form:label path="professionalRecords.companyName">
		<spring:message code="professionalRecord.companyName"/>
	</form:label>
	<form:input path="professionalRecords.companyName"/>
	<form:errors cssClass="errors" path="professionalRecords.companyName"/>
	
</form:form>

<h1><spring:message code="curriculum.professionalRecords"/></h1> 

<display:table name="curriculum.professionalRecords" id="professionalRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="companyName" titleKey="professionalRecord.companyName" sortable="true"/>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>
	<display:column property="startingDate" titleKey="professionalRecord.startingDate" format="${dateFormat}" sortable="true" />
	<display:column property="endingDate" titleKey="professionalRecord.endingDate" format="${dateFormat}" sortable="true" />
	
	<display:column property="role" titleKey="professionalRecord.role" sortable="true"/>
	
	<display:column titleKey="professionalRecord.attachment">
		<a href="${professionalRecord.attachment}"><jstl:out value="${professionalRecord.attachment}"/></a>
	</display:column>
	
	<display:column property="comments" titleKey="professionalRecord.comments"/>
</display:table>

<h1><spring:message code="curriculum.endorserRecords"/></h1> 

<display:table name="curriculum.endorserRecords" id="endorserRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="fullName" titleKey="endorserRecord.fullName" sortable="true"/>
	
	<display:column property="email" titleKey="endorserRecord.email"/>
	
	<display:column property="phoneNumber" titleKey="endorserRecord.phoneNumber"/>
		
	<display:column titleKey="endorserRecord.linkedInProfile">
		<a href="${endorserRecord.linkedInProfile}"><jstl:out value="${endorserRecord.linkedInProfile}"/></a>
	</display:column>
	
	<display:column property="comments" titleKey="endorserRecord.comments"/>
</display:table>

<h1><spring:message code="curriculum.miscellaneousRecords"/></h1> 

<display:table name="curriculum.miscellaneousRecords" id="miscellaneousRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="title" titleKey="miscellaneousRecord.title" sortable="true"/>
	
		<display:column titleKey="miscellaneousRecord.attachment">
		<a href="${miscellaneousRecord.attachment}"><jstl:out value="${miscellaneousRecord.attachment}"/></a>
	</display:column>
	
	<display:column property="comments" titleKey="miscellaneousRecord.comments"/>
</display:table>