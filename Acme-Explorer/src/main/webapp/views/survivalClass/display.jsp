<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><jstl:out value="${survivalClass.title}"/></h1>

<p><spring:message code="survivalClass.description"/>: <jstl:out value="${survivalClass.description}"/></p>

<spring:message code="date.format" var="dateFormat"></spring:message>
<p><spring:message code="survivalClass.moment"/>: <fmt:formatDate value="${survivalClass.moment}" pattern="${dateFormat}"/></p>

<p>
	<spring:message code="survivalClass.location"/>: 
	<jstl:out value="${survivalClass.location.name}"/>. 
	(<spring:message code="location.latitude"/>: <jstl:out value="${survivalClass.location.coordinateX}"/> |
	<spring:message code="location.longitude"/>: <jstl:out value="${survivalClass.location.coordinateY}"/>)
</p>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="url" value="explorer/"></jstl:set>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="url" value="manager/"></jstl:set>
</security:authorize>

<p><spring:message code="survivalClass.trip"/>: <a href="trip/${url}display.do?tripId=${survivalClass.trip.id}"><jstl:out value="${survivalClass.trip.ticker}"/></a></p>

<p><spring:message code="survivalClass.manager"/>: <jstl:out value="${survivalClass.manager.surname}"/>, <jstl:out value="${survivalClass.manager.name}"/></p>

<security:authorize access="hasRole('MANAGER')">
	<p><spring:message code="survivalClass.explorers"/>: <a href="explorer/manager/list.do?survivalClassId=${survivalClass.id}"><spring:message code="explorer.list"/></a></p>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<p><a href="survivalClass/explorer/enroll.do?survivalClassId=${survivalClass.id}"><spring:message code="survivalClass.enroll"/></a></p>
</security:authorize>