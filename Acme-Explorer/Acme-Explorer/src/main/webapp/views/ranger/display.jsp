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
<p><strong><spring:message code="ranger.name"/>:</strong> <jstl:out value="${actor.name}"/></p>
<p><strong><spring:message code="ranger.surname"/>:</strong> <jstl:out value="${actor.surname}"/></p>
<p><strong><spring:message code="ranger.email"/>:</strong> <jstl:out value="${actor.email}"/></p>
<p><strong><spring:message code="ranger.phoneNumber"/>:</strong> <jstl:out value="${actor.phoneNumber}"/></p>
<p><strong><spring:message code="ranger.address"/>:</strong> <jstl:out value="${actor.address}"/></p>

<jstl:if test="${ownProfile}">
	<a href="actor/ranger/edit.do"><spring:message code="ranger.edit"/></a>
</jstl:if>
	
<h1><spring:message code="ranger.socialIDs"/>:</h1>

<display:table name="socialIDs" id="socialID" class="displaytag">
	<display:column titleKey="ranger.socialID.nick" sortable="true">
		<jstl:out value="${socialID.nick}"/>	
	</display:column>

	<display:column titleKey="ranger.socialID.nameSocialNetwork" sortable="true">
		<jstl:out value="${socialID.nameSocialNetwork}"/>	
	</display:column>

	<display:column titleKey="ranger.socialID.link" sortable="true">
		<a href="<jstl:out value="${socialID.link}"/>"> ${socialID.link}</a>	
	</display:column>

	<display:column titleKey="actor.socialID.photo" sortable="true">
		<a href="<jstl:out value="${socialID.photo}"/>"> ${socialID.photo} </a>	
	</display:column>

	<jstl:if test="${ownProfile}">	
		<display:column>
			<a href="socialID/ranger/edit.do?socialIDId=${socialID.id}"><spring:message code="ranger.socialID.edit"/></a>
		</display:column>
	</jstl:if>
</display:table>
	
<jstl:if test="${ownProfile}">
	<a href="socialID/ranger/create.do"><spring:message code="ranger.socialID.create"/></a>
</jstl:if>

<h1><spring:message code="ranger.curriculum"/></h1>:
<jstl:if test="${curriculumURI != ''}">
	<a href="${curriculumURI}"><spring:message code="ranger.curriculum.display"/></a>
</jstl:if>
<jstl:if test="${curriculumURI == ''}">
	<spring:message code="ranger.noCurriculum"/>
	<jstl:if test="${ownProfile}">
		<a href="curriculum/ranger/create.do"><spring:message code="ranger.curriculum.create"/></a>
	</jstl:if>
</jstl:if>