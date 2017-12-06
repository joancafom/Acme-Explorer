<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Display an explorer -->
<p><spring:message code="explorer.name"/>: <jstl:out value="${explorer.name}"/></p>
<p><spring:message code="explorer.surname"/>: <jstl:out value="${explorer.surname}"/></p>
<p><spring:message code="explorer.email"/>: <jstl:out value="${explorer.email}"/></p>
<p><spring:message code="explorer.phoneNumber"/>: <jstl:out value="${explorer.phoneNumber}"/></p>

<p><spring:message code="explorer.socialIDs"/>:</p>
<display:table name="socialIDs" id="socialID" requestURI="/socialID/explorer/list.do" pagesize="3" class="displaytag">
	<display:column titleKey="socialID.nick" sortable="true">
		<jstl:out value="${socialID.nick}"/>	
	</display:column>
	
	<display:column titleKey="socialID.nameSocialNetwork" sortable="true">
		<jstl:out value="${socialID.nameSocialNetwork}"/>	
	</display:column>
	
	<display:column titleKey="socialID.link" sortable="true">
		<a href="<jstl:out value="${socialID.link}"/>"></a>	
	</display:column>
	
	<display:column titleKey="socialID.photo" sortable="true">
		<a href="<jstl:out value="${socialID.photo}"/>"></a>	
	</display:column>
</display:table>

<p><spring:message code="explorer.emergencyContacts"/>:</p>
<display:table name="emergencyContacts" id="emergencyContact" requestURI="/contact/explorer/list.do" pagesize="3" class="displaytag">
	<display:column titleKey="emergencyContact.name" sortable="true">
		<jstl:out value="${emergencyContact.name}"/>
	</display:column>
	
	<display:column titleKey="emergencyContact.email" sortable="true">
		<jstl:out value="${emergencyContact.email}"/>
	</display:column>
	
	<display:column titleKey="emergencyContact.phoneNumber" sortable="true">
		<jstl:out value="${emergencyContact.phoneNumber}"/>
	</display:column>
</display:table>

<p><spring:message code="explorer.stories"/>: <a href="story/manager/list.do?explorerId=${explorer.id}">List All</a></p>