<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><strong><spring:message code="message.sender"/>:</strong> <jstl:out value="${messageDisplay.sender.name}"/></p>
<p><strong><spring:message code="message.recipient"/>:</strong> <jstl:out value="${messageDisplay.recipient.name}"/></p>
<p><strong><spring:message code="message.date"/>:</strong> <jstl:out value="${messageDisplay.sentMoment}"/></p>
<br>
<p><strong><spring:message code="message.subject"/>:</strong> <jstl:out value="${messageDisplay.subject}"/></p>
<hr>
<p><jstl:out value="${messageDisplay.body}"/></p>
<hr>
