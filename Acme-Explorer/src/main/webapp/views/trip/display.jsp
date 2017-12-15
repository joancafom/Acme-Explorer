<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${trip.cancelationReason != null}">
	<spring:message code="trip.cancelled" var="tripCancelled"></spring:message>	
</jstl:if>
	
<jstl:if test="${trip.cancelationReason == null}">
	<jstl:set var="tripCancelled" value=""></jstl:set>
</jstl:if>

<h1><jstl:out value="${trip.title}${tripCancelled}"/></h1>

<jstl:if test="${sponsorship != null}">
	<spring:message code="sponsorship.bannerUrl" var="sponsorshipBannerUrl"/>
	<a href="${sponsorship.infoPageLink}">
		<img alt="${sponsorshipBannerUrl}" src="${sponsorship.bannerUrl}"/>
	</a>
</jstl:if>

<jstl:if test="${trip.cancelationReason != null}">
	<p><spring:message code="trip.cancelationReason"/>: <jstl:out value="${trip.cancelationReason}"/></p>
</jstl:if>

<p><spring:message code="trip.ticker"/>: <jstl:out value="${trip.ticker}"/></p>

<p><spring:message code="trip.description"/>: <jstl:out value="${trip.description}"/></p>

<spring:message code="price.format" var="priceFormat"></spring:message>
<p><spring:message code="trip.price"/>: <fmt:formatNumber type="currency" currencySymbol="&#8364;" pattern="${priceFormat}" value="${trip.price}" /></p>


<spring:message code="date.format2" var="dateFormat"></spring:message>
<p><spring:message code="trip.startingDate"/>: <fmt:formatDate value="${trip.startingDate}" pattern="${dateFormat}"/></p>

<p><spring:message code="trip.endingDate"/>: <fmt:formatDate value="${trip.endingDate}" pattern="${dateFormat}"/></p>

<p><spring:message code="trip.publicationDate"/>: <fmt:formatDate value="${trip.publicationDate}" pattern="${dateFormat}"/>

<p><spring:message code="trip.stages"/>:</p>
<display:table name="trip.stages" id="stage" requestURI="${stageRequestURI}" class="displaytag">
	<display:column property="number" titleKey="stage.number" sortable="true"/>
	
	<display:column property="title" titleKey="stage.title"/>
	
	<display:column property="description" titleKey="stage.description"/>
	
	<display:column titleKey="stage.price">
		<fmt:formatNumber type="currency" currencySymbol="&#8364;" pattern="${priceFormat}" value="${stage.price}" />
	</display:column>
</display:table>

<p><spring:message code="trip.requirements"/>: <jstl:out value="${trip.requirements}"/></p>

<p><spring:message code="category"/>: <jstl:out value="${trip.category.name}"></jstl:out></p>

 <p>
	<spring:message code="tagValues"/>:
	<jstl:set var="x" value="0"/>
	
	<jstl:forEach var="tagValue" items="${trip.tagValues}">
		<jstl:out value="#${tagValue.value}"/>
		<jstl:set var="x" value="${x+1}"/>
		<jstl:if test="${x} < ${tagValues.size()}">
			, 
		</jstl:if>
	</jstl:forEach>
</p>

<p><spring:message code="trip.manager"/>: <jstl:out value="${trip.manager.surname}"/>, <jstl:out value="${trip.manager.name}"/></p>

<spring:message code="trip.ranger"/>: <a href="ranger/display.do?rangerId=${trip.ranger.id}"><jstl:out value="${trip.ranger.surname}"/>, <jstl:out value="${trip.ranger.name}"/></a>

<security:authorize access="hasRole('MANAGER')">
	<p><spring:message code="survivalClasses"/>: <a href="survivalClass/manager/list.do?tripId=${trip.id}"><spring:message code="survivalClass.list"/></a></p>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<p><spring:message code="survivalClasses"/>: 
	<a href="survivalClass/explorer/list.do?tripId=${trip.id}"><spring:message code="survivalClass.list"/></a>
	</p>
</security:authorize>

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

<p><spring:message code="auditions"/>: 
<a href="audition/${url}list.do?tripId=${trip.id}"><spring:message code="audit.list"/></a>
</p>

<p><spring:message code="stories"/>: 
<a href="story/${url}list.do?tripId=${trip.id}"><spring:message code="story.list"/></a>
</p>

<p><spring:message code="legalTexts"/>: 
<a href="legalText/${url}list.do?tripId=${trip.id}"><spring:message code="legalText.list"/></a>
</p>

<security:authorize access="hasRole('MANAGER')">
	<p><spring:message code="notes"/>: 
	<a href="note/manager/list.do?tripId=${trip.id}"><spring:message code="note.list"/></a>
	</p>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<p><spring:message code="tripApplications"/>: 
	<a href="tripApplication/manager/list.do?tripId=${trip.id}"><spring:message code="tripApplication.list"/></a>
	</p>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<p><spring:message code="sponsorships"/>: 
	<a href="sponsorship/manager/list.do?tripId=${trip.id}"><spring:message code="sponsorship.list"/></a>
	</p>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<p><a href="tripApplication/explorer/create.do?tripId=${trip.id}">
	<spring:message code="tripApplication.create"/>
	</a></p>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<p><a href="audit/auditor/create.do?tripId=${trip.id}">
	<spring:message code="audit.create"/>
	</a></p>
</security:authorize>