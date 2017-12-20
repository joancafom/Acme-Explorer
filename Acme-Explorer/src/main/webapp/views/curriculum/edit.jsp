<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="curriculum/ranger/edit.do" modelAttribute="curriculum">
	<jstl:if test="${curriculum.id != 0}">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="ticker"/>
		<form:hidden path="ranger"/>
		<form:hidden path="educationRecords"/>
		<form:hidden path="personalRecord"/>
		<form:hidden path="professionalRecords"/>
		<form:hidden path="endorserRecords"/>
		<form:hidden path="miscellaneousRecords"/>
	
		<spring:message code="delete.areYouSure"/>
	
		<br/>
	
		<input type="submit" name="delete" value="<spring:message code="educationRecord.delete"/>">
		<input type="button" name="cancel" value="<spring:message	code="educationRecord.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${curriculum.id}');" />
	</jstl:if>
	
	<jstl:if test="${curriculum.id == 0}">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="ticker"/>
		<form:hidden path="ranger"/>
		<form:hidden path="educationRecords"/>
		<form:hidden path="professionalRecords"/>
		<form:hidden path="endorserRecords"/>
		<form:hidden path="miscellaneousRecords"/>
	
		<form:label path="personalRecord.fullName">
			<spring:message code="personalRecord.fullName"/>
		</form:label>
		<form:input path="personalRecord.fullName"/>
		<form:errors cssClass="errors" path="personalRecord.fullName"/>
	
		<br/>
	
		<form:label path="personalRecord.photo">
			<spring:message code="personalRecord.photo"/>
		</form:label>
		<form:input path="personalRecord.photo"/>
		<form:errors cssClass="errors" path="personalRecord.photo"/>
	
		<br/>
	
		<form:label path="personalRecord.email">
			<spring:message code="personalRecord.email"/>
		</form:label>
		<form:input path="personalRecord.email"/>
		<form:errors cssClass="errors" path="personalRecord.email"/>
	
		<br/>
	
		<form:label path="personalRecord.phoneNumber">
			<spring:message code="personalRecord.phoneNumber"/>
		</form:label>
		<form:input path="personalRecord.phoneNumber" id="phoneNumber"/>
		<form:errors cssClass="errors" path="personalRecord.phoneNumber"/>
	
		<br/>
	
		<form:label path="personalRecord.linkedInProfile">
			<spring:message code="personalRecord.linkedInProfile"/>
		</form:label>
		<form:input path="personalRecord.linkedInProfile"/>
		<form:errors cssClass="errors" path="personalRecord.linkedInProfile"/>
	
		<br/>
		
		<input type="submit" name="save" value="<spring:message code="curriculum.save"/>">
		<input type="button" name="cancel" value="<spring:message	code="curriculum.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${personalRecord.curriculum.id}');" />
		
	</jstl:if>
</form:form>