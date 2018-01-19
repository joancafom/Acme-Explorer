<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><jstl:out value="${tripApplication.trip.title}" /> - <spring:message code="tripApplication.status.${tripApplication.status}"/></h1>

<security:authorize access="hasRole('MANAGER')">
	<h2><spring:message code="tripApplication.explorer"/></h2>
	<p><strong><spring:message code="tripApplication.explorer.name"/>:</strong> <jstl:out value="${tripApplication.explorer.name}"/></p>
	<p><strong><spring:message code="tripApplication.explorer.surname"/>:</strong> <jstl:out value="${tripApplication.explorer.surname}"/></p>
	<p><strong><spring:message code="tripApplication.explorer.email"/>:</strong> <jstl:out value="${tripApplication.explorer.email}"/></p>
	<p><strong><spring:message code="tripApplication.explorer.phoneNumber"/>:</strong> <jstl:out value="${tripApplication.explorer.phoneNumber}"/></p>
	<p><strong><spring:message code="tripApplication.explorer.address"/>:</strong> <jstl:out value="${tripApplication.explorer.address}"/></p>
	<hr>
</security:authorize>


<jstl:if test="${tripApplication.status == 'REJECTED'}">
	<p><strong style="color:red"><spring:message code="tripApplication.rejectionReason"/>:</strong> 
		<jstl:choose>
			<jstl:when test="${tripApplication.rejectionReason == null }">-</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${tripApplication.rejectionReason}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</p>
</jstl:if>

<p><strong><spring:message code="tripApplication.trip"/>:</strong> <a href="${tripURI}"><jstl:out value="${tripApplication.trip.ticker}" /></a></p>

<spring:message code="date.format2" var="dateFormat"></spring:message>
<p><strong><spring:message code="tripApplication.moment"/>:</strong> <fmt:formatDate value="${tripApplication.moment}" pattern="${dateFormat}" type="both"/></p>

<p><strong><spring:message code="tripApplication.comments"/>:</strong> <jstl:out value="${tripApplication.comments}"></jstl:out></p>

<p><strong><spring:message code="tripApplication.creditCard"/>:</strong>
	<jstl:choose>
		<jstl:when test="${tripApplication.creditCard == null}">-</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${tripApplication.creditCard.number}"></jstl:out>
		</jstl:otherwise>
	</jstl:choose>
</p> 