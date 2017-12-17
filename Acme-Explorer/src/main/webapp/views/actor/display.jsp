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
	<jstl:set var="url" value="explorer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="url" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="url" value="auditor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
	<jstl:set var="url" value="sponsor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="url" value="admin"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('RANGER')">
	<jstl:set var="url" value="ranger"></jstl:set>
</security:authorize>

<!-- Display an actor -->
<p><spring:message code="actor.name"/>: <jstl:out value="${actor.name}"/></p>
<p><spring:message code="actor.surname"/>: <jstl:out value="${actor.surname}"/></p>
<p><spring:message code="actor.email"/>: <jstl:out value="${actor.email}"/></p>
<p><spring:message code="actor.phoneNumber"/>: <jstl:out value="${actor.phoneNumber}"/></p>
<br>
<a href="actor/${url}/edit.do"><spring:message code="actor.edit"/></a>

<p><spring:message code="actor.socialIDs"/>:</p>

<display:table name="socialIDs" id="socialID" pagesize="3" class="displaytag">
	<display:column titleKey="actor.socialID.nick" sortable="true">
		<jstl:out value="${socialID.nick}"/>	
	</display:column>
	
	<display:column titleKey="actor.socialID.nameSocialNetwork" sortable="true">
		<jstl:out value="${socialID.nameSocialNetwork}"/>	
	</display:column>
	
	<display:column titleKey="actor.socialID.link" sortable="true">
		<a href="<jstl:out value="${socialID.link}"/>"> ${socialID.link}</a>	
	</display:column>
	
	<display:column titleKey="actor.socialID.photo" sortable="true">
		<a href="<jstl:out value="${socialID.photo}"/>"> ${socialID.photo} </a>	
	</display:column>
	
	<display:column>
		<a href="socialID/edit.do?socialIDId=${socialId.id}"><spring:message code="actor.socialID.edit"/></a>
	</display:column>
</display:table>
<a href="socialID/create.do"><spring:message code="actor.socialID.create"/></a>

<security:authorize access="hasRole('EXPLORER')">
	<p><spring:message code="actor.explorer.emergencyContacts"/>:</p>
	<display:table name="emergencyContacts" id="emergencyContact" pagesize="3" class="displaytag">
		<display:column titleKey="actor.explorer.emergencyContact.name" sortable="true">
			<jstl:out value="${emergencyContact.name}"/>
		</display:column>
	
		<display:column titleKey="actor.explorer.emergencyContact.email" sortable="true">
			<jstl:out value="${emergencyContact.email}"/>
		</display:column>
	
		<display:column titleKey="actor.explorer.emergencyContact.phoneNumber" sortable="true">
			<jstl:out value="${emergencyContact.phoneNumber}"/>
		</display:column>
		
		<display:column>
			<a href="contact/explorer/edit.do?contactId="><spring:message code="actor.explorer.emergencyContact.edit"/></a>
		</display:column>
	</display:table>
	<a href="contact/explorer/create.do"><spring:message code="actor.explorer.emergencyContact.create"/></a>
	<p><spring:message code="actor.explorer.stories"/>: <a href="story/manager/list.do?explorerId=${explorer.id}"><spring:message code="actor.listAll"/></a></p>
	<p><spring:message code="actor.explorer.tripApplications"/>: <a href="tripApplication/explorer/list.do"><spring:message code="actor.listAll"/></a></p>
	<p><spring:message code="actor.explorer.survivalClasses"/>: <a href="survivalClass/explorer/list.do"><spring:message code="actor.listAll"/></a></p>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<p><spring:message code="actor.manager.survivalClasses"/>: <a href="survivalClass/manager/list.do"><spring:message code="actor.listAll"/></a></p>
	<p><spring:message code="actor.manager.trips"/>: <a href="trip/manager/list.do"><spring:message code="actor.listAll"/></a></p>
</security:authorize>

<security:authorize access="hasRole('RANGER')">
	<p><spring:message code="actor.ranger.curriculum"/>: <a href="curriculum/display.do?curriculumId=${ranger.curriculum.id}"><spring:message code="actor.ranger.curriculum.display"/></a></p>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<p><spring:message code="actor.auditor.audits"/>: <a href="audit/auditor/list.do"><spring:message code="actor.listAll"/></a></p>
	<p><spring:message code="actor.auditor.notes"/>: <a href="note/auditor/list.do"><spring:message code="actor.listAll"/></a></p>
</security:authorize>

<security:authorize access="hasRole('SPONSOR')">
	<p><spring:message code="actor.sponsor.sponsorships"/>: <a href="sponsorship/sponsor/list.do"><spring:message code="actor.listAll"/></a></p>
</security:authorize>




