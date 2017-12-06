<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="sponsorships" id="sponsorship" requestURI="sponsorship/sponsor/list.do" pagesize="5" class="displaytag">
	<display:column titleKey="sponsorship.trip.title" sortable="true">
		<jstl:out value="${sponsorship.trip.title}"></jstl:out>
	</display:column>
	
	<display:column titleKey="sponsorship.infoPageLink" sortable="true">
		<a href="<jstl:out value='${sponsorship.infoPageLink}'/>"></a>
	</display:column>
	
	<display:column titleKey="sponsorship.creditCard.numer" sortable="true">
		<jstl:out value="${sponsorship.creditCard}"></jstl:out>
	</display:column>
	
	<display:column>
		<a href="/sponsorship/sponsor/edit.do?sponsorshipId=${sponsorship.id}"><spring:message code="sponsorship.edit"/></a>
		<a href="/sponsorship/sponsor/display.do?sponsorshipId=${sponsorship.id}"><spring:message code="sponsorship.display"/></a>
	</display:column>
	
</display:table>
<a href="/sponsorship/sponsor/create.do"><spring:message code="sponsorship.create"/></a>