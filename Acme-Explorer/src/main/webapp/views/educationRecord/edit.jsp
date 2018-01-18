<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="educationRecord/ranger/edit.do" modelAttribute="educationRecord">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="curriculum"/>
	
	<form:label path="titleOfDiploma">
		<spring:message code="educationRecord.titleOfDiploma"/>
	</form:label>
	<form:input path="titleOfDiploma"/>
	<form:errors cssClass="error" path="titleOfDiploma"/>
	
	<br/>
	
	<form:label path="startingDate">
		<spring:message code="educationRecord.startingDate"/>
	</form:label>
	<form:input path="startingDate"/>
	<form:errors cssClass="error" path="startingDate"/>
	
	<br/>
	
	<form:label path="endingDate">
		<spring:message code="educationRecord.endingDate"/>
	</form:label>
	<form:input path="endingDate"/>
	<form:errors cssClass="error" path="endingDate"/>
	
	<br/>
	
	<form:label path="institution">
		<spring:message code="educationRecord.institution"/>
	</form:label>
	<form:input path="institution"/>
	<form:errors cssClass="error" path="institution"/>
	
	<br/>
	
	<form:label path="attachment">
		<spring:message code="educationRecord.attachment"/>
	</form:label>
	<form:input path="attachment"/>
	<form:errors cssClass="error" path="attachment"/>
	
	<br/>
	
	<form:label path="comments">
		<spring:message code="educationRecord.comments"/>
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="educationRecord.save"/>">
	<jstl:if test="${educationRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="educationRecord.delete"/>">
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message	code="educationRecord.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${educationRecord.curriculum.id}');" />
</form:form>
