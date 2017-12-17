<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="note/manager/edit.do" modelAttribute="note">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="trip"/>
	<form:hidden path="auditor"/>
	<form:hidden path="remark"/>
	<form:hidden path="writtenMoment"/>
	
	<form:input path="reply"/>
	
	<input type="submit" name="save" value="<spring:message code="note.save" />" />
	<input type="button" name="cancel" value="<spring:message code="note.cancel"/>" onclick="javascript: relativeRedir('note/manager/list.do');" />
	
</form:form>