<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="/sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="sponsor"/>
	
	<form:label path="bannerUrl">
		<spring:message code="sponsorship.bannerUrl"/>
	</form:label>
	<form:input path="bannerUrl"/>
	<form:errors cssClass="errors" path="bannerUrl"/>
	
	
	<form:label path="infoPageLink">
		<spring:message code="sponsorship.infoPageLink"/>
	</form:label>
	<form:input path="infoPageLink"/>
	<form:errors cssClass="errors" path="infoPageLink"/>
	
	
	<form:label path="trip">
		<spring:message code="sponsorship.trip"/>
	</form:label>
	<form:select path="trip">
		<form:option value="0" label="---"/>
		<form:options items="${trips}"
			itemValue="id"
			itemLabel="name"/>
	</form:select>
	<form:errors cssClass="errors" path="trip"/>
	
	<form:label path="creditCard.holderName">
		<spring:message code="sponsorship.creditCard.holderName"/>
	</form:label>
	<form:input path="creditCard.holderName"/>
	<form:errors cssClass="errors" path="creditCard.holderName"/>
	
	<form:label path="creditCard.brandName">
		<spring:message code="sponsorship.creditCard.brandName"/>
	</form:label>
	<form:input path="creditCard.brandName"/>
	<form:errors cssClass="errors" path="creditCard.brandName"/>
	
	<form:label path="creditCard.number">
		<spring:message code="sponsorship.creditCard.number"/>
	</form:label>
	<form:input path="creditCard.number"/>
	<form:errors cssClass="errors" path="creditCard.number"/>
	
	<form:label path="creditCard.CVV">
		<spring:message code="sponsorship.creditCard.CVV"/>
	</form:label>
	<form:input path="creditCard.CVV"/>
	<form:errors cssClass="errors" path="creditCard.CVV"/>
	
	<form:label path="creditCard.month">
		<spring:message code="sponsorship.creditCard.month"/>
	</form:label>
	<form:input path="creditCard.month"/>
	<form:errors cssClass="errors" path="creditCard.month"/>
	
	<form:label path="creditCard.year">
		<spring:message code="sponsorship.creditCard.year"/>
	</form:label>
	<form:input path="creditCard.year"/>
	<form:errors cssClass="errors" path="creditCard.year"/>

	<input type="submit" name="save" value="<spring:message code="sponsorship.save"/>"/>
	<input type="button" name="cancel" value="<spring:message	code="sponsorship.cancel" />" onclick="javascript: relativeRedir('sponsorship/auditor/list.do');" />
</form:form>