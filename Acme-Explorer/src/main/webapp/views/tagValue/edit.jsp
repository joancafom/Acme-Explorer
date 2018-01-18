<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="tagValue/manager/edit.do" modelAttribute="tagValue">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="trip"/>
	
	<jstl:choose>
		<jstl:when test="${tagValue.id == 0}">
			<form:label path="tag">
				<spring:message code="tagValue.tag"/>
			</form:label>
			<form:select path="tag">
				<form:option value="0" label="---"/>
				<form:options items="${tags}"
					itemValue="id"
					itemLabel="name"/>
			</form:select>
			<form:errors cssClass="error" path="tag"/>
			<br />
		</jstl:when>
		<jstl:otherwise>
			<form:hidden path="tag"/>
		</jstl:otherwise>
	</jstl:choose>
	
	<form:label path="value">
		<spring:message code="tagValue.value"/>:
	</form:label>
	<form:textarea path="value"/>
	<form:errors cssClass="error" path="value"/>
	
	<input type="submit" name="save" value="<spring:message code="tagValue.save"/>">
		<jstl:if test="${tagValue.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="tagValue.delete"/>">
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message	code="tagValue.cancel" />" onclick="javascript: relativeRedir('trip/manager/display.do?tripId=${tagValue.trip.id}');" />
</form:form>
