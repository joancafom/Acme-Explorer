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
	
	function checkPasswordsRegister(){
		var pass1 = document.getElementById('pass1').value;
		var pass2 = document.getElementById('pass2').value;

		if(pass1 == "" && pass2 == ""){
			document.getElementById('passwordMatchMessage').innerHTML = "";
			return true;
		}else if((pass1 == pass2) && pass1 != ""){
			document.getElementById('passwordMatchMessage').innerHTML = "";
			return false;
		}else{
			document.getElementById('passwordMatchMessage').innerHTML = '${passwordMatchError}';
			return true;
		}
	}
	
	function checkAllFields(){
		var name = document.getElementById('name').value;
		var surname = document.getElementById('surname').value; 
		var email = document.getElementById('email').value; 
		var username = document.getElementById('username').value; 
		var pass1 = document.getElementById('pass1').value; 
		var pass2 = document.getElementById('pass2').value; 
		
		if( name != "" && surname != "" && email != "" && username != "" && pass1 != "" && pass2 != ""){
			document.getElementById('submitButton').disabled = checkPasswordsRegister();
		}else{
			document.getElementById('submitButton').disabled = true;
			checkPasswordsRegister();
		}
	}
</script>
<jstl:if test="${actorClassName == 'explorer' && explorer.id==0}">
	
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
		<form:hidden path="finder"/>
		
		<%-- Edition Begins --%>
	
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input id="name" onkeyup="checkAllFields()" path="name"/>
		<form:errors cssClass="error" path="name"></form:errors>
		<br />
	
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input id="surname" onkeyup="checkAllFields()" path="surname"/>
		<form:errors cssClass="error" path="surname"></form:errors>
		<br />
	
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input id="email" onkeyup="checkAllFields()" path="email"/>
		<form:errors cssClass="error" path="email"></form:errors>
		<br />
	
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />
		</form:label>
		<form:input id="username" onkeyup="checkAllFields()" path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"></form:errors>
		<br />
	
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />
		</form:label>
		<form:password id="pass1" onkeyup="checkAllFields()" path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"></form:errors>
		<br />
	
		<%-- I don't want this to be sent --%>
		<label for="pass2">
			<spring:message code="actor.userAccount.repeatPassword" />
		</label>
		<input id="pass2" type="password" onkeyup="checkAllFields()" />
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

<jstl:if test="${actorClassName == 'explorer' && explorer.id!=0}">
	<form:form action="actor/explorer/edit.do" modelAttribute="explorer">
	<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount"/>
		
	<!-- Explorer -->
	<security:authorize access="hasRole('EXPLORER')">
		<form:hidden path="emergencyContacts"/>
		<form:hidden path="tripApplications"/>
		<form:hidden path="stories"/>
		<form:hidden path="survivalClasses"/>
	</security:authorize>
	
	<form:label path="name"><spring:message code="actor.name"/>:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br />
	<form:label path="surname"><spring:message code="actor.surname"/>:</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	<br />
	<form:label path="email"><spring:message code="actor.email"/>:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	<br />
	<form:label path="phoneNumber"><spring:message code="actor.phoneNumber"/>:</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<form:label path="address"><spring:message code="actor.address"/>:</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br /><br />
	<input type="submit" name="save" value="<spring:message code="actor.save"/>"/>
	<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/explorer/display.do');" />
	
