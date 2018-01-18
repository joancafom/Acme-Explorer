<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="professionalRecord/ranger/edit.do" modelAttribute="professionalRecord">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="curriculum"/>
	
	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName"/>
	</form:label>
	<form:input path="companyName"/>
	<form:errors cssClass="error" path="companyName"/>
	
	<br/>
	
	<form:label path="startingDate">
		<spring:message code="professionalRecord.startingDate"/>
	</form:label>
	<form:input path="startingDate"/>
	<form:errors cssClass="error" path="startingDate"/>
	
	<br/>
	
	<form:label path="endingDate">
		<spring:message code="professionalRecord.endingDate"/>
	</form:label>
	<form:input path="endingDate"/>
	<form:errors cssClass="error" path="endingDate"/>
	
	<br/>
	
	<form:label path="role">
		<spring:message code="professionalRecord.role"/>
	</form:label>
	<form:input path="role"/>
	<form:errors cssClass="error" path="role"/>
	
	<br/>
	
	<form:label path="attachment">
		<spring:message code="professionalRecord.attachment"/>
	</form:label>
	<form:input path="attachment"/>
	<form:errors cssClass="error" path="attachment"/>
	
	<br/>
	
	<form:label path="comments">
		<spring:message code="professionalRecord.comments"/>
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="professionalRecord.save"/>">
	<jstl:if test="${professionalRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="professionalRecord.delete"/>">
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message	code="professionalRecord.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${professionalRecord.curriculum.id}');" />
</form:form>
