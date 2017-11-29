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

<spring:message code="date.format" var="dateFormat"></spring:message>	

<h1><tiles:insertAttribute name="title" /> - <jstl:out value="${audit.title}"></jstl:out> </h1>

<p><spring:message code="audit.description"/>: <jstl:out value="${audit.description}" /></p>

<p><spring:message code="audit.moment"/>: <fmt:formatDate value="${audit.moment}" pattern="${dateFormat}" /></p>

<jstl:if test="${not empty audit.attachments }">

	<p><spring:message code="audit.attachments"/>: </p>
	
	<jstl:forEach var="attachment" items="${audit.attachments}">
		<p><a href="<jstl:out value="${attachment}"/>"><jstl:out value="${attachment}"/></a></p>
	</jstl:forEach>
	
</jstl:if>

<p><spring:message code="audit.mode"/>:
	<jstl:choose>
		<jstl:when test="${audit.isFinal}"><spring:message code="audit.final"/></jstl:when>
		<jstl:otherwise>
			<spring:message code="audit.draft"/>
		</jstl:otherwise>
	</jstl:choose>
</p>

<p><spring:message code="audit.auditor"/>: <jstl:out value="${audit.auditor.surname}"/>, <jstl:out value="${audit.auditor.name}"/></p>

<p><spring:message code="audit.trip"/>: <a href="trip/${actorWithSlash}display.do?tripId=${audit.trip.id}"><jstl:out value="${audit.trip.ticker}" /></a></p>

