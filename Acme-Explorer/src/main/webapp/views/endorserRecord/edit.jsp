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
	document.getElementById('endorserRecord').onsubmit = function checkPhoneNumber(){
		var pattern = new RegExp(/^(\+[0-9]{1,3} \([0-9]{1,3}\) [0-9]{4,}|\+[0-9]{1,3} [0-9]{4,}|[0-9]{4,})$/);
		var phoneNumber = document.getElementById('phoneNumber').value;
		
		if(phoneNumber != ""){
			var testRes = pattern.test(phoneNumber);
			var res = true;
			if(!testRes){
				res = confirm("${patternMatchError}");
			}
			return res;
		}
		
		};
		
	};
</script>

<form:form action="endorserRecord/ranger/edit.do" modelAttribute="endorserRecord">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="curriculum"/>
	
	<form:label path="fullName">
		<spring:message code="endorserRecord.fullName"/>
	</form:label>
	<form:input path="fullName"/>
	<form:errors cssClass="error" path="fullName"/>
	
	<br/>
	
	<form:label path="email">
		<spring:message code="endorserRecord.email"/>
	</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	
	<br/>
	
	<form:label path="phoneNumber">
		<spring:message code="endorserRecord.phoneNumber"/>
	</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	
	<br/>
	
	<form:label path="linkedInProfile">
		<spring:message code="endorserRecord.linkedInProfile"/>
	</form:label>
	<form:input path="linkedInProfile"/>
	<form:errors cssClass="error" path="linkedInProfile"/>
	
	<br/>
	
	<form:label path="comments">
		<spring:message code="endorserRecord.comments"/>
	</form:label>
	<form:input path="comments"/>
	<form:errors cssClass="error" path="comments"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="endorserRecord.save"/>">
	<jstl:if test="${endorserRecord.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="endorserRecord.delete"/>">
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message	code="endorserRecord.cancel" />" onclick="javascript: relativeRedir('curriculum/ranger/display.do?curriculumId=${endorserRecord.curriculum.id}');" />
</form:form>
