<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="trips" id="trip" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<jsp:useBean id="now" class="java.util.Date" />

	<jstl:if test="${trip.cancelationReason != null}">
		<spring:message code="trip.cancelled" var="tripCancelled"></spring:message>	
	</jstl:if>
	
	<jstl:if test="${trip.cancelationReason == null}">
		<jstl:set var="tripCancelled" value=""></jstl:set>
	</jstl:if>

	<display:column titleKey="trip.ticker" sortable="true">
		<a href="trip/${actorWS}display.do?tripId=${trip.id}"><jstl:out value="${trip.ticker}"></jstl:out></a>
		<jstl:out value="${tripCancelled}"></jstl:out>
	</display:column>

	<display:column property="title" titleKey="trip.title" sortable="true"></display:column>
	
	<spring:message code="price.format" var="priceFormat"></spring:message>
	<display:column titleKey="trip.price" sortable="true">
		<fmt:formatNumber type="currency" currencySymbol="&#8364;" pattern="${priceFormat}" value="${trip.price}" />
	</display:column>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="startingDate" titleKey="trip.startingDate" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column property="endingDate" titleKey="trip.endingDate" format="${dateFormat}"></display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column property="publicationDate" titleKey="trip.publicationDate" format="${dateFormat}"></display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<jstl:if test="${canCreateTA[trip.ticker] and !tripCancelled and trip.startingDate > now}">
				<a href="tripApplication/explorer/create.do?tripId=${trip.id}"><spring:message code="tripApplication.create"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<display:column property="category.name" titleKey="trip.category" sortable="true"></display:column>
		
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${trip.manager.id == principalId}">
			<display:column titleKey="tripApplications">
				<a href="tripApplication/manager/list.do?tripId=${trip.id}"><spring:message code="tripApplication.list"/></a>
			</display:column>
			<spring:message code="date.format2" var="dateFormat2"></spring:message>
			<fmt:formatDate value="${now}" pattern="${dateFormat2}"/>
			<display:column>
				<jstl:if test="${now.time < trip.publicationDate.time}">
					<a href="trip/manager/edit.do?tripId=${trip.id}" ><spring:message code="trip.list.edit"/></a>
				</jstl:if>
				<jstl:if test="${now.time >= trip.publicationDate.time}">
					<jstl:if test="${now.time < trip.startingDate.time and trip.cancelationReason == null and trip.manager.id == principalId}">
							<a href="trip/manager/cancel.do?tripId=${trip.id}" ><spring:message code="trip.list.cancel"/></a>
					</jstl:if>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>	
		
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<a href="trip/manager/create.do"><spring:message code="trip.create"/></a>
</security:authorize>