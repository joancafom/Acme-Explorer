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

<display:table name="tripApplications" id="tripApplication" requestURI="tripApplication/${actor}/list.do" pagesize="5" class="displaytag">

	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="day" value="${now}" pattern="dd"/>
	<fmt:formatDate var="month" value="${now}" pattern="MM"/>
	<fmt:formatDate var="year" value="${now}" pattern="yyyy"/>
	<fmt:formatDate var="time" value="${now}" pattern="HH:mm"/>

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

	<fmt:formatDate var="startDate" value="${tripApplication.trip.startingDate}" pattern="dd/MM/yyyy HH:mm"/>

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

	<spring:message code="date.format" var="dateFormat"></spring:message>	
	
	<display:column titleKey="tripApplication.trip.ticket" sortable="true">
		<a href="trip/${actor}/display.do?tripId=${tripApplication.trip.id}"><jstl:out value="${tripApplication.trip.ticker}" /></a>
	</display:column>
	
	<display:column property="moment" titleKey="tripApplication.moment" format="${dateFormat}" sortable="true" />
	
	<display:column style="background-color:${backColor};" titleKey="tripApplication.status" sortable="true">
		<spring:message code="tripApplication.status.${tripApplication.status}"></spring:message>
	</display:column>
	
	<display:column titleKey="tripApplication.creditCard" sortable="true">
		<jstl:choose>
			<jstl:when test="${tripApplication.creditCard == null}">-</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${tripApplication.creditCard.number}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column titleKey="tripApplication.details">
		<a href="tripApplication/${actor}/display.do?tripApplicationId=${tripApplication.id}"><spring:message code="tripApplication.details"/></a>
	</display:column>
	
	<display:column>
		<security:authorize access="hasRole('EXPLORER')">
			<jstl:if test="${tripApplication.status == 'DUE' }">
				<a href="tripApplication/explorer/edit.do?tripApplicationId=${tripApplication.id}"><spring:message code="tripApplication.enterCreditCard"/></a>
			</jstl:if>
		</security:authorize>
		<security:authorize access="hasRole('MANAGER')">
			<jstl:if test="${tripApplication.status == 'PENDING' }">
				<a href="tripApplication/manager/edit.do?tripApplicationId=${tripApplication.id}">Edit</a>
			</jstl:if>
		</security:authorize>
		<security:authorize access="hasRole('EXPLORER')">
			<jstl:if test="${tripApplication.status == 'ACCEPTED' }">
				<a href="tripApplication/explorer/edit.do?tripApplicationId=${tripApplication.id}"><spring:message code="tripApplication.cancelApplication"/></a>
			</jstl:if>
		</security:authorize>
	</display:column>

</display:table>
