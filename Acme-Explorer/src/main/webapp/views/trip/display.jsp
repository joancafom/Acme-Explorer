<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<html>

<head>
	<jstl:choose>
		<jstl:when test="${trip.cancelationReason} == null">
			<title><jstl:out value="${trip.title}"></jstl:out></title>
		</jstl:when>
		
		<jstl:otherwise>
			<title><jstl:out value="${trip.title}"></jstl:out> - <spring:message code="trip.cancelled"/></title>
		</jstl:otherwise>
	</jstl:choose>
</head>

<body>
	<p><spring:message code="trip.ticker"/>: <jstl:out value="${trip.ticker}"/></p>
	
	<p><spring:message code="trip.description"/>: <jstl:out value="${trip.description}"/></p>
	
	<p><spring:message code="trip.price"/>: <jstl:out value="${trip.price}"/>&euro;</p>
	
	<p><spring:message code="trip.startingDate"/>: <fmt:formatDate value="${trip.startingDate}" pattern="{0, date, dd/MM/yyyy HH:mm}"/></p>
	
	<p><spring:message code="trip.endingDate"/>: <fmt:formatDate value="${trip.endingDate}" pattern="{0, date, dd/MM/yyyy HH:mm}"/></p>
	
	<p><spring:message code="trip.requirements"/>: <jstl:out value="${trip.requirements}"/></p>
	
	<jstl:if test="${trip.CancelationReason} != null">
		<p><spring:message code="trip.cancelationReason"/>: <jstl:out value="${trip.cancelationReason}"></jstl:out></p>
	</jstl:if>
	
	<a href="${sponsorship.infoPageLink}"><img alt="<spring:message code="sponsorship.bannerUrl"/>" src="${sponsorship.bannerUrl}"/></a>
	
	<p><spring:message code="stories"/>: <a href="story/list.do?tripId=${trip.id}"></a></p>
	
	<security:authorize access="hasRole('MANAGER')">
		<p><spring:message code="notes"/>:</p>
		<a href="note/manager/list.do"><spring:message code="note.list"/></a>
	</security:authorize>
	
	<p><spring:message code="auditions"/>:</p>
	<jstl:forEach var="audition" items="${auditions}">
		<a href="audition/display.do?auditionId=${audition.id}"><jstl:out value="${audition.title}"/></a>
		<br/>
	</jstl:forEach>
	
	<security:authorize access="hasRole('MANAGER')">
		<p><spring:message code="tripApplications"/>:</p>
		<a href="tripApplication/manager/list.do"><spring:message code="tripApplication.list"/></a>
	</security:authorize>
	
	<p><spring:message code="tagValues"/>:</p>
	<jstl:forEach var="tagValue" items="${tagValues}">
		<jstl:out value="#${tagValue.value}"></jstl:out>
		<br/>
	</jstl:forEach>
	
	<p><spring:message code="legalTexts"/>:</p>
	<display:table name="legalTexts" id="legalText" class="displaytag">
		<display:column property="title" titleKey="legalText.title" sortable="true"></display:column>
		<display:column property="body" titleKey="legalText.body"></display:column>
	</display:table>
	
	<p><spring:message code="stages"/>:</p>
	<display:table name="stages" id="stage" class="displaytag">
		<display:column property="number" titleKey="stage.number" sortable="true"></display:column>
		<display:column property="title" titleKey="stage.title"></display:column>
		<display:column property="description" titleKey="stage.description"></display:column>
		<display:column titleKey="stage.price">
			<jstl:out value="${stage.price}&euro;"></jstl:out>
		</display:column>
	</display:table>
	
	<p><spring:message code="category"/>:</p>
	<jstl:out value="${category.name}"></jstl:out>
	
	<security:authorize access="hasRole('MANAGER')">
		<p><spring:message code="survivalClasses"/>:</p>
		<a href="survivalClass/manager/list.do"><spring:message code="survivalClass.list"/></a>
	</security:authorize>
	
	<security:authorize access="!hasRole('MANAGER')">
		<p><spring:message code="manager"/>:</p>
		<jstl:out value="${manager.surname}"/>, <jstl:out value="${manager.name}"/>
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
		<a href="tripApplication/explorer/create.do"><spring:message code="tripApplication.create"/></a>
	</security:authorize>
</body>

</html>