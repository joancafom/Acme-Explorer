<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="actorWithSlash" value="admin/"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="actorWithSlash" value="auditor/"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actorWithSlash" value="explorer/"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actorWithSlash" value="manager/"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('RANGER')">
	<jstl:set var="actorWithSlash" value="ranger/"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
	<jstl:set var="actorWithSlash" value="sponsor/"></jstl:set>
</security:authorize>
<security:authorize access="isAnonymous()">
	<jstl:set var="actorWithSlash" value=""></jstl:set>
</security:authorize>

<h1><tiles:insertAttribute name="title" />: <jstl:out value="${rootCategory.name}" /></h1>

<display:table name="rootCategory" id="childNode" requestURI="category/${actorWithSlash}list.do" class="displaytag">
	<display:column titleKey="category.children" sortable="true">
		<a href="category/${actorWithSlash}list.do?rootCategoryId=${childNode.id}"><jstl:out value="${childNode.name}" /></a>
	</display:column>
	<display:column>
		<a href="trip/${actorWithSlash}list.do?categoryId=${childNode.id}"><spring:message code="trip.search" /></a>
	</display:column>
</display:table>