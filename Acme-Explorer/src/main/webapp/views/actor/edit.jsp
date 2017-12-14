<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="actor.userAccount.passwordMatch" var="matchError"></spring:message>

<script>
	function checkPasswords(){
		var pass1 = document.getElementById('pass1');
		var pass2 = document.getElementById('pass2');

		if(pass1.value == pass2.value){
			
			document.getElementById('passwordMatchMessage').innerHTML = "";
			document.getElementById('submitButton').disabled = false;
		}else{
			document.getElementById('passwordMatchMessage').innerHTML = '${matchError}';
			document.getElementById('submitButton').disabled = true;
		}
		
		
	}
</script>

<form:form action="${actionURI}" modelAttribute="actor">
	
	<%-- Common Actor Attributes --%>
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isSuspicious"/>
	<form:hidden path="socialIDs"/>
	<form:hidden path="folders"/>
	<form:hidden path="sentMessages"/>
	<form:hidden path="receivedMessages"/>
	<form:hidden path="userAccount.authorities"/>
	
	<%-- Explorer Attributes --%>
	
	<jstl:if test="${actorClassName == 'explorer'}">
		<form:hidden path="tripApplications"/>
		<form:hidden path="stories"/>
		<form:hidden path="survivalClasses"/>
		<form:hidden path="emergencyContacts"/>
	</jstl:if>
	
		<%-- Ranger Attributes --%>
	
	<jstl:if test="${actorClassName == 'ranger'}">
		<form:hidden path="trips"/>
		<form:hidden path="curriculum"/>
	</jstl:if>
	
	<form:label path="name">
		<spring:message code="actor.name" />
	</form:label>
	<form:input path="name"/>
	<br />
	
	<form:label path="surname">
		<spring:message code="actor.surname" />
	</form:label>
	<form:input path="surname"/>
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
	<input id="pass2" type="password" onchange="checkPasswords()" />
	<p id="passwordMatchMessage"></p>
	
	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address"/>
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="actor.phoneNumber" />
	</form:label>
	<form:input path="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"></form:errors>
	<br />
	
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('/welcome/index.do')" />
	<br />
	
	<input id="submitButton" disabled type="submit" name="save" value="<spring:message code="actor.save" />" />

</form:form>