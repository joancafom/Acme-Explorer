<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><tiles:insertAttribute name="title" />: <jstl:out value="${rootCategory.name}" /></h1>

<ul>

	<jstl:forEach var="childNode" items="${rootCategory.childCategory}">
		<li><a href="category/list.do?rootCategoryId=${childNode.id}"><jstl:out value="${childNode.name}" /></a>  
		<a href="trip/list.do?categoryId=${childNode.id}"><spring:message code="trip.search" /></a></li>
	</jstl:forEach>
	
</ul>
