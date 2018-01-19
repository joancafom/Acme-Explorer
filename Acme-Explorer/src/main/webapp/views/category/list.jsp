<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2><spring:message code="category"/>: <jstl:out value="${actor.name}"/><jstl:out value="${rootCategory.name}" /></h2>

<display:table name="rootCategory.childCategories" id="childNode" requestURI="category/${actorWS}list.do" class="displaytag">
	<display:column titleKey="category.children" sortable="true">
		<a href="category/${actorWS}list.do?rootCategoryId=${childNode.id}"><jstl:out value="${childNode.name}" /></a>
	</display:column>
	<display:column>
		<a href="trip/${actorWS}list.do?categoryId=${childNode.id}"><spring:message code="trip.search" /></a>
	</display:column>
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="category/admin/edit.do?categoryId=${childNode.id}"><spring:message code="category.edit"/></a>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<a href="category/admin/create.do?parentCategoryId=${rootCategory.id}"><spring:message code="category.create"/></a>
</security:authorize>