</form:form>
</jstl:if>
<jstl:if test="${actorClassName == 'ranger' && ranger.id==0}">
	
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
		<form:input id="name" onkeyup="checkAllFields()" path="name"/>
		<form:errors cssClass="error" path="name"></form:errors>
		<br />
	
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input id="surname" onkeyup="checkAllFields()" path="surname"/>
		<form:errors cssClass="error" path="surname"></form:errors>
		<br />
	
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input id="email" onkeyup="checkAllFields()" path="email"/>
		<form:errors cssClass="error" path="email"></form:errors>
		<br />
	
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />
		</form:label>
		<form:input id="username" onkeyup="checkAllFields()" path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"></form:errors>
		<br />
	
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />
		</form:label>
		<form:password id="pass1" onkeyup="checkAllFields()" path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"></form:errors>
		<br />
	
		<%-- I don't want this to be sent --%>
		<label for="pass2">
			<spring:message code="actor.userAccount.repeatPassword" />
		</label>
		<input id="pass2" type="password" onkeyup="checkAllFields()" />
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
<jstl:if test="${actorClassName == 'ranger' && ranger.id!=0}">
	
	<security:authorize access="hasRole('RANGER')">
		<form:form action="${actionURI}" modelAttribute="ranger">
		
			<%-- Common Actor Attributes --%>
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			<form:hidden path="isSuspicious"/>
			<form:hidden path="socialIDs"/>
			<form:hidden path="sentMessages"/>
			<form:hidden path="receivedMessages"/>
			<form:hidden path="userAccount"/>
	
			<!-- Ranger -->
			<form:hidden path="curriculum"/>
			<form:hidden path="trips"/>
	
			<form:label path="name"><spring:message code="actor.name"/>:</form:label>
			<form:input path="name"/>
			<form:errors cssClass="error" path="name"/>
			<br />
			<form:label path="surname"><spring:message code="actor.surname"/>:</form:label>
			<form:input path="surname"/>
			<form:errors cssClass="error" path="surname"/>
			<br />
			<form:label path="email"><spring:message code="actor.email"/>:</form:label>
			<form:input path="email"/>
			<form:errors cssClass="error" path="email"/>
			<br />
			<form:label path="phoneNumber"><spring:message code="actor.phoneNumber"/>:</form:label>
			<form:input path="phoneNumber" id="phoneNumber"/>
			<form:errors cssClass="error" path="phoneNumber"/>
			<br />
			<form:label path="address"><spring:message code="actor.address"/>:</form:label>
			<form:input path="address"/>
			<form:errors cssClass="error" path="address"/>
			<br /><br />
	
			<input type="submit" name="save" value="<spring:message code="actor.save"/>"/>
			<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/ranger/display.do');" />
	
		</form:form>
	</security:authorize>
	

	<security:authorize access="hasRole('ADMIN')">
		<form:form action="${actionURI}" modelAttribute="ranger">
		
			<%-- Common Actor Attributes --%>
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			<form:hidden path="name"/>
			<form:hidden path="surname"/>
			<form:hidden path="email"/>
			<form:hidden path="phoneNumber"/>
			<form:hidden path="address"/>
			<form:hidden path="isSuspicious"/>
			<form:hidden path="socialIDs"/>
			<form:hidden path="sentMessages"/>
			<form:hidden path="receivedMessages"/>
			<form:hidden path="userAccount"/>
	
			<!-- Ranger -->
			<form:hidden path="curriculum"/>
			<form:hidden path="trips"/>
	
			<jstl:choose>
				<jstl:when test="${ranger.userAccount.isLocked == false}">
					<input type="submit" name="ban" value="<spring:message code="actor.ban"/>"/>
				</jstl:when>
				<jstl:otherwise>
					<input type="submit" name="unban" value="<spring:message code="actor.unban"/>"/>
				</jstl:otherwise>
			</jstl:choose>
			<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/ranger/display.do');" />
	
		</form:form>
	</security:authorize>
</jstl:if>

<jstl:if test="${actorClassName == 'auditor' && auditor.id!=0}">
	<form:form action="actor/auditor/edit.do" modelAttribute="auditor">
	<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount"/>
		
	<!-- Auditor -->
	<security:authorize access="hasRole('AUDITOR')">
		<form:hidden path="notes"/>
		<form:hidden path="audits"/>
	</security:authorize>
	
	<form:label path="name"><spring:message code="actor.name"/>:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br />
	<form:label path="surname"><spring:message code="actor.surname"/>:</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	<br />
	<form:label path="email"><spring:message code="actor.email"/>:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	<br />
	<form:label path="phoneNumber"><spring:message code="actor.phoneNumber"/>:</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<form:label path="address"><spring:message code="actor.address"/>:</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br /><br />
	
	<input type="submit" name="save" value="<spring:message code="actor.save"/>"/>
	<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/auditor/display.do');" />
	
</form:form>
</jstl:if>

<jstl:if test="${actorClassName == 'sponsor' && sponsor.id!=0}">
	<form:form action="actor/sponsor/edit.do" modelAttribute="sponsor">
	<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount"/>
		
	<!-- Sponsor -->
	<security:authorize access="hasRole('SPONSOR')">
		<form:hidden path="sponsorships"/>
	</security:authorize>
	
	<form:label path="name"><spring:message code="actor.name"/>:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br />
	<form:label path="surname"><spring:message code="actor.surname"/>:</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	<br />
	<form:label path="email"><spring:message code="actor.email"/>:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	<br />
	<form:label path="phoneNumber"><spring:message code="actor.phoneNumber"/>:</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<form:label path="address"><spring:message code="actor.address"/>:</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br /><br />
	
	<input type="submit" name="save" value="<spring:message code="actor.save"/>"/>
	<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/sponsor/display.do');" />
	
</form:form>
</jstl:if>

<jstl:if test="${actorClassName == 'admin' && admin.id!=0}">
	<form:form action="actor/admin/edit.do" modelAttribute="admin">
	<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount"/>
		
	<form:label path="name"><spring:message code="actor.name"/>:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br />
	<form:label path="surname"><spring:message code="actor.surname"/>:</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname"/>
	<br />
	<form:label path="email"><spring:message code="actor.email"/>:</form:label>
	<form:input path="email"/>
	<form:errors cssClass="error" path="email"/>
	<br />
	<form:label path="phoneNumber"><spring:message code="actor.phoneNumber"/>:</form:label>
	<form:input path="phoneNumber" id="phoneNumber"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<form:label path="address"><spring:message code="actor.address"/>:</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"/>
	<br /><br />
	
	<input type="submit" name="save" value="<spring:message code="actor.save"/>"/>
	<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/admin/display.do');" />
