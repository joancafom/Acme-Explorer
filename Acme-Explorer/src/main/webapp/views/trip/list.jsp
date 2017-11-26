<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="trips" id="trip" requestURI="" pagesize="5" class="displaytag">

	<jstl:choose>
		<jstl:when test="hasRole('MANAGER')">
			<security:authorize access="hasRole('MANAGER')">
				<display:column title="<spring:message code="trip.ticker"/>" sortable="true">
					<a href="trip/manager/display.do?tripId=${trip.id}"><jstl:out value="${trip.ticker}"></jstl:out></a>
				</display:column>
			</security:authorize>
		</jstl:when>
		
		<jstl:when test="hasRole('EXPLORER')">
			<security:authorize access="hasRole('EXPLORER')">
				<display:column title="<spring:message code="trip.ticker"/>" sortable="true">
					<a href="trip/explorer/display.do?tripId=${trip.id}"><jstl:out value="${trip.ticker}"></jstl:out></a>
				</display:column>
			</security:authorize>
		</jstl:when>
		
		<jstl:otherwise>
			<display:column title="<spring:message code="trip.ticker"/>" sortable="true">
				<a href="trip/display.do?tripId=${trip.id}"><jstl:out value="${trip.ticker}"></jstl:out></a>
			</display:column>
		</jstl:otherwise>
	</jstl:choose>
	
	<display:column property="title" title="<spring:message code="trip.title"/>" sortable="true"></display:column>
	
	<display:column property="description" title="<spring:message code="trip.description"/>"></display:column>
	
	<display:column property="price" title="<spring:message code="trip.price"/>" sortable="true">
		<jstl:out value="${trip.price}&euro;"></jstl:out>
	</display:column>
	
	<display:column property="startingDate" title="<spring:message code="trip.startingDate"/>" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"></display:column>
	
	<display:column property="endingDate" title="<spring:message code="trip.endingDate"/>" format="{0, date, dd/MM/yyyy HH:mm}"></display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<div>
			<a href="trip/manager/create.do"><spring:message code="trip.create"/></a>
		</div>
	</security:authorize>

</display:table>