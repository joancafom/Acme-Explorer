<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="day" value="${now}" pattern="dd"/>
<p>Current day: ${day}</p>
<fmt:formatDate var="month" value="${now}" pattern="MM"/>
<p>Current month: ${month}</p>
<fmt:formatDate var="year" value="${now}" pattern="yyyy"/>
<p>Current year: ${year}</p>
<fmt:formatDate var="time" value="${now}" pattern="HH:mm"/>
<p>Current time: ${time}</p>

<jstl:if test="${month == 12}">
	<jstl:set var="nextMonth" value="01"></jstl:set>
	<jstl:set var="nextYear" value="${year+1}"></jstl:set>	
</jstl:if>

<jstl:if test="${month < 12}">
	<jstl:set var="nextMonth" value="${month+1}"></jstl:set>
	<jstl:set var="nextYear" value="${year}"></jstl:set>	
</jstl:if>

<jstl:set var="nextDate" value="${day}/${nextMonth}/${nextYear} ${time}"></jstl:set>
<fmt:parseDate value = "${nextDate}" type="both" var = "parsedNextDate" pattern = "dd/MM/yyyy HH:mm" />
<p>Next Date: ${parsedNextDate}</p>

<fmt:formatDate var="startDate" value="${tripApplication.trip.startingDate}" pattern="dd/MM/yyyy HH:mm"/>
<p>Starting Date: ${startDate}</p>

<jstl:if test="${tripApplication.status == 'PENDING'}">
	<jstl:set var="backColor" value="white"></jstl:set>
	<jstl:if test="${startDate < parsedNextDate}">
		<jstl:set var="backColor" value="red"></jstl:set>
	</jstl:if>
</jstl:if>

<jstl:if test="${tripApplication.status == 'REJECTED'}">
	<jstl:set var="backColor" value="grey"></jstl:set>
</jstl:if>

<jstl:if test="${tripApplication.status == 'DUE'}">
	<jstl:set var="backColor" value="yellow"></jstl:set>
</jstl:if>

<jstl:if test="${tripApplication.status == 'ACCEPTED'}">
	<jstl:set var="backColor" value="green"></jstl:set>
</jstl:if>

<jstl:if test="${tripApplication.status == 'CANCELLED'}">
	<jstl:set var="backColor" value="cyan"></jstl:set>
</jstl:if>

<script type="text/javascript">
document.body.style.backgroundColor = "${backColor}";
</script>

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