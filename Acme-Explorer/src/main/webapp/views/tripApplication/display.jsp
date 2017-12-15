<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actor" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actor" value="explorer"></jstl:set>
</security:authorize>

<h1><jstl:out value="${tripApplication.trip.title}" /> - <spring:message code="tripApplication.status.${tripApplication.status}"/></h1>

<jstl:if test="${tripApplication.status == 'REJECTED'}">
	<p><spring:message code="tripApplication.rejectionReason"/>: 
		<jstl:choose>
			<jstl:when test="${tripApplication.rejectionReason == null }">-</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${tripApplication.rejectionReason}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</p>
</jstl:if>

<p><spring:message code="tripApplication.trip"/>: <a href="trip/${actor}/display.do?tripId=${tripApplication.trip.id}"><jstl:out value="${tripApplication.trip.ticker}" /></a></p>

<spring:message code="date.format2" var="dateFormat"></spring:message>
<p><spring:message code="tripApplication.moment"/>: <fmt:formatDate value="${tripApplication.moment}" pattern="${dateFormat}" type="both"/></p>

<p><spring:message code="tripApplication.comments"/>: <jstl:out value="${tripApplication.comments}"></jstl:out></p>

<p><spring:message code="tripApplication.creditCard"/>:
	<jstl:choose>
		<jstl:when test="${tripApplication.creditCard == null}">-</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${tripApplication.creditCard.number}"></jstl:out>
		</jstl:otherwise>
	</jstl:choose>
</p>