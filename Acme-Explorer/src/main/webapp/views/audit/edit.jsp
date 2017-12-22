<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${audit.isFinal==false}">
		<form:form action="audit/auditor/edit.do" modelAttribute="audit">
		
			<!-- Hidden inputs -->
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			<form:hidden path="auditor"/>
			<form:hidden path="trip"/>
			
			<jstl:choose>
				<jstl:when test="${audit.id != 0}">
					<form:label path="moment">
						<spring:message code="audit.moment"/>
					</form:label>
					<form:input path="moment"/>
					<form:errors cssClass="error" path="moment"/>
					<br />
				</jstl:when>
				<jstl:otherwise>
					<form:hidden path="moment"/>
				</jstl:otherwise>
			</jstl:choose>
			
			<form:label path="title">
				<spring:message code="audit.title"/>
			</form:label>
			<form:input path="title"/>
			<form:errors cssClass="error" path="title"/>
			<br />
			
			<form:label path="description">
				<spring:message code="audit.description"/>
			</form:label>
			<form:textarea path="description"/>
			<form:errors cssClass="error" path="description"/>
			<br />
			
			<form:label path="attachments">
				<spring:message code="audit.attachments"/>
			</form:label>
			<form:textarea path="attachments"/>
			<form:errors cssClass="error" path="attachments"/>
			<br />
			
			<form:label path="isFinal">
				<spring:message code="audit.mode"/>
			</form:label>
			<form:radiobutton path="isFinal" value="false"/><spring:message code="audit.draft"/>
			<form:radiobutton path="isFinal" value="true"/><spring:message code="audit.final"/>
			<form:errors cssClass="error" path="isFinal"/>
			
			<input type="submit" name="save" value="<spring:message code="audit.save"/>"/>
			<jstl:if test="${audit.id != 0}">
				<input type="submit" name="delete" value="<spring:message code="audit.delete"/>">
			</jstl:if>
		</form:form>
		<input type="button" name="cancel" value="<spring:message	code="audit.cancel" />" onclick="javascript: relativeRedir('audit/auditor/list.do');" />
</jstl:if>