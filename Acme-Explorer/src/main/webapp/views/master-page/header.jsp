<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Sample Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/admin/display.do">My Profile</a></li>
					<li><a href="category/admin/list.do">Taxonomy of Categories</a></li>					
					<li><a href="manager/admin/create.do">Register a Manager</a></li>					
					<li><a href="ranger/admin/create.do">Register a Ranger</a></li>					
					<li><a href="trip/admin/list.do">List of Trips</a></li>					
					<li><a href="folder/list.do">My Folders</a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv">AUDITOR</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/auditor/display.do">My Profile</a></li>
					<li><a href="audit/auditor/list.do">My Audit Records</a></li>										
					<li><a href="trip/auditor/list.do">List of Trips</a></li>					
					<li><a href="folder/list.do">My Folders</a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv">EXPLORER</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/explorer/display.do">My Profile</a></li>
					<li><a href="finder/explorer/display.do">My Finder -</a></li>									
					<li><a href="trip/explorer/list.do">List of Trips</a></li>					
					<li><a href="tripApplication/explorer/list.do">My Trip application</a></li>					
					<li><a href="folder/list.do">My Folders</a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv">MANAGER</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/manager/display.do">My Profile</a></li>									
					<li><a href="trip/manager/list.do">List of my Trips</a></li>								
					<li><a href="folder/list.do">My Folders</a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('RANGER')">
			<li><a class="fNiv">RANGER</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/ranger/display.do">My Profile</a></li>								
					<li><a href="folder/list.do">My Folders</a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv">SPONSOR</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/sponsor/display.do">My Profile</a></li>																
					<li><a href="folder/list.do">My Folders</a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do">List of Categories</a></li>																
					<li><a href="explorer/create.do">Create an Explorer Account</a></li>				
					<li><a href="ranger/create.do">Create a Ranger Account</a></li>				
					<li><a href="trip/list.do">List of Trips</a></li>				
					<li><a href="trip/search.do">Search of Trips</a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

