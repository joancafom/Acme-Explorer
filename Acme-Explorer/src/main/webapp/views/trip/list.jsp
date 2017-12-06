<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

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

<h1><spring:message code="trip.list"/></h1>

<display:table name="trips" id="trip" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<jstl:if test="${trip.cancelationReason != null}">
		<spring:message code="trip.cancelled" var="tripCancelled"></spring:message>	
	</jstl:if>
	
	<jstl:if test="${trip.cancelationReason == null}">
		<jstl:set var="tripCancelled" value=""></jstl:set>
	</jstl:if>

	<display:column titleKey="trip.ticker" sortable="true">
		<a href="trip/${url}display.do?tripId=${trip.id}"><jstl:out value="${trip.ticker}"></jstl:out></a>
		<jstl:out value="${tripCancelled}"></jstl:out>
	</display:column>

	<display:column property="title" titleKey="trip.title" sortable="true"></display:column>
	
	<spring:message code="price.format" var="priceFormat"></spring:message>
	<display:column titleKey="trip.price" sortable="true">
		<fmt:formatNumber value="${trip.price}" pattern="${priceFormat}"/>
	</display:column>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="startingDate" titleKey="trip.startingDate" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column property="endingDate" titleKey="trip.endingDate" format="${dateFormat}"></display:column>
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="tripApplication/explorer/create.do?tripId=${trip.id}"><spring:message code="tripApplication.create"/></a>
		</display:column>
	</security:authorize>
		
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<a href="trip/manager/create.do"><spring:message code="trip.create"/></a>
</security:authorize>