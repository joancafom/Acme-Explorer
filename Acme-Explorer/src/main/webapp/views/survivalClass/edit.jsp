<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${actor=='manager'}">
<form:form action="survivalClass/manager/edit.do" modelAttribute="survivalClass">
	<!-- Hidden Inputs -->
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="manager"/>
	<form:hidden path="explorers"/>
	
	<form:label path="title"><spring:message code="survivalClass.title"/></form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br><br>
	<form:label path="description"><spring:message code="survivalClass.description"/></form:label>
	<form:textarea path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br><br>
	<form:label path="moment"><spring:message code="survivalClass.moment"/></form:label>
	<form:input path="moment" placeholder="dd/MM/YYYY HH:mm" />
	<form:errors cssClass="error" path="moment"/>
	<br><br>
	<form:label path="trip"><spring:message code="survivalClass.trip"/></form:label>
	<form:select path="trip">
		<form:option value="0" label="---"></form:option>
		<jstl:forEach items="${trips}" var="t">
			<form:option value="${t.id}" label="${t.ticker}, ${t.title}"/>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="trip"/>
	<br><br>
	<form:label path="location.name"><spring:message code="survivalClass.location"/></form:label>
	<form:input path="location.name"/>
	<form:errors cssClass="error" path="location.name"/>
	<br><br>
	<form:label path="location.coordinateX"><spring:message code="location.latitude"/></form:label>
	<form:input path="location.coordinateX"/>
	<form:errors cssClass="error" path="location.coordinateX"/>
	<br><br>
	<form:label path="location.coordinateY"><spring:message code="location.longitude"/></form:label>
	<form:input path="location.coordinateY"/>
	<form:errors cssClass="error" path="location.coordinateY"/>
	<br><br>
	<input type="submit" name="save" value="<spring:message code="survivalClass.save"/>"/>
	<jstl:if test="${survivalClass.id!=0}">
		<input type="submit" name="delete" value="<spring:message code="survivalClass.delete"/>"/>
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message	code="survivalClass.cancel" />" onclick="javascript: relativeRedir('survivalClass/manager/list.do');" />
</form:form>
</jstl:if>

<jstl:if test="${actor=='explorer'}">
	<form:form action="survivalClass/explorer/edit.do" modelAttribute="survivalClass">
	<!-- Hidden Inputs -->
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="manager"/>
	<form:hidden path="explorers"/>
	<form:hidden path="title"/>
	<form:hidden path="description"/>
	<form:hidden path="moment"/>
	<form:hidden path="location"/>
	<form:hidden path="trip"/>
	
	<p><spring:message code="survivalClass.enroll.question"/></p>
	<br>
	<input type="submit" name="enroll" value="<spring:message code="survivalClass.yes"/>"/>
	<input type="button" name="cancel" value="<spring:message	code="survivalClass.no" />" onclick="javascript: relativeRedir('trip/explorer/display.do?tripId=${survivalClass.trip.id}');" />
			
	
	</form:form>
</jstl:if>