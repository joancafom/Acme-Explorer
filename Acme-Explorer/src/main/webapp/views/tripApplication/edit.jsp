<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Edit an application -->


<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actor" value="explorer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actor" value="manager"></jstl:set>
</security:authorize>


<form:form action="tripApplication/${actor}/edit.do" modelAttribute="tripApplication">
	
	<!-- Create an application -->
	
	<jstl:if test="${tripApplication.id==0}">
	
		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="explorer"/>
		<form:hidden path="moment"/>
		<form:hidden path="status"/>
		<form:hidden path="rejectionReason"/>
		<form:hidden path="creditCard"/>
		<form:hidden path="trip"/>
	
		
		<form:label path="comments">
			<spring:message code="tripApplication.comments"/>:
		</form:label>
		
		<spring:message code="tripApplication.commentsPlaceHolder" var="commentsPlaceHolder"/>
		<form:textarea path="comments" placeholder="${commentsPlaceHolder}"/>
		
		<form:errors cssClass="error" path="comments"/>
		
		<br><br>
	
		<input type="submit" name="save" value="<spring:message code="tripApplication.apply"/>">
		<input type="button" name="cancel" value="<spring:message	code="tripApplication.cancel" />" onclick="javascript: relativeRedir('tripApplication/${actor}/list.do');" />
	</jstl:if>
	
	
	
	
	
	<!-- Edit an application -->
		
	<jstl:if test="${tripApplication.id!=0}">
		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="explorer"/>
		<form:hidden path="moment"/>
		<form:hidden path="trip"/>
		
		<security:authorize access="hasRole('MANAGER')">
			<form:hidden path="creditCard"/>
			
			<form:label path="status">
				<spring:message code="tripApplication.status"/>
			</form:label>
			<form:select path="status" id="selectStatus">
				<spring:message code="tripApplication.status.PENDING" var="pending"/>
				<spring:message code="tripApplication.status.REJECTED" var="rejected"/>
				<spring:message code="tripApplication.status.DUE" var="due"/>
				<form:option value="PENDING" label="${pending}"/>
				<form:option value="REJECTED" label="${rejected}"/>
				<form:option value="DUE" label="${due}"/>
			</form:select>
		
			<jstl:if test="${selectStatus=='REJECTED'}">
				<form:label path="rejectionReason">
					<spring:message code="tripApplication.rejectionReason"/>
				</form:label>
				<form:textarea path="rejectionReason"/>
			</jstl:if>
			
			<input type="submit" name="save" value="<spring:message code="tripApplication.save"/>">
			<input type="button" name="cancel" value="<spring:message	code="tripApplication.cancel" />" onclick="javascript: relativeRedir('tripApplication/manager/list.do');" />
		</security:authorize>
		
		
		
		<security:authorize access="hasRole('EXPLORER')">
			<form:hidden path="status"/>
			<form:hidden path="rejectionReason"/>
			<form:hidden path="trip"/>
			
			<jstl:if test="${tripApplication.status == 'DUE'}">
			
				<form:label path="creditCard.holderName">
					<spring:message code="tripApplication.creditCard.holderName"/>
				</form:label>
				<form:input path="creditCard.holderName"/>
				<form:errors cssClass="error" path="creditCard.holderName"/>
			
				<br/>
	
				<form:label path="creditCard.brandName">
					<spring:message code="tripApplication.creditCard.brandName"/>
				</form:label>
				<form:input path="creditCard.brandName"/>
				<form:errors cssClass="error" path="creditCard.brandName"/>
			
				<br/>
	
				<form:label path="creditCard.number">
					<spring:message code="tripApplication.creditCard.number"/>
				</form:label>
				<form:input path="creditCard.number"/>
				<form:errors cssClass="error" path="creditCard.number"/>
			
				<br/>
	
				<form:label path="creditCard.CVV">
					<spring:message code="tripApplication.creditCard.CVV"/>
				</form:label>
				<form:input path="creditCard.CVV"/>
				<form:errors cssClass="error" path="creditCard.CVV"/>
			
				<br/>
	
				<form:label path="creditCard.month">
					<spring:message code="tripApplication.creditCard.month"/>
				</form:label>
				<form:input path="creditCard.month"/>
				<form:errors cssClass="error" path="creditCard.month"/>
			
				<br/>
	
				<form:label path="creditCard.year">
					<spring:message code="tripApplication.creditCard.year"/>
				</form:label>
				<form:input path="creditCard.year"/>
				<form:errors cssClass="error" path="creditCard.year"/>
			
				<br/>
			
				<input type="submit" name="save" value="<spring:message code="tripApplication.save"/>">
				<input type="button" name="cancel" value="<spring:message	code="tripApplication.cancel" />" onclick="javascript: relativeRedir('tripApplication/explorer/list.do');" />
			
			</jstl:if>
			
			
			<jstl:if test="${tripApplication.status == 'ACCEPTED'}">
				<input type="submit" name="cancelTripApplication" value="Cancel this application">
			</jstl:if>
				
		</security:authorize>
		
	</jstl:if>
</form:form>

