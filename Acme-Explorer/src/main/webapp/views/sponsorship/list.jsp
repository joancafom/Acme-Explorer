<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="sponsorships" id="sponsorship" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column titleKey="sponsorship.trip.ticker" sortable="true">
		<a href="${tripURI}${sponsorship.trip.id}"><jstl:out value="${sponsorship.trip.ticker}"></jstl:out></a>
	</display:column>
	
	<display:column titleKey="sponsorship.infoPageLink" sortable="true">
		<a href="${sponsorship.infoPageLink}"><jstl:out value='${sponsorship.infoPageLink}'/></a>
	</display:column>
	
	<display:column titleKey="sponsorship.creditCard.number" sortable="true">
		<jstl:out value="${sponsorship.creditCard.number}"></jstl:out>
	</display:column>

	<security:authorize access="hasRole('MANAGER')">
		<display:column titleKey="sponsorship.sponsor" sortable="true">
			<jstl:out value="${sponsorship.sponsor.surname}, ${sponsorship.sponsor.name}"></jstl:out>
		</display:column>
	</security:authorize>	
	
</display:table>

<security:authorize access="hasRole('SPONSOR')">
	<a href="sponsorship/sponsor/create.do"><spring:message code="sponsorship.create"/></a>
</security:authorize>