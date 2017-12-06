<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Display a Sponsorship -->

<img src="<jstl:out value="${sponsorship.bannerUrl}"/>"/>
<p><spring:message code="sponsorship.sponsor"/>: <jstl:out value="${sponsorship.sponsor.name} ${sponsorship.sponsor.surname }"/></p>
<p><spring:message code="sponsorship.trip"/>: <jstl:out value="${sponsorship.trip.title}" /></p>
<p><spring:message code="sponsorship.infoPageLink"/>: <a href="<jstl:out value="${sponsorship.infoPageLink}"/>"></a></p>
<p><spring:message code="sponsorship.creditCard"/>: <jstl:out value="${sponsorship.creditCard.number}"/></p>
