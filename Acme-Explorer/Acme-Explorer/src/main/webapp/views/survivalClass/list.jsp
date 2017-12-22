<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="survivalClasses" id="survivalClass" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('EXPLORER')">
		<jstl:set var="url" value="explorer/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:set var="url" value="manager/"></jstl:set>
	</security:authorize>
	
	<display:column titleKey="survivalClass.title" sortable="true">
		<a href="survivalClass/${url}display.do?survivalClassId=${survivalClass.id}"><jstl:out value="${survivalClass.title}"/></a>
	</display:column>
	
	<display:column titleKey="survivalClass.trip" sortable="true">
		<a href="trip/${url}display.do?tripId=${survivalClass.trip.id}"><jstl:out value="${survivalClass.trip.ticker}"/></a>
	</display:column>	
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="moment" titleKey="survivalClass.moment" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column titleKey="survivalClass.location">
		<jstl:out value="${survivalClass.location.name}"></jstl:out>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${enableEdit==true}">
		<display:column>
			<a href="survivalClass/manager/edit.do?survivalClassId=${survivalClass.id}"><spring:message code="survivalClass.edit"/></a>
		</display:column>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="survivalClass/explorer/enroll.do?survivalClassId=${survivalClass.id}"><spring:message code="survivalClass.enroll"/></a>
		</display:column>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${enableEdit==true}">
		<a href="survivalClass/manager/create.do"><spring:message code="survivalClass.create"/></a>
	</jstl:if>
</security:authorize>