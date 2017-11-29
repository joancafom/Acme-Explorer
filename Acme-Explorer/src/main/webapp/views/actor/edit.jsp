<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="ranger/create.do" modelAttribute="actor">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isBanned"/>
	<form:hidden path="isSuspicious"/>
	<form:hidden path="socialIDs"/>
	<form:hidden path="folders"/>
	<form:hidden path="sentMessages"/>
	<form:hidden path="receivedMessages"/>
	<form:hidden path="userAccount"/>
	
	<jstl:if test="${actor.notes != null}">
		<form:hidden path="notes"/>
	</jstl:if>
	
	<jstl:if test="${actor.auditions != null}">
		<form:hidden path="notes"/>
	</jstl:if>
	
	<jstl:if test="${actor.finder != null}">
		<form:hidden path="finder"/>
	</jstl:if>
	
	<jstl:if test="${actor.stories != null}">
		<form:hidden path="stories"/>
	</jstl:if>
	
	<jstl:if test="${actor.tripApplications != null}">
		<form:hidden path="tripApplications"/>
	</jstl:if>
	
	<jstl:if test="${actor.survivalClasses != null}">
		<form:hidden path="survivalClasses"/>
	</jstl:if>
	
	<jstl:if test="${actor.sponsorships != null}">
		<form:hidden path="sponsorships"/>
	</jstl:if>
	
	<jstl:if test="${actor.trips != null}">
		<form:hidden path="trips"/>
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
		onclick="javascript: window.location.replace('<jstl:out value="${requestURI}" />')" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />

</form:form>