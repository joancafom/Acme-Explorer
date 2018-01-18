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
	<form:hidden path="professionalRecords"/>
	<form:hidden path="endorserRecords"/>
	<form:hidden path="miscellaneousRecords"/>

	<form:label path="fullName">
		<spring:message code="fullName"/>
	</form:label>
	<form:input path="fullName"/>
	<form:errors cssClass="error" path="fullName"/>

	<br/>

	<form:label path="photo">
	<spring:message code="photo"/>
		</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="error" path="photo"/>

	<br/>
	
	<form:label path="email">
		<spring:message code="email"/>
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>

	<br/>

	<form:label path="phoneNumber">
		<spring:message code="phoneNumber"/>
	</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	
	<br/>

	<form:label path="linkedInProfile">
		<spring:message code="linkedInProfile"/>
	</form:label>
	<form:input path="linkedInProfile"/>
	<form:errors cssClass="error" path="linkedInProfile"/>
	
	<br/>
		
	<input type="submit" name="save" value="<spring:message code="curriculum.save"/>">
	<input type="submit" name="delete" value="<spring:message code="curriculum.delete"/>">
	<input type="button" name="cancel" value="<spring:message	code="curriculum.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${personalRecord.curriculum.id}');" />
		
</form:form>