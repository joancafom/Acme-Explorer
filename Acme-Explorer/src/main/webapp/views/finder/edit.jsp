<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="finder/explorer/edit.do" modelAttribute="finder">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:label path="keyword">
		<spring:message code="finder.keyword"/>
	</form:label>
	<form:input path="keyword"/>
	<form:errors cssClass="error" path="keyword"/>
	
	<br/>
	
	<form:label path="minRange">
		<spring:message code="finder.minRange"/>
	</form:label>
	<form:input path="minRange"/>
	<form:errors cssClass="error" path="minRange"/>
	
	<br/>
	
	<form:label path="maxRange">
		<spring:message code="finder.maxRange"/>
	</form:label>
	<form:input path="maxRange"/>
	<form:errors cssClass="error" path="maxRange"/>
	
	<br/>
	
	<form:label path="minDate">
		<spring:message code="finder.minDate"/>
	</form:label>
	<form:input path="minDate"/>
	<form:errors cssClass="error" path="minDate"/>
	
	<br/>
	
	<form:label path="maxDate">
		<spring:message code="finder.maxDate"/>
	</form:label>
	<form:input path="maxDate"/>
	<form:errors cssClass="error" path="maxDate"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="finder.save"/>">
	<input type="button" name="cancel" value="<spring:message	code="finder.cancel" />" onclick="javascript: relativeRedir('finder/explorer/display.do?finderId=${finder.id}');" />
</form:form>
