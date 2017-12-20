<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:out value="${curriculum.ticker}" />
<h1><spring:message code="curriculum.personalRecord"/></h1>

<p><spring:message code="personalRecord.fullName"/>: <jstl:out value="${curriculum.personalRecord.fullName}"/></p>
<p><spring:message code="personalRecord.photo"/>: <a href="${curriculum.personalRecord.photo}"><jstl:out value="${curriculum.personalRecord.photo}"/></a></p>
<p><spring:message code="personalRecord.email"/>: <jstl:out value="${curriculum.personalRecord.email}"/></p>
<p><spring:message code="personalRecord.phoneNumber"/>: <jstl:out value="${curriculum.personalRecord.phoneNumber}"/></p>
<p><spring:message code="personalRecord.linkedInProfile"/>: <a href="${curriculum.personalRecord.linkedInProfile}"><jstl:out value="${curriculum.personalRecord.linkedInProfile}"/></a></p>

<jstl:if test="${ownCurriculum}">
	<a href="personalRecord/ranger/edit.do?personalRecordId=${curriculum.personalRecord.id}"><spring:message code="personalRecord.edit"/></a>
</jstl:if>

<h1><spring:message code="curriculum.educationRecords"/></h1> 

<display:table name="curriculum.educationRecords" id="educationRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="titleOfDiploma" titleKey="educationRecord.titleOfDiploma" sortable="true"/>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>
	<display:column property="startingDate" titleKey="educationRecord.startingDate" format="${dateFormat}" sortable="true" />
	<display:column property="endingDate" titleKey="educationRecord.endingDate" format="${dateFormat}" sortable="true" />
	
	<display:column property="institution" titleKey="educationRecord.institution" sortable="true"/>
	
	<display:column titleKey="educationRecord.attachment">
		<a href="${educationRecord.attachment}"><jstl:out value="${educationRecord.attachment}"/></a>
	</display:column>
	
	<display:column property="comments" titleKey="educationRecord.comments"/>
	
	<jstl:if test="${ownCurriculum}">
		<display:column>
			<a href="educationRecord/ranger/edit.do?educationRecordId=${educationRecord.id}"><spring:message code="educationRecord.edit"/></a>
		</display:column>
	</jstl:if>
</display:table>

<jstl:if test="${ownCurriculum}">
	<a href="educationRecord/ranger/create.do?curriculumId=${educationRecord.curriculum.id}"><spring:message code="educationRecord.create"/></a>
</jstl:if>

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
	
	<jstl:if test="${ownCurriculum}">
		<display:column>
			<a href="professionalRecord/ranger/edit.do?professionalRecordId=${professionalRecord.id}"><spring:message code="professionalRecord.edit"/></a>
		</display:column>
	</jstl:if>
</display:table>

<jstl:if test="${ownCurriculum}">
	<a href="professionalRecord/ranger/create.do?curriculumId=${professionalRecord.curriculum.id}"><spring:message code="professionalRecord.create"/></a>
</jstl:if>

<h1><spring:message code="curriculum.endorserRecords"/></h1> 

<display:table name="curriculum.endorserRecords" id="endorserRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="fullName" titleKey="endorserRecord.fullName" sortable="true"/>
	
	<display:column property="email" titleKey="endorserRecord.email"/>
	
	<display:column property="phoneNumber" titleKey="endorserRecord.phoneNumber"/>
		
	<display:column titleKey="endorserRecord.linkedInProfile">
		<a href="${endorserRecord.linkedInProfile}"><jstl:out value="${endorserRecord.linkedInProfile}"/></a>
	</display:column>
	
	<display:column property="comments" titleKey="endorserRecord.comments"/>
	
	<jstl:if test="${ownCurriculum}">
		<display:column>
			<a href="endorserRecord/ranger/edit.do?endorserRecordId=${endorserRecord.id}"><spring:message code="endorserRecord.edit"/></a>
		</display:column>
	</jstl:if>
</display:table>

<jstl:if test="${ownCurriculum}">
	<a href="endorserRecord/ranger/create.do?curriculumId=${endorserRecord.curriculum.id}"><spring:message code="endorserRecord.create"/></a>
</jstl:if>

<h1><spring:message code="curriculum.miscellaneousRecords"/></h1> 

<display:table name="curriculum.miscellaneousRecords" id="miscellaneousRecord" requestURI="${RequestURI}" class="displaytag">
	<display:column property="title" titleKey="miscellaneousRecord.title" sortable="true"/>
	
	<display:column titleKey="miscellaneousRecord.attachment">
		<a href="${miscellaneousRecord.attachment}"><jstl:out value="${miscellaneousRecord.attachment}"/></a>
	</display:column>
	
	<display:column property="comments" titleKey="miscellaneousRecord.comments"/>
	
	<jstl:if test="${ownCurriculum}">
		<display:column>
			<a href="miscellaneousRecord/ranger/edit.do?miscellaneousRecordId=${miscellaneousRecord.id}"><spring:message code="miscellaneousRecord.edit"/></a>
		</display:column>
	</jstl:if>
</display:table>

<jstl:if test="${ownCurriculum}">
	<a href="miscellaneousRecord/ranger/create.do?curriculumId=${miscellaneousRecord.curriculum.id}"><spring:message code="miscellaneousRecord.create"/></a>
</jstl:if>

<jstl:if test="${ownCurriculum}">
	<br/>
	<a href="curriculum/ranger/edit.do?curriculumId=${curriculum.id}"><spring:message code="curriculum.delete"/></a>
</jstl:if>