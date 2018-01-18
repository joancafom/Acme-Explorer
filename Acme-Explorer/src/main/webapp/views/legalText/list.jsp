<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="legalTexts" id="legalText" requestURI="${requestURI}" pagesize="5" class="displaytag">

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
		
	<display:column property="title" titleKey="legalText.title" sortable="true"/>
	
	<display:column property="body" titleKey="legalText.body"/>
	
	<display:column property="laws" titleKey="legalText.laws"/>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="registrationMoment" titleKey="legalText.moment" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column titleKey="legalText.mode">
		<jstl:if test="${legalText.isFinal}">
			<jstl:set var="mode" value="final"/>
		</jstl:if>
	
		<jstl:if test="${legalText.isFinal==false}">
			<jstl:set var="mode" value="draft"/>
		</jstl:if>
		
		<spring:message code="legalText.${mode}"/>	
	</display:column>
	
	<display:column titleKey="legalText.trips">
		<jstl:if test="${not empty legalText.trips}">
			<jstl:forEach var="trip" items="${legalText.trips}">
			<a href="trip/${url}display.do?tripId=${trip.id}"><jstl:out value="${trip.ticker}"/></a><br/>
		</jstl:forEach>
		</jstl:if>
		
		<jstl:if test="${empty legalText.trips}">
			<p>-</p>
		</jstl:if>		
	</display:column>
	
	<display:column>
		<jstl:if test="${legalText.isFinal==false}">
			<a href="legalText/admin/edit.do?legalTextId=${legalText.id}"><spring:message code="legalText.edit"/></a>
		</jstl:if>
	</display:column>
</display:table>

<a href="legalText/admin/create.do"><spring:message code="legalText.create"/></a>