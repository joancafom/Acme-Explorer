<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actor" value="explorer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actor" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="actor" value="admin"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="actor" value="auditor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('RANGER')">
	<jstl:set var="actor" value="ranger"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
	<jstl:set var="actor" value="sponsor"></jstl:set>
</security:authorize>

<form:form action="folder/${actor}/edit.do" modelAttribute="folder">
	<!-- Hidden inputs -->
	<form:hidden path="version"/>
	<form:hidden path="id"/>
	<form:hidden path="isSystem"/>
	<form:hidden path="messages"/>
	<form:hidden path="parentFolder"/>
	<form:hidden path="childFolders"/>
	<form:hidden path="actor"/>

	<form:label path="name">
		<spring:message code="folder.name"/>:
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	
	<br><br>
	<input type="submit" name="save" value="<spring:message code="folder.save"/>"/>
	<jstl:if test="${folder.id!=0}">
		<input type="submit" name="delete" value="<spring:message code="folder.delete"/>"/>
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="folder.cancel"/>" onclick="javascript: relativeRedir('folder/${actor}/list.do');" />
	
</form:form>

