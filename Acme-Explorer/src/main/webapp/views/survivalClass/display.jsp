<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="survivalClass.enroll.deniedBecause" var="cannotEnrollMessage"></spring:message>


<script type="text/javascript">
function cannotEnroll(){
	alert("${cannotEnrollMessage}");
}

</script>

<jsp:useBean id="now" class="java.util.Date"/>

<h3><jstl:out value="${survivalClass.title}"/></h3>

<p><jstl:out value="${survivalClass.description}"/></p>

<hr>

<spring:message code="date.format2" var="dateFormat"></spring:message>
<p><strong><spring:message code="survivalClass.moment"/>:</strong> <fmt:formatDate value="${survivalClass.moment}" pattern="${dateFormat}" type="both"/></p>

<p><strong>
	<spring:message code="survivalClass.location"/>:</strong> 
	<jstl:out value="${survivalClass.location.name}"/>. 
	(<spring:message code="location.latitude"/>: <jstl:out value="${survivalClass.location.coordinateX}"/> |
	<spring:message code="location.longitude"/>: <jstl:out value="${survivalClass.location.coordinateY}"/>)
</p>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:set var="url" value="explorer/"></jstl:set>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="url" value="manager/"></jstl:set>
</security:authorize>

<p><strong><spring:message code="survivalClass.trip"/>:</strong> <a href="trip/${url}display.do?tripId=${survivalClass.trip.id}"><jstl:out value="${survivalClass.trip.ticker}"/></a></p>

<p><strong><spring:message code="survivalClass.manager"/>:</strong> <jstl:out value="${survivalClass.manager.surname}"/>, <jstl:out value="${survivalClass.manager.name}"/></p>

<security:authorize access="hasRole('MANAGER')">
	<p><strong><spring:message code="survivalClass.explorers"/>:</strong> <a href="explorer/manager/list.do?survivalClassId=${survivalClass.id}"><spring:message code="explorer.list"/></a></p>
</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
	<jstl:if test="${alreadyEnrolled==true}">
		<strong style="color:green"><spring:message code="survivalClass.enroll.alreadyEnrolled"/></strong>
	</jstl:if>
	<jstl:if test="${alreadyEnrolled==false}">
		<jstl:if test="${isEnrollable==true && survivalClass.moment > now}">
			<a href="survivalClass/explorer/enroll.do?survivalClassId=${survivalClass.id}"><spring:message code="survivalClass.enroll"/></a>
		</jstl:if>
		<jstl:if test="${isEnrollable==false}">
			<p style="color:red"><spring:message code="survivalClass.enroll.denied"/><a href="javascript:cannotEnroll();"><spring:message code="survivalClass.enroll.why"/></a></p>
		</jstl:if>
	</jstl:if>
</security:authorize>