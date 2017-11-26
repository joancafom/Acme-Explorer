<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<html>

<head>
	<jstl:choose>
		<jstl:when test="${trip.cancelationReason} == null">
			<title><jstl:out value="${trip.title}"></jstl:out></title>
		</jstl:when>
		
		<jstl:otherwise>
			<title><jstl:out value="${trip.title}"></jstl:out> - <spring:message code="trip.cancelled"/></title>
		</jstl:otherwise>
	</jstl:choose>
</head>

<body>
	<div>
		<p><spring:message code="trip.ticker"/>: <jstl:out value="${trip.ticker}"/></p>
	</div>
	
	<div>
		<p><spring:message code="trip.description"/>: <jstl:out value="${trip.description}"/></p>
	</div>
	
	<div>
		<p><spring:message code="trip.price"/>: <jstl:out value="${trip.price}"/>&euro;</p>
	</div>
	
	<div>
		<p><spring:message code="trip.startingDate"/>: <fmt:formatDate value="${trip.startingDate}" pattern="{0, date, dd/MM/yyyy HH:mm}"/></p>
	</div>
	
	<div>
		<p><spring:message code="trip.endingDate"/>: <fmt:formatDate value="${trip.endingDate}" pattern="{0, date, dd/MM/yyyy HH:mm}"/></p>
	</div>
	
	<div>
		<p><spring:message code="trip.requirements"/>: <jstl:out value="${trip.requirements}"/></p>
	</div>
	
	<div>
		<jstl:if test="${trip.CancelationReason} != null">
			<p><spring:message code="trip.cancelationReason"/>: <jstl:out value="${trip.cancelationReason}"></jstl:out></p>
		</jstl:if>
	</div>
	
	<div>
		<img alt="<spring:message code="sponsorship.bannerUrl"/>" src="${sponsorship.bannerUrl}"/>
	</div>
	
	<div>
		<p><spring:message code="stories"/>:</p>
		<display:table name="stories" id="story" class="displaytag">
			<display:column property="title" title="<spring:message code="story.title"/>" sortable="true"></display:column>
			<display:column property="text" title="<spring:message code="story.text"/>"></display:column>
			
			<display:column title="<spring:message code="story.attachments"/>">
				<jstl:choose>
					<jstl:when test="${stories} == null">
						<jstl:out value="-"></jstl:out>
					</jstl:when>
					
					<jstl:otherwise>
						<jstl:forEach var="story" items="${stories}">
							<jstl:forEach var="attachment" items="${story.attachments}">
								<jstl:out value="${attachment}"/>
								<br/>
							</jstl:forEach>
						</jstl:forEach>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column property="explorer" title="<spring:message code="story.explorer"/>" sortable="true"></display:column>				
		</display:table>
	</div>
	
	<security:authorize access="hasRole('MANAGER')">
	<div>
		<p><spring:message code="notes"/>:</p>
		<a href="note/manager/list.do"><spring:message code="note.list"/></a>
	</div>
	</security:authorize>
	
	<div>
		<p><spring:message code="auditions"/>:</p>
		<jstl:forEach var="audition" items="${auditions}">
			<a href="audition/display.do?auditionId=${audition.id}"><jstl:out value="${audition.title}"/></a>
			<br/>
		</jstl:forEach>
	</div>
	
	<security:authorize access="hasRole('MANAGER')">
	<div>
		<p><spring:message code="tripApplications"/>:</p>
		<a href="tripApplication/manager/list.do"><spring:message code="tripApplication.list"/></a>
	</div>
	</security:authorize>
	
	<div>
		<p><spring:message code="tagValues"/>:</p>
		<jstl:forEach var="tagValue" items="${tagValues}">
			<jstl:out value="#${tagValue.value}"></jstl:out>
			<br/>
		</jstl:forEach>
	</div>
	
	<div>
		<p><spring:message code="legalTexts"/>:</p>
		<display:table name="legalTexts" id="legalText" class="displaytag">
			<display:column property="title" title="<spring:message code="legalText.title"/>" sortable="true"></display:column>
			<display:column property="body" title="<spring:message code="legalText.body"/>"></display:column>
		</display:table>
	</div>
	
	<div>
		<p><spring:message code="stages"/>:</p>
		<display:table name="stages" id="stage" class="displaytag">
			<display:column property="number" title="<spring:message code="stage.number"/>" sortable="true"></display:column>
			<display:column property="title" title="<spring:message code="stage.title"/>"></display:column>
			<display:column property="description" title="<spring:message code="stage.description"/>"></display:column>
			<display:column property="price" title="<spring:message code="stage.price"/>">
				<jstl:out value="${stage.price}&euro;"></jstl:out>
			</display:column>
		</display:table>
	</div>
	
	<div>
		<p><spring:message code="category"/>:</p>
		<jstl:out value="${category.name}"></jstl:out>
	</div>
	
	<security:authorize access="hasRole('MANAGER')">
	<div>
		<p><spring:message code="survivalClasses"/>:</p>
		<a href="survivalClass/manager/list.do"><spring:message code="survivalClass.list"/></a>
	</div>
	</security:authorize>
	
	<security:authorize access="!hasRole('MANAGER')">
	<div>
		<p><spring:message code="manager"/>:</p>
		<jstl:out value="${manager.surname}"/>, <jstl:out value="${manager.name}"/>
	</div>
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
	<div>
		<a href="tripApplication/explorer/create.do"><spring:message code="tripApplication.create"/></a>
	</div>
	</security:authorize>
</body>

</html>