<%@page import="classes.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>   
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="pure-g" id ="account-searchbox">
	<div class="pure-u-1-5"></div>
	<div id="wrapper" class="pure-u-3-5">
		<div id="search-container" class="searchbox">
			<form class="pure-form" action="users?page=usersearch" method="POST">
			    <fieldset>
			        <legend>
			        <h1>Nutzersuche</h1>
			        </legend>
					<div class="pure-g">
				        <div class="pure-u-1-5">
					        <select name="categories" class="boxedinput">
					        <c:set var="cat" value="${category}"/>
					          <option value="all" <c:if test="cat eq 'all'">selected</c:if>>Alle</option>
							  <option value="customer" <c:if test="cat eq 'customer'">selected</c:if>>Kunde</option>
							  <option value="employee" <c:if test="cat eq 'employee'">selected</c:if>>Mitarbeiter</option>
							  <option value="admin" <c:if test="cat eq 'admin'">selected</c:if>>Admin</option>
							</select>
						</div>
				        <div class="pure-u-3-5">
				        	<input type="text" name="user-info" value="${userinfo}" class="boxedinput" placeholder="Nutzername eingeben">
						</div>
						<div class="pure-u-1-5">
			        		<button type="submit" name="search-user" class="pure-button pure-button-primary boxedinput">Suchen</button>
			        	</div>
			        </div>
			    </fieldset>
			</form>			
			</div>
				<div>
				<form class="add-new-user-form" action="register" method="GET">
					<div class="pure-u-1-5" id="add-new-user">
				        <button type="submit" name="new-user" class="pure-button pure-button-primary boxedinput" style="width=20%">Nutzer anlegen</button>
				    </div>
				</form>		
			</div>			
		</div>
	<div class="pure-u-1-5"></div>
</div>

<div class="search-results">
	<c:if test="${ not empty userlist }">
		<table class="pure-table pure-table-horizontal">
		    <thead>
		        <tr>
		            <th>Nutzername</th>
		            <th>Nutzertyp</th>
		            <th>Option</th>
		        </tr>
		    </thead>
		 	<tbody>
		 	<c:forEach var="user" items="${userlist}">
					<tr class="pure-table-odd">
						<td id="uname"><c:out value="${user.username}"/></td>
						<td>
							<c:choose>
								<c:when test="${user.utid == 1}">
								 	Admin
								</c:when>
								<c:when test="${user.utid == 2}">
								 	Kunde
								</c:when>
								<c:when test="${user.utid == 3}">
								 	Mitarbeiter
								</c:when>
							</c:choose>
						</td>
						<td style="padding:0%">
							<a href="users?page=userinfo" class="pure-button pure-button-primary boxedinput" style="width:100%;height:100%">Bearbeiten</a>		
						</td>
					</tr>
			</c:forEach>
		 	</tbody>
		</table>
	</c:if>
	<c:if test="${nouserfound eq 'true'}">
		<form class="pure-form pure-form-aligned" action="users?page=usersearch" method="get">
			<p class="error">Es wurde keine Suchergebnisse gefunden.</p>
		</form>	
	</c:if>	
</div>

<div class="user-info">

</div>