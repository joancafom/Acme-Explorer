<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="sysConfig.VAT"/>: <jstl:out value="${systemConfiguration.VAT}" />
</p>

<p>
	<spring:message code="sysConfig.banner"/>: <jstl:out value="${systemConfiguration.banner}" />
</p>

<p>
	<spring:message code="sysConfig.spamWords"/>: <jstl:out value="${systemConfiguration.spamWords}" />
</p>

<p>
	<spring:message code="sysConfig.countryCode"/>: <jstl:out value="${systemConfiguration.countryCode}" />
</p>

<p>
	<spring:message code="sysConfig.cacheTime"/>: <jstl:out value="${systemConfiguration.cacheTime}" />
</p>

<p>
	<spring:message code="sysConfig.maxNumResults"/>: <jstl:out value="${systemConfiguration.maxNumResults}" />
</p>
<br>
<a href="systemConfiguration/admin/edit.do"><spring:message code="sysConfig.edit"/></a>