<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Create a Trip -->

<form:form action="systemConfiguration/admin/edit.do" modelAttribute="systemConfiguration">

		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="nextTicker"/>
	
		<form:label path="VAT">
			<spring:message code="sysConfig.VAT"/>
		</form:label>
		<form:input path="VAT"/>
		<form:errors cssClass="error" path="VAT"/>
		<br />
		
		<form:label path="banner">
			<spring:message code="sysConfig.banner"/>
		</form:label>
		<form:input path="banner"/>
		<form:errors cssClass="error" path="banner"/>
		<br />
		
		<form:label path="spamWords">
			<spring:message code="sysConfig.spamWords"/>
		</form:label>
		<form:textarea path="spamWords"/>
		<form:errors cssClass="error" path="spamWords"/>
		<br />
	
		<form:label path="countryCode">
			<spring:message code="sysConfig.countryCode"/>
		</form:label>
		<form:input path="countryCode"/>
		<form:errors cssClass="error" path="countryCode"/>
		<br />
		
		<form:label path="cacheTime">
			<spring:message code="sysConfig.cacheTime"/>
		</form:label>
		<form:input path="cacheTime"/>
		<form:errors cssClass="error" path="cacheTime"/>
		<br />
		
		<form:label path="maxNumResults">
			<spring:message code="sysConfig.maxNumResults"/>
		</form:label>
		<form:input path="maxNumResults"/>
		<form:errors cssClass="error" path="maxNumResults"/>
		<br />
	
		<input type="submit" name="save" value="<spring:message code="sysConfig.save"/>">
		<input type="button" name="cancel" value="<spring:message code="sysConfig.cancel"/>" onclick="javascript: relativeRedir('systemConfiguration/admin/display.do');" />
	
</form:form>