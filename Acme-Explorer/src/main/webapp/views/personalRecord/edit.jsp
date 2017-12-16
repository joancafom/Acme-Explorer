<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="phoneNumber.notMatched" var="patternMatchError"></spring:message>

<script type="text/javascript">

	window.onload = function(){
	document.getElementById('personalRecord').onsubmit = function checkPhoneNumber(){
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

<form:form action="personalRecord/ranger/edit.do" modelAttribute="personalRecord">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="curriculum"/>
	
	<form:label path="fullName">
		<spring:message code="personalRecord.fullName"/>
	</form:label>
	<form:input path="fullName"/>
	<form:errors cssClass="errors" path="fullName"/>
	
	<br/>
	
	<form:label path="photo">
		<spring:message code="personalRecord.photo"/>
	</form:label>
	<form:input path="photo"/>
	<form:errors cssClass="errors" path="photo"/>
	
	<br/>
	
	<form:label path="email">
		<spring:message code="personalRecord.email"/>
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="errors" path="email"/>
	
	<br/>
	
	<form:label path="phoneNumber">
		<spring:message code="personalRecord.phoneNumber"/>
	</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="errors" path="phoneNumber"/>
	
	<br/>
	
	<form:label path="linkedInProfile">
		<spring:message code="personalRecord.linkedInProfile"/>
	</form:label>
	<form:input path="linkedInProfile"/>
	<form:errors cssClass="errors" path="linkedInProfile"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="personalRecord.save"/>">
	<input type="button" name="cancel" value="<spring:message	code="personalRecord.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${personalRecord.curriculum.id}');" />
</form:form>
