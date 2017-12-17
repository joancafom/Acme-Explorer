<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="actor.userAccount.passwordMatch" var="passwordMatchError"></spring:message>
<spring:message code="actor.phoneNumber.notMatched" var="patternMatchError"></spring:message>

<script type="text/javascript">

	window.onload = function(){
	document.getElementById('${actorClassName}').onsubmit = function checkPhoneNumber(){
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

	function checkPasswords(){
		var pass1 = document.getElementById('pass1').value;
		var pass2 = document.getElementById('pass2').value;

		if((pass1 == pass2) && pass1 != ""){
			
			document.getElementById('passwordMatchMessage').innerHTML = "";
			document.getElementById('submitButton').disabled = false;
		}else{
			document.getElementById('passwordMatchMessage').innerHTML = '${passwordMatchError}';
			document.getElementById('submitButton').disabled = true;
		}
	}
</script>
<jstl:if test="${actorClassName == 'explorer'}">

	<form:form action="${actionURI}" modelAttribute="explorer">
		
		<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount.authorities"/>
	
		<%-- Explorer Attributes --%>

		<form:hidden path="tripApplications"/>
		<form:hidden path="stories"/>
		<form:hidden path="survivalClasses"/>
		<form:hidden path="emergencyContacts"/>
		
		<%-- Edition Begins --%>
	
		<form:label path="name">
			<spring:message code="actor.name" />*
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"></form:errors>
		<br />
	
		<form:label path="surname">
			<spring:message code="actor.surname" />*
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname"></form:errors>
		<br />
	
		<form:label path="email">
			<spring:message code="actor.email" />*
		</form:label>
		<form:input path="email"/>
		<form:errors cssClass="error" path="email"></form:errors>
		<br />
	
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />*
		</form:label>
		<form:input path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"></form:errors>
		<br />
	
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />*
		</form:label>
		<form:password id="pass1" path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"></form:errors>
		<br />
	
		<%-- I don't want this to be sent --%>
		<label for="pass2">
			<spring:message code="actor.userAccount.repeatPassword" />*
		</label>
		<input id="pass2" type="password" onkeyup="checkPasswords()" />
		<p id="passwordMatchMessage"></p>
	
		<form:label path="address">
			<spring:message code="actor.address" />
		</form:label>
		<form:input path="address"/>
		<br />
	
		<form:label path="phoneNumber">
			<spring:message code="actor.phoneNumber" />
		</form:label>
		<form:input id="phoneNumber" path="phoneNumber"/>
		<form:errors cssClass="error" path="phoneNumber"></form:errors>
		<br />
	
		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('/welcome/index.do')" />
		<br />
	
		<input id="submitButton" disabled type="submit" name="save" value="<spring:message code="actor.save" />" />
	</form:form>
	
</jstl:if>
<jstl:if test="${actorClassName == 'ranger'}">
	
		<form:form action="${actionURI}" modelAttribute="ranger">
		
		<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount.authorities"/>

		<%-- Ranger Attributes --%>
	
		<form:hidden path="trips"/>
		<form:hidden path="curriculum"/>
	
		<%-- Edition Begins --%>
	
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"></form:errors>
		<br />
	
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input path="surname"/>
		<form:errors cssClass="error" path="surname"></form:errors>
		<br />
	
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input path="email"/>
		<form:errors cssClass="error" path="email"></form:errors>
		<br />
	
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />
		</form:label>
		<form:input path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"></form:errors>
		<br />
	
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />
		</form:label>
		<form:password id="pass1" path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"></form:errors>
		<br />
	
		<%-- I don't want this to be sent --%>
		<label for="pass2">
			<spring:message code="actor.userAccount.repeatPassword" />
		</label>
		<input id="pass2" type="password" onkeyup="checkPasswords()" />
		<p id="passwordMatchMessage"></p>
	
		<form:label path="address">
			<spring:message code="actor.address" />
		</form:label>
		<form:input path="address"/>
		<br />
	
		<form:label path="phoneNumber">
			<spring:message code="actor.phoneNumber" />
		</form:label>
		<form:input id="phoneNumber" path="phoneNumber"/>
		<form:errors cssClass="error" path="phoneNumber"></form:errors>
		<br />
	
		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('/welcome/index.do')" />
		<br />
	
		<input id="submitButton" disabled type="submit" name="save" value="<spring:message code="actor.save" />" />
	</form:form>
	
</jstl:if>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="url" value="explorer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="url" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="url" value="auditor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
	<jstl:set var="url" value="sponsor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="url" value="admin"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('RANGER')">
	<jstl:set var="url" value="ranger"></jstl:set>
</security:authorize>

<form:form action="actor/${url}/edit.do" modelAttribute="actor">
	<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount.authorities"/>
		
	<!-- Auditor -->
	<security:authorize access="hasRole('AUDITOR')">
		<form:hidden path="notes"/>
		<form:hidden path="audits"/>
	</security:authorize>
	<!-- Explorer -->
	<security:authorize access="hasRole('EXPLORER')">
		<form:hidden path="emergencyContacts"/>
		<form:hidden path="tripApplications"/>
		<form:hidden path="stories"/>
		<form:hidden path="survivalClasses"/>
	</security:authorize>
	<!-- Sponsor -->
	<security:authorize access="hasRole('SPONSOR')">
		<form:hidden path="sponsorships"/>
	</security:authorize>
	<!-- Admin -->
	<!-- Manager -->
	<security:authorize access="hasRole('MANAGER')">
		<form:hidden path="trips"/>
		<form:hidden path="survivalClasses"/>
	</security:authorize>
	<!-- Ranger -->
	<security:authorize access="hasRole('RANGER')">
		<form:hidden path="curriculum"/>
		<form:hidden path="trip"/>
	</security:authorize>
	
	<form:label path="name">Name:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	
	<form:label path="surname">Surname:</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	
	<form:label path="email">Email:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	
	<form:label path="phoneNumber">Phone number:</form:label>
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	
	<form:label path="address">Address:</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	
	<input type="submit" name="save" value="Save"/>
</form:form>