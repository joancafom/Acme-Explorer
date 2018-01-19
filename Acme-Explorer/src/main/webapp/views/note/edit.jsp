<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('AUDITOR')">
		<jstl:set var="url" value="auditor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
		<jstl:set var="url" value="manager"></jstl:set>
</security:authorize>


<form:form action="note/${url}/edit.do" modelAttribute="note">
	<!-- Hidden inputs -->
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="auditor"/>
	<form:hidden path="writtenMoment"/>
	<form:hidden path="replyMoment"/>
	
	<security:authorize access="hasRole('AUDITOR')">
		<form:hidden path="reply"/>
		
		<form:label path="trip"><spring:message code="note.trip"/></form:label>
		<form:select path="trip">
			<form:option value="0" label="---"/>
			<jstl:forEach items="${trips}" var="t">
				<form:option value="${t.id}" label="${t.ticker}, ${t.title}"/>
			</jstl:forEach>
		</form:select>
		<form:errors cssClass="error" path="trip"/>
		<br><br>
		<form:label path="remark"><spring:message code="note.remark"/></form:label>
		<form:textarea path="remark"/>
		<form:errors cssClass="error" path="remark"/>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<form:hidden path="trip"/>
		<form:hidden path="remark"/>
		
		<form:label path="reply"><spring:message code="note.reply"/></form:label>
		<form:textarea path="reply" required="required" />
		<form:errors cssClass="error" path="reply"/>
	</security:authorize>
	
	<br><br>
	<input type="submit" name="save" value="<spring:message code="note.save" />" />
	<security:authorize access="hasRole('MANAGER')">
		<input type="button" name="cancel" value="<spring:message code="note.cancel"/>" onclick="javascript: relativeRedir('note/${url}/list.do?tripId=${note.trip.id}');" />
	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
		<input type="button" name="cancel" value="<spring:message code="note.cancel"/>" onclick="javascript: relativeRedir('note/${url}/list.do');" />
	</security:authorize>
	
</form:form>