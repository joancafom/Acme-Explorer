<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="story.list"/></h1>

<display:table name="stories" id="story" requestURI="${requestURI}" pagesize="5" class="displaytag">

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
		
	<display:column property="title" titleKey="story.title" sortable="true"/>
	
	<display:column titleKey="story.trip" sortable="true">
		<a href="trip/${url}display.do?tripId=${story.trip.id}"><jstl:out value="${story.trip.ticker}"/></a>
	</display:column>	

	<display:column property="text" titleKey="audit.text"/>
	
	<display:column titleKey="story.attachments">
		<jstl:if test="${not empty story.attachments}">
			<jstl:forEach var="attachment" items="${story.attachments}">
				<jstl:out value="${attachment}"/><br/>
			</jstl:forEach>
		</jstl:if>
		
		<jstl:if test="${empty story.attachments}">
			<p>-</p>
		</jstl:if>		
	</display:column>
	
	<display:column titleKey="story.explorer" sortable="true">
		<p><jstl:out value="${story.explorer.surname}"></jstl:out>, <jstl:out value="${story.explorer.name}"></jstl:out></p>
	</display:column>
	
</display:table>