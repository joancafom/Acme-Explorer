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
</form:form>