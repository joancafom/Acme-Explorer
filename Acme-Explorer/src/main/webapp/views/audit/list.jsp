<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="audits" id="audit" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
	<display:column titleKey="audit.title" sortable="true">
		<a href="audit/${actorWS}display.do?auditId=${audit.id}"><jstl:out value="${audit.title}"/></a>
	</display:column>
	
	<display:column titleKey="audit.trip" sortable="true">
		<a href="trip/${actorWS}display.do?tripId=${audit.trip.id}"><jstl:out value="${audit.trip.ticker}"/></a>
	</display:column>	
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="moment" titleKey="audit.moment" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column titleKey="audit.auditor">
		<jstl:out value="${audit.auditor.surname}"></jstl:out>, <jstl:out value="${audit.auditor.name}"></jstl:out>
	</display:column>
	
	<display:column titleKey="audit.mode" sortable="true">
		<jstl:if test="${audit.isFinal}">
			<jstl:set var="mode" value="final"/>
		</jstl:if>
	
		<jstl:if test="${!audit.isFinal}">
			<jstl:set var="mode" value="draft"/>
		</jstl:if>
		
		<spring:message code="audit.${mode}"/>	
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')">
		<display:column>
			<jstl:if test="${!audit.isFinal}">
				<a href="audit/auditor/edit.do?auditId=${audit.id}"><spring:message code="audit.edit"/></a>
			</jstl:if>			
		</display:column>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('AUDITOR')">
	<a href="audit/auditor/create.do"><spring:message code="audit.create"/></a>		
</security:authorize>