<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="legalText/admin/edit.do" modelAttribute="legalText">
	<!-- Hidden inputs -->
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="trips"/>
	<form:hidden path="registrationMoment"/>
	
	<form:label path="title"><spring:message code="legalText.title"/></form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br/>
	<form:label path="body"><spring:message code="legalText.body"/></form:label>
	<form:textarea path="body"/>
	<form:errors cssClass="error" path="body"/>
	<br/>
	<form:label path="laws"><spring:message code="legalText.laws"/></form:label>
	<form:textarea path="laws"/>
	<form:errors cssClass="error" path="laws"/>
	<br/>
	<form:label path="isFinal"><spring:message code="legalText.mode"/></form:label>
	<form:radiobutton path="isFinal" value="false"/><spring:message code="legalText.draft"/>
	<form:radiobutton path="isFinal" value="true"/><spring:message code="legalText.final"/>
	<form:errors cssClass="error" path="isFinal"/>
	<br><br/>
	<input type="submit" name="save" value="<spring:message code="legalText.save"/>"/>
	<jstl:if test="${legalText.id !=0 && legalText.isFinal == false}">
		<input type="submit" name="delete" value="<spring:message code="legalText.delete"/>"/>
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message	code="legalText.cancel" />" onclick="javascript: relativeRedir('legalText/admin/list.do');" />
</form:form>