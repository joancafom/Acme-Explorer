<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actor" value="explorer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actor" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="actor" value="admin"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="actor" value="auditor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('RANGER')">
	<jstl:set var="actor" value="ranger"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
	<jstl:set var="actor" value="sponsor"></jstl:set>
</security:authorize>
<h3><jstl:out value="${folder.name}"/></h3>


<a href="message/${actor}/create.do"><spring:message code="folder.message.send"/></a><br>

<security:authorize access="hasRole('ADMIN')">
	<a href="message/admin/create.do?isNotification=true"><spring:message code="folder.notification.send"/></a><br>
</security:authorize>

<br>


<hr>
<display:table name="folders" id="folder" requestURI="folder/${actor}/list.do" class="displaytag" pagesize="5">
	<display:column titleKey="folder.name">
		<a href="folder/${actor}/list.do?folderId=${folder.id}"><jstl:out value="${folder.name}"/></a>
	</display:column>
	<display:column>
		<jstl:if test="${folder.isSystem == false}">
			<a href="folder/${actor}/edit.do?folderId=${folder.id}"><spring:message code="folder.edit"/></a>
		</jstl:if>
	</display:column>
</display:table>

<jstl:if test="${folder.isSystem==false || folderId==null}">
	<a href="folder/${actor}/create.do?folderId=${folderId}"><spring:message code="folder.create"/></a>
</jstl:if>


<jstl:if test="${folderId!=null}">
	<hr>
	<strong><spring:message code="folder.messages"/>:</strong>
	<display:table name="messages" id="message" requestURI="message/list.do?folderId=${folderId}" class="displaytag" pagesize="5">
	<display:column titleKey="folder.message.date">
		<jstl:out value="${message.sentMoment}"/>
	</display:column>
	<display:column titleKey="folder.message.sender">
		<jstl:out value="${message.sender.name}"/>
	</display:column>
	<display:column titleKey="folder.message.subject">
		<jstl:out value="${ message.subject}"/>
	</display:column>
	<display:column titleKey="folder.message.priority">
		<jstl:out value="${message.priority}"/>
	</display:column>
	<display:column >
		<a href="message/${actor}/display.do?messageId=${message.id}"><spring:message code="folder.message.details"/></a>
		<a href="message/${actor}/edit.do?messageId=${message.id}"><spring:message code="folder.message.edit"/></a>
	</display:column>
</display:table>
</jstl:if>


