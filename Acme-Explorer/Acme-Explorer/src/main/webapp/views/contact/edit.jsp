<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<spring:message code="phoneNumber.notMatched" var="patternMatchError"/>

<script>
	window.onload = function(){
	document.getElementById('contactForm').onsubmit = function checkPhoneNumber(){
		var pattern = new RegExp(/^(\+[0-9]{1,3} \([0-9]{1,3}\) [0-9]{4,}|\+[0-9]{1,3} [0-9]{4,}|[0-9]{4,})$/);
		var phoneNumber = document.getElementById('phoneNumber').value;
		
		var testRes = pattern.test(phoneNumber);
		var res = true;
		if(!testRes){
			res = confirm("${patternMatchError}");
		}
		return res;
		
		};
	};
</script>

<form:form action="contact/explorer/edit.do" id="contactForm" modelAttribute="contact">
	<form:label path="name"><spring:message code="contact.name"/>:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	
	<br/>
	
	<form:label path="email"><spring:message code="contact.email"/>:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	
	<br/>
	
	<form:label path="phoneNumber"><spring:message code="contact.phoneNumber"/>:</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="contact.save"/>">
	<input type="button" name="cancel" value="<spring:message code="contact.cancel"/>" onclick="javascript: relativeRedir('actor/explorer/display.do');" />
</form:form>