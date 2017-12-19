<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="tags" id="tag" requestURI="/tag/admin/list.do" pagesize="5" class="displaytag">
	<display:column property="name" titleKey="tag.name" sortable="true"/>
	<display:column titleKey="tag.tagValues">
		<jstl:forEach items="${tag.tagValues}" var="tagValue">
			<jstl:out value="${tagValue.value}"></jstl:out><br/>
		</jstl:forEach>
	</display:column>
	<display:column>
		<a href="tag/admin/edit.do?tagId=${tag.id}"><spring:message code="tag.edit"/></a>
	</display:column>
</display:table>

<a href="tag/admin/create.do"><spring:message code="tag.create"/></a>