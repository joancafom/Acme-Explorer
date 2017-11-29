<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul>
	<jstl:set var="depth" value="${1}"></jstl:set>
	<li><a href="trip/list.do?category=<jstl:out value="${rootCategory.name}" />"> <jstl:out value="${rootCategory.name}" /></a></li>
	<jstl:set var="element" value="${rootCategory}" scope="request"/>
	<jsp:include page="category.jsp" />
</ul>
