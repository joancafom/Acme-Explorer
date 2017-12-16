<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="message/edit.do" modelAttribute="messageEdit">


		<jstl:if test="${messageEdit.id==0}">
			<form:hidden path="version"/>
			<form:hidden path="id"/>
			<form:hidden path="sender"/>
			<form:hidden path="sentMoment"/>
			<form:hidden path="folder"/>
	
			<form:label path="subject">Subject</form:label>
			<form:input path="subject"/>
			<form:errors cssClass="error" path="subject"/>	
			<br><br>
			<form:label path="body">Body</form:label>
			<form:textarea path="body"/>
			<form:errors cssClass="error" path="body"/>
			<br><br>
			<form:label path="recipient">Actor</form:label>
			<form:select path="recipient">
				<form:option value="0" label="----"/>
				<form:options items="${actors}"
					itemValue="id"
					itemLabel="name"/>
			</form:select>
			<form:errors cssClass="error" path="recipient"/>
			<br><br>
			<form:label path="priority">Priority</form:label>
			<form:select path="priority">
				<form:option value="0" label="----"/>
				<jstl:forEach items="${priorities}" var="priority">
					<form:option value="${priority}" label="${priority}"/>
				</jstl:forEach>
			</form:select>
			<form:errors cssClass="error" path="priority"/>
			<br><br>
			<input type="submit" name="save" value="Save"/>	
			<input type="button" name="cancel" value="Cancel" onclick="javascript: relativeRedir('folder/list.do');" />
	
		</jstl:if>
		
		<jstl:if test="${messageEdit.id!=0}">
			
			<form:hidden path="version"/>
			<form:hidden path="id"/>
			<form:hidden path="sender"/>
			<form:hidden path="recipient"/>
			<form:hidden path="sentMoment"/>
			<form:hidden path="body"/>
			<form:hidden path="priority"/>
			<form:hidden path="subject"/>	
			
			<form:label path="folder">Change folder: </form:label>
			<form:select path="folder">
				<form:options items="${folders}"
					itemValue="id"
					itemLabel="name"
				/>
			</form:select>
			<form:errors cssClass="error" path="folder"/>
			<br><br>
			<input type="submit" name="save" value="Save"/>	
			<input type="submit" name="delete" value="Delete"/>
			<input type="button" name="cancel" value="Cancel" onclick="javascript: relativeRedir('folder/list.do');" />
		
		</jstl:if>
</form:form>