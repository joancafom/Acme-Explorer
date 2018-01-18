<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="now" class="java.util.Date" />

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

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${now < trip.publicationDate}">
		<a href="stage/manager/create.do?tripId=${trip.id}"><spring:message code="stage.create"/></a>
	</jstl:if>
</security:authorize>

<p><spring:message code="trip.requirements"/>: <jstl:out value="${trip.requirements}"/></p>

<p><spring:message code="category"/>: <jstl:out value="${trip.category.name}"></jstl:out></p>

<p><spring:message code="trip.manager"/>: <jstl:out value="${trip.manager.surname}"/>, <jstl:out value="${trip.manager.name}"/></p>

<spring:message code="trip.ranger"/>: <a href="${rangerURI}"><jstl:out value="${trip.ranger.surname}"/>, <jstl:out value="${trip.ranger.name}"/></a>

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
<a href="audit/${url}list.do?tripId=${trip.id}"><spring:message code="audit.list"/></a>
</p>

<p><spring:message code="stories"/>: 
<a href="story/${url}list.do?tripId=${trip.id}"><spring:message code="story.list"/></a>
</p>
<hr>
<h3><spring:message code="legalText"/>:</h3>
<p><strong><spring:message code="trip.legalText.title"/></strong>: <jstl:out value="${trip.legalText.title}"/></p>
<p><strong><spring:message code="trip.legalText.body"/></strong>: <jstl:out value="${trip.legalText.body}"/></p>
<p><strong><spring:message code="trip.legalText.laws"/></strong>: <jstl:out value="${trip.legalText.laws}"/></p>
<hr>
<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${myTrip}">
	<p><spring:message code="notes"/>: 
	<a href="note/manager/list.do?tripId=${trip.id}"><spring:message code="note.list"/></a>
	</p>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${myTrip}">
	<p><spring:message code="tripApplications"/>: 
	<a href="tripApplication/manager/list.do?tripId=${trip.id}"><spring:message code="tripApplication.list"/></a>
	</p>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${myTrip}">
	<p><spring:message code="sponsorships"/>: 
	<a href="sponsorship/manager/list.do?tripId=${trip.id}"><spring:message code="sponsorship.list"/></a>
	</p>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${canCreateTA}">
		<p><a href="tripApplication/explorer/create.do?tripId=${trip.id}">
			<spring:message code="tripApplication.create"/>
		</a></p>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${canAudit}">
		<p><a href="audit/auditor/create.do?tripId=${trip.id}">
			<spring:message code="audit.create"/>
		</a></p>
	</jstl:if>
</security:authorize>

<security:authorize access="!hasRole('MANAGER')">
 <p>
	<spring:message code="tagValues"/>:
	
	<jstl:forEach var="tagValue" items="${trip.tagValues}" varStatus="loop">
		<jstl:out value="${tagValue.tag.name}"/>:
		<jstl:out value="#${tagValue.value}"/>
		<jstl:if test="${!loop.last}">,</jstl:if>
	</jstl:forEach>
</p>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	 <h1><spring:message code="tagValues"/>:</h1>
	 <display:table name="trip.tagValues" id="tagValue" class="displaytag">
	 	<display:column property="tag.name" titleKey="tagValue.tag" sortable="true" />
	 	<display:column property="value" titleKey="tagValue.value" />
	 	<jstl:if test="${now < trip.publicationDate}">
			<display:column>
				<a href="tagValue/manager/edit.do?tagValueId=<jstl:out value="${tagValue.id}"></jstl:out>"><spring:message code="tagValue.edit"/></a>
			</display:column>
		</jstl:if>
	 </display:table>
	
	<jstl:if test="${now < trip.publicationDate}">
		<a href="tagValue/manager/create.do?tripId=${trip.id}"><spring:message code="tagValue.create"/></a>
	</jstl:if>
</security:authorize>

<h1><spring:message code="trip.stages"/>: </h1>
<display:table name="trip.stages" id="stage" requestURI="${stageRequestURI}" class="displaytag">
	<display:column property="number" titleKey="stage.number" sortable="true"/>
	
	<display:column property="title" titleKey="stage.title"/>
	
	<display:column property="description" titleKey="stage.description"/>
	
	<display:column titleKey="stage.price">
		<fmt:formatNumber type="currency" currencySymbol="&#8364;" pattern="${priceFormat}" value="${stage.price}" />
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${now < trip.publicationDate}">
			<display:column>
				<a href="stage/manager/edit.do?stageId=<jstl:out value="${stage.id}"></jstl:out>"><spring:message code="stage.edit"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${now < trip.publicationDate and trip.manager.id == principalId}">
		<a href="stage/manager/create.do?tripId=${trip.id}"><spring:message code="stage.create"/></a>
	</jstl:if>
</security:authorize>