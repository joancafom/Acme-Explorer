<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="curriculum.personalRecord"/></h1>

<p><spring:message code="personalRecord.fullName"/>: <jstl:out value="${curriculum.personalRecord.fullName}"/></p>
<p><spring:message code="personalRecord.photo"/>: <a href="${curriculum.personalRecord.photo}"><jstl:out value="${curriculum.personalRecord.photo}"/></a></p>
<p><spring:message code="personalRecord.email"/>: <jstl:out value="${curriculum.personalRecord.email}"/></p>
<p><spring:message code="personalRecord.phoneNumber"/>: <jstl:out value="${curriculum.personalRecord.phoneNumber}"/></p>
<p><spring:message code="personalRecord.linkedInProfile"/>: <a href="${curriculum.personalRecord.linkedInProfile}"><jstl:out value="${curriculum.personalRecord.linkedInProfile}"/></a></p>

<h1><spring:message code="curriculum.educationRecords"/></h1> 

<display:table name="curriculum.educationRecords" id="educationRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="titleOfDiploma" titleKey="educationRecord.titleOfDiploma" sortable="true"/>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>
	<display:column property="startingDate" titleKey="educationRecord.startingDate" format="${dateFormat}" sortable="true" />
	<display:column property="endingDate" titleKey="educationRecord.endingDate" format="${dateFormat}" sortable="true" />
	
	<display:column property="institution" titleKey="educationRecord.institution" sortable="true"/>
	
	<display:column property="attachment" titleKey="educationRecord.attachment"/>
	
	<display:column property="comments" titleKey="educationRecord.comments"/>
</display:table>

<h1><spring:message code="curriculum.professionalRecords"/></h1> 

<display:table name="curriculum.professionalRecords" id="professionalRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="companyName" titleKey="professionalRecord.companyName" sortable="true"/>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>
	<display:column property="startingDate" titleKey="professionalRecord.startingDate" format="${dateFormat}" sortable="true" />
	<display:column property="endingDate" titleKey="professionalRecord.endingDate" format="${dateFormat}" sortable="true" />
	
	<display:column property="role" titleKey="professionalRecord.role" sortable="true"/>
	
	<display:column property="attachment" titleKey="professionalRecord.attachment"/>
	
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
	
	<display:column property="attachment" titleKey="miscellaneousRecord.attachment"/>
	
	<display:column property="comments" titleKey="miscellaneousRecord.comments"/>
</display:table>