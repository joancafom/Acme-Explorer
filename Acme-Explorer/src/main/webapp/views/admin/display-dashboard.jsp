<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<spring:message code="format.number" var="numberFormat"/>
<p>
<spring:message code="statistic.avg" /> <spring:message code="statistic.numberApplicationsTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripApplicationsPerTripStatistics[0]}" />
</p>
<p> 
<spring:message code="statistic.min" /> <spring:message code="statistic.numberApplicationsTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripApplicationsPerTripStatistics[1]}" /> 
</p>
<p>
<spring:message code="statistic.max" /> <spring:message code="statistic.numberApplicationsTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripApplicationsPerTripStatistics[2]}" /> 
</p>
<p>
<spring:message code="statistic.std" /> <spring:message code="statistic.numberApplicationsTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripApplicationsPerTripStatistics[3]}" /> 
</p>

<p>
<spring:message code="statistic.avg" /> <spring:message code="statistic.numberTripsPerManager" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerManagerStatistics[0]}" />
</p>
<p> 
<spring:message code="statistic.min" /> <spring:message code="statistic.numberTripsPerManager" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerManagerStatistics[1]}" /> 
</p>
<p>
<spring:message code="statistic.max" /> <spring:message code="statistic.numberTripsPerManager" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerManagerStatistics[2]}" /> 
</p>
<p>
<spring:message code="statistic.std" /> <spring:message code="statistic.numberTripsPerManager" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerManagerStatistics[3]}" /> 
</p>

<p>
<spring:message code="statistic.avg" /> <spring:message code="statistic.tripPrice" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPriceStatistics[0]}" />
</p>
<p> 
<spring:message code="statistic.min" /> <spring:message code="statistic.tripPrice" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPriceStatistics[1]}" /> 
</p>
<p>
<spring:message code="statistic.max" /> <spring:message code="statistic.tripPrice" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPriceStatistics[2]}" /> 
</p>
<p>
<spring:message code="statistic.std" /> <spring:message code="statistic.tripPrice" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPriceStatistics[3]}" /> 
</p>

<p>
<spring:message code="statistic.avg" /> <spring:message code="statistic.numberTripsPerRanger" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerRangerStatistics[0]}" />
</p>
<p> 
<spring:message code="statistic.min" /> <spring:message code="statistic.numberTripsPerRanger" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerRangerStatistics[1]}" /> 
</p>
<p>
<spring:message code="statistic.max" /> <spring:message code="statistic.numberTripsPerRanger" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerRangerStatistics[2]}" /> 
</p>
<p>
<spring:message code="statistic.std" /> <spring:message code="statistic.numberTripsPerRanger" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsPerRangerStatistics[3]}" /> 
</p>

<p>
<spring:message code="ratioTA" /> <spring:message code="status.pending" />: <fmt:formatNumber pattern="${numberFormat}" value="${pendingRatio}" />
</p>
<p> 
<spring:message code="ratioTA" /> <spring:message code="status.due" />: <fmt:formatNumber pattern="${numberFormat}" value="${dueRatio}" /> 
</p>
<p>
<spring:message code="ratioTA" /> <spring:message code="status.accepted" />: <fmt:formatNumber pattern="${numberFormat}" value="${acceptedRatio}" /> 
</p>
<p>
<spring:message code="ratioTA" /> <spring:message code="status.cancelled" />: <fmt:formatNumber pattern="${numberFormat}" value="${cancelledRatio}" /> 
</p>

<p>
<spring:message code="ratioT" />: <fmt:formatNumber pattern="${numberFormat}" value="${cancelledVsOrganisedRatio}" /> 
</p>

<p>
<spring:message code="statistic.min" /> <spring:message code="statistic.numberNotesPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${notesPerTripStatistics[0]}" />
</p>
<p> 
<spring:message code="statistic.max" /> <spring:message code="statistic.numberNotesPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${notesPerTripStatistics[1]}" /> 
</p>
<p>
<spring:message code="statistic.avg" /> <spring:message code="statistic.numberNotesPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${notesPerTripStatistics[2]}" /> 
</p>
<p>
<spring:message code="statistic.std" /> <spring:message code="statistic.numberNotesPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${notesPerTripStatistics[3]}" /> 
</p>

<p>
<spring:message code="statistic.min" /> <spring:message code="statistic.numberAuditPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${auditsPerTripStatistics[0]}" />
</p>
<p> 
<spring:message code="statistic.max" /> <spring:message code="statistic.numberAuditPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${auditsPerTripStatistics[1]}" /> 
</p>
<p>
<spring:message code="statistic.avg" /> <spring:message code="statistic.numberAuditPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${auditsPerTripStatistics[2]}" /> 
</p>
<p>
<spring:message code="statistic.std" /> <spring:message code="statistic.numberAuditPerTrip" />: <fmt:formatNumber pattern="${numberFormat}" value="${auditsPerTripStatistics[3]}" /> 
</p>

<p>
<spring:message code="ratioTripsAudit" />: <fmt:formatNumber pattern="${numberFormat}" value="${tripsWithAuditRatio}" /> 
</p>

<p>
<spring:message code="ratioRangerCurriculum" />: <fmt:formatNumber pattern="${numberFormat}" value="${rangersCurriculumRatio}" /> 
</p>

<p>
<spring:message code="ratioRangerCurriculumEndorsed" />: <fmt:formatNumber pattern="${numberFormat}" value="${rangersERRatio}" /> 
</p>

<p>
<spring:message code="ratioSuspiciousManagers" />: <fmt:formatNumber pattern="${numberFormat}" value="${suspiciousManagersRatio}" /> 
</p>

<p>
<spring:message code="ratioSuspiciousRangers" />: <fmt:formatNumber pattern="${numberFormat}" value="${suspiciousRangersRatio}" /> 
</p>

<p>
<spring:message code="trips.moreTA" />:
</p>

<display:table name="tripsMoreApplications" id="trip"  requestURI="admin/display-dashboard.do" class="displaytag">
	<display:column titleKey="trip.ticker">
		<jstl:out value="${trip.ticker}"/>	
	</display:column>
		
	<display:column titleKey="trip.nTa" sortable="true">
		<jstl:out value="${fn:length(trip.tripApplications)}"/>	
	</display:column>
</display:table>

<p>
<spring:message code="legaltext.references" />:

<display:table name="legalTexts" id="legt" requestURI="admin/display-dashboard.do" class="displaytag">
	<display:column titleKey="legaltext.title">
		<jstl:out value="${legt.title}"/>	
	</display:column>
		<display:column sortable="true" titleKey="legaltext.nref">
		<jstl:out value="${references[legt.id]}"/>	
	</display:column>
</display:table>

</p>