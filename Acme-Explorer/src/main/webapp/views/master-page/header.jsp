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
	<img src="${logo}" alt="Sample Co., Inc." height="200"/>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/admin/display.do"><spring:message code="master.page.user.profile" /></a></li>
					<li><a href="category/admin/list.do"><spring:message code="master.page.admin.register.category" /></a></li>					
					<li><a href="manager/admin/create.do"><spring:message code="master.page.admin.register.manager" /></a></li>					
					<li><a href="ranger/admin/create.do"><spring:message code="master.page.admin.register.ranger" /></a></li>
					<li><a href="actor/admin/listSuspicious.do"><spring:message code="master.page.admin.list.suspicious" /></a></li>					
					<li><a href="admin/display-dashboard.do"><spring:message code="master.page.admin.dashboard" /></a></li>					
					<li><a href="trip/admin/list.do"><spring:message code="master.page.user.trips" /></a></li>
					<li><a href="systemConfiguration/admin/display.do"><spring:message code="master.page.admin.systemConfiguration" /></a></li>						
					<li><a href="trip/admin/search.do"><spring:message code="master.page.user.tripSearch" /></a></li>					
					<li><a href="folder/admin/list.do"><spring:message code="master.page.user.folders" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/auditor/display.do"><spring:message code="master.page.user.profile" /></a></li>
					<li><a href="audit/auditor/list.do"><spring:message code="master.page.auditor.audit" /></a></li>
					<li><a href="note/auditor/list.do"><spring:message code="master.page.auditor.notes" /></a></li>
					<li><a href="category/auditor/list.do"><spring:message code="master.page.user.category" /></a></li>										
					<li><a href="trip/auditor/list.do"><spring:message code="master.page.user.trips" /></a></li>			
					<li><a href="trip/auditor/search.do"><spring:message code="master.page.user.tripSearch" /></a></li>				
					<li><a href="folder/auditor/list.do"><spring:message code="master.page.user.folders" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message	code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/explorer/display.do"><spring:message code="master.page.user.profile" /></a></li>
					<li><a href="finder/explorer/display.do"><spring:message code="master.page.explorer.finder" /></a></li>
					<li><a href="category/explorer/list.do"><spring:message code="master.page.user.category" /></a></li>										
					<li><a href="trip/explorer/list.do"><spring:message code="master.page.user.trips" /></a></li>			
					<li><a href="trip/explorer/search.do"><spring:message code="master.page.user.tripSearch" /></a></li>					
					<li><a href="tripApplication/explorer/list.do"><spring:message code="master.page.explorer.tripApplication" /></a></li>
					<li><a href="story/explorer/create.do"><spring:message code="master.page.explorer.story" /></a></li>
					<li><a href="survivalClass/explorer/list.do"><spring:message code="master.page.explorer.survivalClasses" /></a></li>						
					<li><a href="folder/explorer/list.do"><spring:message code="master.page.user.folders" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/manager/display.do"><spring:message code="master.page.user.profile" /></a></li>	
					<li><a href="category/manager/list.do"><spring:message code="master.page.user.category" /></a></li>									
					<li><a href="trip/manager/list.do"><spring:message code="master.page.manager.myTrips" /></a></li>
					<li><a href="trip/manager/list.do?showAll=true"><spring:message code="master.page.user.trips" /></a></li>			
					<li><a href="trip/manager/search.do"><spring:message code="master.page.user.tripSearch" /></a></li>	
					<li><a href="tripApplication/manager/list.do"><spring:message code="master.page.manager.tripApplications" /></a></li>
					<li><a href="survivalClass/manager/list.do"><spring:message code="master.page.manager.survivalClasses" /></a></li>												
					<li><a href="folder/manager/list.do"><spring:message code="master.page.user.folders" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('RANGER')">
			<li><a class="fNiv"><spring:message	code="master.page.ranger" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/ranger/display.do"><spring:message code="master.page.user.profile" /></a></li>
					<li><a href="category/ranger/list.do"><spring:message code="master.page.user.category" /></a></li>	
					<li><a href="trip/ranger/list.do"><spring:message code="master.page.user.trips" /></a></li>				
					<li><a href="trip/ranger/search.do"><spring:message code="master.page.user.tripSearch" /></a></li>								
					<li><a href="folder/ranger/list.do"><spring:message code="master.page.user.folders" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/sponsor/display.do"><spring:message code="master.page.user.profile" /></a></li>
					<li><a href="category/sponsor/list.do"><spring:message code="master.page.user.category" /></a></li>	
					<li><a href="trip/sponsor/list.do"><spring:message code="master.page.user.trips" /></a></li>				
					<li><a href="trip/sponsor/search.do"><spring:message code="master.page.user.tripSearch" /></a></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.user.sponsorships" /></a></li>																
					<li><a href="folder/sponsor/list.do"><spring:message code="master.page.user.folders" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/list.do"><spring:message code="master.page.anonymous.register.category" /></a></li>																
					<li><a href="explorer/create.do"><spring:message code="master.page.anonymous.register.explorer" /></a></li>				
					<li><a href="ranger/create.do"><spring:message code="master.page.anonymous.register.ranger" /></a></li>				
					<li><a href="trip/list.do"><spring:message code="master.page.anonymous.register.listTrips" /></a></li>				
					<li><a href="trip/search.do"><spring:message code="master.page.anonymous.register.tripSearch" /></a></li>				
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
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

