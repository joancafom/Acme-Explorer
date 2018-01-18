<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

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
	
	<spring:message code="date.format" var="dateFormat" />	
	<spring:message code="date.format2" var="dateFormat2" />	
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
			<fmt:formatDate value="${note.replyMoment}" pattern="${dateFormat2}"/>
		</jstl:if>
	</display:column>
	
	<display:column titleKey="note.auditor" sortable="true">
		<jstl:out value="${note.auditor.surname}"></jstl:out>, <jstl:out value="${note.auditor.name}"></jstl:out>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column titleKey="note.option">
			<jstl:if test="${note.reply == ''}">
				<a href="note/manager/edit.do?noteId=${note.id}"><spring:message code="note.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>	
</display:table>

<security:authorize access="hasRole('AUDITOR')">
	<a href="note/auditor/create.do"><spring:message code="note.create"/></a>
</security:authorize>