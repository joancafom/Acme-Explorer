<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="finder.keyword"/>: <jstl:out value="${finder.keyword}"/></p>

<spring:message code="price.format" var="priceFormat"></spring:message>
<p><spring:message code="finder.minRange"/>: <fmt:formatNumber type="currency" currencySymbol="&#8364;" pattern="${priceFormat}" value="${finder.minRange}"/></p>
<p><spring:message code="finder.maxRange"/>: <fmt:formatNumber type="currency" currencySymbol="&#8364;" pattern="${priceFormat}" value="${finder.maxRange}"/></p>

<spring:message code="date.format2" var="dateFormat"></spring:message>
<p><spring:message code="finder.minDate"/>: <fmt:formatDate value="${finder.minDate}" pattern="${dateFormat}"/></p>
<p><spring:message code="finder.maxDate"/>: <fmt:formatDate value="${finder.maxDate}" pattern="${dateFormat}"/></p>

<a href="trip/explorer/list.do?finderId=${finder.id}"><spring:message code="finder.list.trip"/></a>
<br/>
<a href="finder/explorer/edit.do?finderId=${finder.id}"><spring:message code="finder.edit"/></a>