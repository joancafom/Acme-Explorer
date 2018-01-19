<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 



<display:table name="explorers" id="explorer" requestURI="explorer/manager/list.do" pagesize="5" class="displaytag">

	<display:column property="name" titleKey="explorer.name" sortable="true"/>
	
	<display:column property="surname" titleKey="explorer.surname" sortable="true" />
	
	<display:column property="email" titleKey="explorer.email" />
	
	<display:column property="phoneNumber" titleKey="explorer.phoneNumber" />

	<display:column property="address" titleKey="explorer.address" />
	
</display:table>
