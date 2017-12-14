<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


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

<a href="message/${url}create.do">Send a new message</a><br>

<display:table name="folders" id="folder" requestURI="folder/${url}list.do" class="displaytag">
	<display:column title="Name">
		<a href="message/${url}list.do?folderId=${folder.id}"><jstl:out value="${folder.name}"/></a>
	</display:column>
	
	<display:column>
		<a href="folder/${url}edit.do?folderId=${folder.id}">Edit</a>
	</display:column>
</display:table>

<a href="folder/${url}create.do">Create a new folder</a>