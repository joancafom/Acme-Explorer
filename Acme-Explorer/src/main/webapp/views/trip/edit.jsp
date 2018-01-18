<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Create a Trip -->

<form:form action="trip/manager/edit.do" modelAttribute="trip">

		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="ticker"/>
		<form:hidden path="price"/>
		<form:hidden path="cancelationReason"/>
		<form:hidden path="sponsorships"/>
		<form:hidden path="stories"/>
		<form:hidden path="notes"/>
		<form:hidden path="audits"/>
		<form:hidden path="tripApplications"/>
		<form:hidden path="tagValues"/>
		<form:hidden path="stages"/>
		<form:hidden path="survivalClasses"/>
		<form:hidden path="manager"/>
	
		<form:label path="title">
			<spring:message code="trip.title"/>
		</form:label>
		<form:input path="title"/>
		<form:errors cssClass="error" path="title"/>
		<br />
	
		<form:label path="description">
			<spring:message code="trip.description"/>	
		</form:label>
		<form:textarea path="description"/>
		<form:errors cssClass="error" path="description"/>
		<br />
	
		<form:label path="publicationDate">
			<spring:message code="trip.publicationDate"/>
		</form:label>
		<form:input path="publicationDate" placeholder="dd/MM/YYYY HH:mm" />
		<form:errors cssClass="error" path="publicationDate"/>
		<br />
	
		<form:label path="startingDate">
			<spring:message code="trip.startingDate"/>
		</form:label>
		<form:input path="startingDate" placeholder="dd/MM/YYYY HH:mm" />
		<form:errors cssClass="error" path="startingDate"/>
		<br />
	
		<form:label path="endingDate">
			<spring:message code="trip.endingDate"/>
		</form:label>
		<form:input path="endingDate" placeholder="dd/MM/YYYY HH:mm" />
		<form:errors cssClass="error" path="endingDate"/>
		<br />
	
		<form:label path="requirements">
			<spring:message code="trip.requirements"/>
		</form:label>
		<form:textarea path="requirements"/>
		<form:errors cssClass="error" path="requirements"/>
		<br />
	
		<form:label path="ranger">
			<spring:message code="trip.ranger"/>
		</form:label>
		<form:select path="ranger">
			<form:option value="0" label="---"/>
			<form:options items="${rangers}"
				itemValue="id"
				itemLabel="name"/>
		</form:select>
		<form:errors cssClass="error" path="ranger"/>
		<br />
	
		<form:label path="legalText">
			<spring:message code="trip.legalText"/>
		</form:label>
		<form:select path="legalText">
			<form:option value="0" label="---"/>
			<form:options items="${legalTexts}"
				itemValue="id"
				itemLabel="title"/>
		</form:select>
		<form:errors cssClass="error" path="legalText"/>
		<br />
	
		<form:label path="category">
			<spring:message code="trip.category"/>
		</form:label>
		<form:select path="category">
			<form:option value="0" label="---"/>
			<jstl:forEach items="${categories}" var="c">
				<jstl:choose>
					<jstl:when test="${c.name != 'CATEGORY'}">
						<form:option value="${c.id}" label="${c.parentCategory.name} - ${c.name}"/>
					</jstl:when>
					<jstl:otherwise>
						<form:option value="${c.id}" label="${c.name}"/>
					</jstl:otherwise>
				</jstl:choose>
			</jstl:forEach>
		</form:select>
		<form:errors cssClass="error" path="category"/>
		<br />
	
		<input type="submit" name="save" value="<spring:message code="trip.save"/>">
		<jstl:if test="${trip.id != 0}">
			<input type="submit" name="delete" value="<spring:message code="trip.delete"/>" />
		</jstl:if>
		<input type="button" name="cancel" value="<spring:message code="trip.cancel"/>" onclick="javascript: relativeRedir('trip/manager/list.do');" />
		<spring:message code="date.format2" var="dateFormat2"></spring:message>
	
</form:form>