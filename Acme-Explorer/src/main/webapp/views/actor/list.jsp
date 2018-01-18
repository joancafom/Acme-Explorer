<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Although this view is kind of generic, it could be used by any type of actor
but, since in the requirements it only says about listing Explorers, it's adapted to them --%>
<jstl:if test="${action==''}">
<display:table name="actors" id="actor" requestURI="explorer/manager/list.do" pagesize="5" class="displaytag">
	
	<display:column titleKey="actor.list.name" sortable="true">
		<a href="explorer/manager/display.do?explorerId=${actor.id}"><jstl:out value="${actor.name}" /></a>
	</display:column>
	
	<display:column property="surname" titleKey="actor.list.surname" sortable="true" />
	
	<display:column property="email" titleKey="actor.list.email" />
	
	<display:column property="phoneNumber" titleKey="actor.phoneNumber" />

	<display:column property="address" titleKey="actor.address" />
	
</display:table>
</jstl:if>

<jstl:if test="${action=='listSuspicious'}">
	<h1><spring:message code="managers"/>:</h1>
	<display:table name="managers" id="manager" class="displaytag">
	
		<display:column property="name" title="Name" sortable="true"/>
	
		<display:column property="surname" title="Surname" sortable="true" />
	
		<display:column property="email" title="Email" />
	
		<display:column property="phoneNumber" title="Phone Number" />

		<display:column property="address" title="Address" />
		
		<display:column property="userAccount.isLocked" title="Is banned" />
		
		<display:column>
		<jstl:choose>
			<jstl:when test="${manager.userAccount.isLocked == false}">
				<a href="manager/admin/edit.do?managerId=${manager.id}"><spring:message code="actor.ban"/></a>
			</jstl:when>
			<jstl:otherwise>
				<a href="manager/admin/edit.do?managerId=${manager.id}"><spring:message code="actor.unban"/></a>
			</jstl:otherwise>
		</jstl:choose>
		</display:column>
		
	</display:table>
	
	<h1><spring:message code="rangers"/>:</h1>
	<display:table name="rangers" id="ranger" class="displaytag">
	
		<display:column property="name" title="Name" sortable="true"/>
	
		<display:column property="surname" title="Surname" sortable="true" />
	
		<display:column property="email" title="Email" />
	
		<display:column property="phoneNumber" title="Phone Number" />

		<display:column property="address" title="Address" />
		
		<display:column property="userAccount.isLocked" title="Is banned" />
		
		<display:column>
		<jstl:choose>
			<jstl:when test="${ranger.userAccount.isLocked == false}">
				<a href="ranger/admin/edit.do?rangerId=${ranger.id}"><spring:message code="actor.ban"/></a>
			</jstl:when>
			<jstl:otherwise>
				<a href="ranger/admin/edit.do?rangerId=${ranger.id}"><spring:message code="actor.unban"/></a>
			</jstl:otherwise>
		</jstl:choose>
		</display:column>
		
	</display:table>
</jstl:if>