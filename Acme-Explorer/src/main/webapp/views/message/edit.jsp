<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="actor" value="explorer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="actor" value="manager"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="actor" value="admin"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('AUDITOR')">
	<jstl:set var="actor" value="auditor"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('RANGER')">
	<jstl:set var="actor" value="ranger"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
	<jstl:set var="actor" value="sponsor"></jstl:set>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${isNotification}">
		<strong><spring:message code="message.notification.info"/></strong> 
		<br />
		<br />
	</jstl:if>
</security:authorize>

<form:form action="message/${actor}/edit.do" modelAttribute="message">
		<jstl:if test="${message.id==0}">
			<form:hidden path="version"/>
			<form:hidden path="id"/>
			<form:hidden path="sender"/>
			<form:hidden path="sentMoment"/>
			<form:hidden path="folder"/>
	
			<form:label path="subject"><spring:message code="message.subject"/></form:label>
			<form:input path="subject"/>
			<form:errors cssClass="error" path="subject"/>	
			<br><br>
			<form:label path="body"><spring:message code="message.body"/></form:label>
			<form:textarea path="body"/>
			<form:errors cssClass="error" path="body"/>
			<br><br>
			
			<security:authorize access="hasRole('ADMIN')">
				<jstl:choose>
					<jstl:when test="${isNotification}">
						<form:hidden path="recipient" value="${message.sender.id}"/>
					</jstl:when>
					<jstl:otherwise>
						<form:select path="recipient">
							<form:option value="0" label="----"/>
							<form:options items="${actors}" itemValue="id" itemLabel="name"/>
						</form:select>
					</jstl:otherwise>
				</jstl:choose>
			</security:authorize>
			<security:authorize access="!hasRole('ADMIN')">
				<form:label path="recipient"><spring:message code="message.recipient"/></form:label>
				<form:select path="recipient">
					<form:option value="0" label="----"/>
					<jstl:forEach items="${actors}" var="actor">
						<form:option value="${actor.id}" label="${actor.userAccount.username} - ${actor.name} ${actor.surname}"/>
					</jstl:forEach>
				</form:select>
				<form:errors cssClass="error" path="recipient"/>
			</security:authorize>
			<br><br>
			<form:label path="priority"><spring:message code="message.priority"/></form:label>
			<form:select path="priority">
				<jstl:forEach items="${priorities}" var="priority">
					<jstl:if test="${priority=='HIGH'}">
						<jstl:set var="priorityLevel">
							<spring:message code="message.priority.HIGH"/>
						</jstl:set>
					</jstl:if>
					<jstl:if test="${priority=='LOW'}">
						<jstl:set var="priorityLevel">
							<spring:message code="message.priority.LOW"/>
						</jstl:set>
					</jstl:if>
					<jstl:if test="${priority=='NEUTRAL'}">
						<jstl:set var="priorityLevel">
							<spring:message code="message.priority.NEUTRAL"/>
						</jstl:set>
					</jstl:if>
					<form:option value="${priority}" label="${priorityLevel}"/>
				</jstl:forEach>
			</form:select>
			<form:errors cssClass="error" path="priority"/>
			<br><br>
			<security:authorize access="hasRole('ADMIN')">
				<jstl:choose>
					<jstl:when test="${isNotification}">
						<input type="submit" name="saveNotification" value="<spring:message code="message.save"/>"/>
					</jstl:when>
					<jstl:otherwise>
						<input type="submit" name="save" value="<spring:message code="message.save"/>"/>
					</jstl:otherwise>
				</jstl:choose>
			</security:authorize>
			<security:authorize access="!hasRole('ADMIN')">
				<input type="submit" name="save" value="<spring:message code="message.save"/>"/>
			</security:authorize>	
			<input type="button" name="cancel" value="<spring:message code="message.cancel"/>" onclick="javascript: relativeRedir('folder/${actor}/list.do');" />
	
		</jstl:if>
		
		<jstl:if test="${message.id!=0}">
			
			<form:hidden path="version"/>
			<form:hidden path="id"/>
			<form:hidden path="sender"/>
			<form:hidden path="recipient"/>
			<form:hidden path="sentMoment"/>
			<form:hidden path="body"/>
			<form:hidden path="priority"/>
			<form:hidden path="subject"/>	
			
			<form:label path="folder"><spring:message code="message.folder"/>: </form:label>
			<form:select path="folder">
				<jstl:forEach items="${folders}" var="folder">
					<jstl:choose>
						<jstl:when test="${folder.parentFolder == null}">
							<form:option value="${folder.id}" label="${folder.name}"/>
						</jstl:when>
						<jstl:otherwise>
							<form:option value="${folder.id}" label="${folder.parentFolder.name} -> ${folder.name}"/>
						</jstl:otherwise>
					</jstl:choose>
					
				</jstl:forEach>
			</form:select>
			<form:errors cssClass="error" path="folder"/>
			<br><br>
			<input type="submit" name="save" value="<spring:message code="message.save"/>"/>	
			<input type="submit" name="delete" value="<spring:message code="message.delete"/>"/>
			<input type="button" name="cancel" value="<spring:message code="message.cancel"/>" onclick="javascript: relativeRedir('folder/${actor}/list.do');" />
		</jstl:if>
</form:form>