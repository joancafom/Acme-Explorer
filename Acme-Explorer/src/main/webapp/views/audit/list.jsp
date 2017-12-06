<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="audit.list"/></h1>

<display:table name="audits" id="audit" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<jstl:set var="url" value="admin/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('AUDITOR')">
		<jstl:set var="url" value="auditor/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<jstl:set var="url" value="explorer/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:set var="url" value="manager/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('RANGER')">
		<jstl:set var="url" value="ranger/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('SPONSOR')">
		<jstl:set var="url" value="sponsor/"></jstl:set>
	</security:authorize>
		
	<display:column titleKey="audit.title" sortable="true">
		<a href="audit/${url}display.do?auditId=${audit.id}"><jstl:out value="${audit.title}"/></a>
	</display:column>
	
	<display:column titleKey="audit.trip" sortable="true">
		<a href="trip/${url}display.do?tripId=${audit.trip.id}"><jstl:out value="${audit.trip.ticker}"/></a>
	</display:column>	
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="moment" titleKey="audit.moment" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column titleKey="audit.auditor">
		<p><jstl:out value="${audit.auditor.surname}"></jstl:out>, <jstl:out value="${audit.auditor.name}"></jstl:out></p>
	</display:column>
	
	<display:column titleKey="audit.mode" sortable="true">
		<jstl:if test="${true audit.isFinal}">
			<jstl:set var="mode" value="final"/>
		</jstl:if>
	
		<jstl:if test="${false audit.isFinal}">
			<jstl:set var="mode" value="draft"/>
		</jstl:if>
		
		<spring:message code="audit.${mode}"/>	
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<jstl:if test="${false audit.isFinal}">
				<a href="audit/auditor/edit.do?auditorId=${audit.auditor.id}"><spring:message code="audit.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>