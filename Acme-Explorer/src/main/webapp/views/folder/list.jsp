<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="message/create.do"><spring:message code="folder.message.send"/></a><br>
<br>
<display:table name="folders" id="folder" requestURI="folder/list.do" class="displaytag">
	<display:column titleKey="folder.name">
		<a href="folder/list.do?folderId=${folder.id}"><jstl:out value="${folder.name}"/></a>
	</display:column>
	<display:column>
		<jstl:if test="${folder.isSystem == false}">
			<a href="folder/edit.do?folderId=${folder.id}"><spring:message code="folder.edit"/></a>
		</jstl:if>
	</display:column>
</display:table>

<a href="folder/create.do?folderId=${folderId}"><spring:message code="folder.create"/></a>

<jstl:if test="${folderId!=null}">
	<p><spring:message code="folder.messages"/>:</p>
	<display:table name="messages" id="message" requestURI="message/list.do?folderId=${folderId}" class="displaytag">
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
		<a href="message/display.do?messageId=${message.id}"><spring:message code="folder.message.details"/></a>
		<a href="message/edit.do?messageId=${message.id}"><spring:message code="folder.message.edit"/></a>
	</display:column>
</display:table>
</jstl:if>