</form:form>
</jstl:if>

<jstl:if test="${actorClassName == 'manager' && manager.id==0}">
	
	<form:form action="${actionURI}" modelAttribute="manager">
		
		<%-- Common Actor Attributes --%>
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="isSuspicious"/>
		<form:hidden path="socialIDs"/>
		<form:hidden path="sentMessages"/>
		<form:hidden path="receivedMessages"/>
		<form:hidden path="userAccount.authorities"/>
	
		<%-- Manager Attributes --%>

		<form:hidden path="trips"/>
		<form:hidden path="survivalClasses"/>
		
		<%-- Manager Begins --%>
	
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input id="name" onkeyup="checkAllFields()" path="name"/>
		<form:errors cssClass="error" path="name"></form:errors>
		<br />
	
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input id="surname" onkeyup="checkAllFields()" path="surname"/>
		<form:errors cssClass="error" path="surname"></form:errors>
		<br />
	
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input id="email" onkeyup="checkAllFields()" path="email"/>
		<form:errors cssClass="error" path="email"></form:errors>
		<br />
	
		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />
		</form:label>
		<form:input id="username" onkeyup="checkAllFields()" path="userAccount.username"/>
		<form:errors cssClass="error" path="userAccount.username"></form:errors>
		<br />
	
		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />
		</form:label>
		<form:password id="pass1" onkeyup="checkAllFields()" path="userAccount.password"/>
		<form:errors cssClass="error" path="userAccount.password"></form:errors>
		<br />
	
		<%-- I don't want this to be sent --%>
		<label for="pass2">
			<spring:message code="actor.userAccount.repeatPassword" />
		</label>
		<input id="pass2" type="password" onkeyup="checkAllFields()" />
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

<jstl:if test="${actorClassName == 'manager' && manager.id!=0}">

	<security:authorize access="hasRole('MANAGER')">
		<form:form action="${actionURI}" modelAttribute="manager">
		
			<%-- Common Actor Attributes --%>
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			<form:hidden path="isSuspicious"/>
			<form:hidden path="socialIDs"/>
			<form:hidden path="sentMessages"/>
			<form:hidden path="receivedMessages"/>
			<form:hidden path="userAccount"/>
		
	
			<!-- Manager -->
			<form:hidden path="trips"/>
			<form:hidden path="survivalClasses"/>
	
			<form:label path="name"><spring:message code="actor.name"/>:</form:label>
			<form:input path="name"/>
			<form:errors cssClass="error" path="name"/>
			<br />
			<form:label path="surname"><spring:message code="actor.surname"/>:</form:label>
			<form:input path="surname"/>
			<form:errors cssClass="error" path="surname"/>
			<br />
			<form:label path="email"><spring:message code="actor.email"/>:</form:label>
			<form:input path="email"/>
			<form:errors cssClass="error" path="email"/>
			<br />
			<form:label path="phoneNumber"><spring:message code="actor.phoneNumber"/>:</form:label>
			<form:input path="phoneNumber" id="phoneNumber"/>
			<form:errors cssClass="error" path="phoneNumber"/>
			<br />
			<form:label path="address"><spring:message code="actor.address"/>:</form:label>
			<form:input path="address"/>
			<form:errors cssClass="error" path="address"/>
			<br /><br />
	
			<input type="submit" name="save" value="<spring:message code="actor.save"/>"/>
			<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/manager/display.do');" />
	
		</form:form>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
			<form:form action="${actionURI}" modelAttribute="manager">
		
			<%-- Common Actor Attributes --%>
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			<form:hidden path="name"/>
			<form:hidden path="surname"/>
			<form:hidden path="email"/>
			<form:hidden path="phoneNumber"/>
			<form:hidden path="address"/>
			<form:hidden path="isSuspicious"/>
			<form:hidden path="socialIDs"/>
			<form:hidden path="sentMessages"/>
			<form:hidden path="receivedMessages"/>
			<form:hidden path="userAccount"/>
		
	
			<!-- Manager -->
			<form:hidden path="trips"/>
			<form:hidden path="survivalClasses"/>
	
			<jstl:choose>
				<jstl:when test="${manager.userAccount.isLocked == false}">
					<input type="submit" name="ban" value="<spring:message code="actor.ban"/>"/>
				</jstl:when>
				<jstl:otherwise>
					<input type="submit" name="unban" value="<spring:message code="actor.unban"/>"/>
				</jstl:otherwise>
			</jstl:choose>
			<input type="button" name="cancel" value="<spring:message code="actor.cancel"/>" onclick="javascript: relativeRedir('actor/manager/display.do');" />
	
		</form:form>
	</security:authorize>
	
</jstl:if>
