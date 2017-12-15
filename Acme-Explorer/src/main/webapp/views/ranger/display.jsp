<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Display a ranger -->
<p><spring:message code="ranger.name"/>: <jstl:out value="${ranger.name}"/></p>
<p><spring:message code="ranger.surname"/>: <jstl:out value="${ranger.surname}"/></p>
<p><spring:message code="ranger.email"/>: <jstl:out value="${ranger.email}"/></p>
<p><spring:message code="ranger.phoneNumber"/>: <jstl:out value="${ranger.phoneNumber}"/></p>

<p><spring:message code="ranger.socialIDs"/>:</p> 

<display:table name="ranger.socialIDs" id="socialID" requestURI="${socialIDRequestURI}" class="displaytag">
	<display:column property="nick" titleKey="socialID.nick" sortable="true"/>
	<display:column property="nameSocialNetwork" titleKey="socialID.nameSocialNetwork" sortable="true"/>
	<display:column titleKey="socialID.link" sortable="true">
		<a href="${socialID.link}"><jstl:out value="${socialID.link}"/></a>
	</display:column>
	<display:column titleKey="socialID.photo" sortable="true">
		<a href="${socialID.photo}"><jstl:out value="${socialID.photo}"/></a>
	</display:column>
</display:table>

<p><spring:message code="ranger.curriculum"/>: <a href="curriculum/display.do?curriculumId=${ranger.curriculum.id}"><spring:message code="ranger.curriculum.display"/></a></p>