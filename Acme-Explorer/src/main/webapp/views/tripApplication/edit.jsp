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


<form:form action="/tripApplication/${actor}/edit.do" modelAttribute="tripApplication">
	
	<jstl:if test="${tripApplication.id==0}">
		<!-- Create an application -->
	
		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="explorer"/>
		<form:hidden path="moment"/>
		<form:hidden path="status"/>
		<form:hidden path="rejectionReason"/>
		<form:hidden path="creditCard"/>
	
		
		<form:label path="comments">
			<spring:message code="tripApplication.comments"/>:
		</form:label>
		<form:textarea path="comments" placeholder="<spring:message code="tripApplication.commentsPlaceHolder"/>"/>
		<form:errors cssClass="error" path="comments"/>
		
		<br><br>
	
		<input type="submit" name="save" value="<spring:message code="tripApplication.apply"/>">
		<input type="button" name="cancel" value="<spring:message	code="tripApplication.cancel" />" onclick="javascript: relativeRedir('tripApplication/explorer/list.do');" />
	</jstl:if>
		
	<jstl:if test="${tripApplication.id!=0}">
		<!-- Edit an application -->
		
		<!-- Hidden inputs -->
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="explorer"/>
		<form:hidden path="moment"/>
		
		
		<security:authorize access="hasRole('MANAGER')">
			<form:hidden path="creditCard"/>
			
			<form:label path="status">
				<spring:message code="tripApplication.status"/>
			</form:label>
			<form:select path="status" id="selectStatus">
				<form:option value="PENDING" label="---"/>
				<jstl:forEach var="status" items="${allStatus}">
      				<form:option label="<spring:message code="tripApplication.status.${status}"/>"
      					value="${status}">
      				</form:option>
      			</jstl:forEach>	
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
			
			<!-- CREDIT CARD -->
			
			<form:label path="creditCard.holderName">
				<spring:message code="tripApplication.creditCard.holderName"/>
			</form:label>
			<form:input path="creditCard.holderName"/>
			<form:errors cssClass="errors" path="creditCard.holderName"/>
	
			<form:label path="creditCard.brandName">
				<spring:message code="tripApplication.creditCard.brandName"/>
			</form:label>
			<form:input path="creditCard.brandName"/>
			<form:errors cssClass="errors" path="creditCard.brandName"/>
	
			<form:label path="creditCard.number">
				<spring:message code="tripApplication.creditCard.number"/>
			</form:label>
			<form:input path="creditCard.number"/>
			<form:errors cssClass="errors" path="creditCard.number"/>
	
			<form:label path="creditCard.CVV">
				<spring:message code="tripApplication.creditCard.CVV"/>
			</form:label>
			<form:input path="creditCard.CVV"/>
			<form:errors cssClass="errors" path="creditCard.CVV"/>
	
			<form:label path="creditCard.month">
				<spring:message code="tripApplication.creditCard.month"/>
			</form:label>
			<form:input path="creditCard.month"/>
			<form:errors cssClass="errors" path="creditCard.month"/>
	
			<form:label path="creditCard.year">
				<spring:message code="tripApplication.creditCard.year"/>
			</form:label>
			<form:input path="creditCard.year"/>
			<form:errors cssClass="errors" path="creditCard.year"/>
			
			<input type="submit" name="save" value="<spring:message code="tripApplication.save"/>">
			<input type="button" name="cancel" value="<spring:message	code="tripApplication.cancel" />" onclick="javascript: relativeRedir('tripApplication/manager/list.do');" />
				
		</security:authorize>
		
	</jstl:if>
</form:form>

