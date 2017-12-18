<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="date.format2" var="dateFormat"></spring:message>	

<p><spring:message code="audit.description"/>: <jstl:out value="${audit.description}" /></p>

<p><spring:message code="audit.moment"/>: <fmt:formatDate value="${audit.moment}" pattern="${dateFormat}" /></p>

<jstl:if test="${not empty audit.attachments }">

	<p><spring:message code="audit.attachments"/>: </p>
	
	<jstl:forEach var="attachment" items="${audit.attachments}">
		<p><a href="<jstl:out value="${attachment}"/>"><jstl:out value="${attachment}"/></a></p>
	</jstl:forEach>
	
</jstl:if>

<p><spring:message code="audit.mode"/>:
	<jstl:choose>
		<jstl:when test="${audit.isFinal}"><spring:message code="audit.final"/></jstl:when>
		<jstl:otherwise>
			<spring:message code="audit.draft"/>
		</jstl:otherwise>
	</jstl:choose>
</p>

<p><spring:message code="audit.auditor"/>: <jstl:out value="${audit.auditor.surname}"/>, <jstl:out value="${audit.auditor.name}"/></p>

<p><spring:message code="audit.trip"/>: <a href="trip/${actorWS}display.do?tripId=${audit.trip.id}"><jstl:out value="${audit.trip.ticker}" /></a></p>

