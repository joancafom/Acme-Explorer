<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:forEach var="element" items="${element.childCategories}">
	<jstl:forEach var="i" begin="0" end="${depth}" step="1"><ul></jstl:forEach>
	<li><a href="trip/list.do?category=<jstl:out value="${element.name}" />"> <jstl:out value="${element.name}" /></a></li>
	<jstl:forEach var="i" begin="0" end="${depth}" step="1"></ul></jstl:forEach>
	<jstl:set var="element" value="${element}" scope="request"/>
	<jstl:set var="depth" value="${depth + 1}"></jstl:set>
	<jsp:include page="category.jsp"/>
</jstl:forEach>
    
