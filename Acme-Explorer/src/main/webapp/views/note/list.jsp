<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="note.list"/></h1>

<display:table name="notes" id="note" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('AUDITOR')">
		<jstl:set var="url" value="auditor/"></jstl:set>
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:set var="url" value="manager/"></jstl:set>
	</security:authorize>
	
	<display:column titleKey="note.trip" sortable="true">
		<a href="trip/${url}display.do?tripId=${note.trip.id}"><jstl:out value="${note.trip.ticker}"></jstl:out></a>
	</display:column>

	<display:column property="remark" titleKey="note.remark"/>
	
	<spring:message code="date.format" var="dateFormat"></spring:message>	
	<display:column property="writtenMoment" titleKey="note.writtenMoment" sortable="true" format="${dateFormat}"></display:column>
	
	<display:column titleKey="note.reply">
		<jstl:if test="${note.reply == null}">
			<p>-</p>
		</jstl:if>
		
		<jstl:if test="${note.reply != null}">
			<jstl:out value="${note.reply}"></jstl:out>
		</jstl:if>
	</display:column>
	
	<display:column titleKey="note.replyMoment">
		<jstl:if test="${note.replyMoment == null}">
			<p>-</p>
		</jstl:if>
		
		<jstl:if test="${note.replyMoment != null}">
			<fmt:formatDate value="${note.replyMoment}" pattern="${dateFormat}"/>
		</jstl:if>
	</display:column>
	
	<display:column titleKey="note.auditor" sortable="true">
		<p><jstl:out value="${note.auditor.surname}"></jstl:out>, <jstl:out value="${note.auditor.name}"></jstl:out></p>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${note.reply == null}">
				<a href="note/manager/edit.do?noteId=${note.id}"><spring:message code="note.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
		
</display:table>