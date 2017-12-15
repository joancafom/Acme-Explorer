<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="folder/edit.do" modelAttribute="folder">
	<form:label path="name">Folder name:</form:label>
	<form:input path="name"/>
	<form:errors cssClass="errors" path="name"/>
	
	<input type="submit" name="save" value="Save"/>
	<jstl:if test="${folder.id==0}">
		<input type="submit" name="delete" value="Delete"/>
	</jstl:if>
	<input type="button" name="cancel" value="Cancel" onclick="javascript: relativeRedir('folder/list.do');" />
	
</form:form>
