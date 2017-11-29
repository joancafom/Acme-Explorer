<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actor" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actor" value="explorer"></jstl:set>
</security:authorize>

<display:table name="tripApplications" id="tripApplication" requestURI="tripApplication/${actor}/list.do" pagesize="5" class="displaytag">
	
	<display:column property="moment" title="<spring:message code="tripApplication.title" />" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />
	<display:column property="status" title="<spring:message code="tripApplication.status" />" sortable="true" />
	<display:column>
		<jstl:forEach var="comment" items="tripApplication.comments" >
			<jstl:choose>
				<jstl:when test="${comment == null }">
					-
				</jstl:when>
				<jstl:otherwise>
					<jstl:out value="${comment}"></jstl:out>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</display:column>
	<display:column>
		<jstl:choose>
			<jstl:when test="${tripApplication.creditCard == null }">
				<security:authorize access="hasRole('MANAGER')">
					<spring:message code="creditCard.no" />
				</security:authorize>
				<security:authorize access="hasRole('EXPLORER')">
					-
				</security:authorize>
			</jstl:when>
			<jstl:otherwise>
				<security:authorize access="hasRole('MANAGER')">
					<spring:message code="creditCard.yes" />
				</security:authorize>
				<security:authorize access="hasRole('EXPLORER')">
					<jstl:out value="${tripApplication.creditCard.number}"></jstl:out>
				</security:authorize>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('EXPLORER')">
	<a href="tripApplication/explorer/create.do"><spring:message code="tripApplication.new" /></a>
</security:authorize>
