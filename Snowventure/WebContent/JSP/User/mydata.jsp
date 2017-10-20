<form class="pure-form pure-form-stacked" id="mydata-form" action="users" method="post">
    <fieldset>
        <legend>
        	<h1>Personenangaben</h1>
        </legend>
        
        <%@page import="classes.Safetyquestion"%>  
		<%
		//If user exists already - should be deleted from session.
		if(request.getSession().getAttribute("user") != null)
		request.getSession().removeAttribute("user");
		
		//User initilization for test purposes.
		classes.User Tuser = new classes.User(new Safetyquestion(),"test1" ,"test", "testname2", "testsurname", new classes.Adress("testlocation","test123","2356test","teststreet"), "12@io.com", 1);
		request.getSession().setAttribute("user", Tuser);
						
		classes.User user = (classes.User)(request.getSession().getAttribute("user"));
		%>
		
        <div class="pure-g">
            
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="first-name">Vorname</label>
	                <input id="first-name" name="first-name" class="pure-u-23-24" type="text" value="<%=user.name%>" required>
	            </div>
	
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="last-name">Nachname</label>
	                <input id="last-name" name="last-name" class="pure-u-23-24" type="text" value="<%=user.surname%>" required>
	            </div>
	
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="email">E-Mail</label>
	                <input id="email" name="email" class="pure-u-23-24" type="email" value="<%=user.email%>" required>
	            </div>			 
			
				<div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="street">Stra�e</label>
	                <input id="street" name="street" class="pure-u-23-24" type="text" value="<%=user.adress.street%>" required>
	            </div>
	
				<div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="houseno">Hausnummer</label>
	                <input id="houseno" name="houseno" class="pure-u-23-24" type="text" value="<%=user.adress.houseno%>" required>
	            </div>
	
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="location">Stadt</label>
	                <input id="location" name="location" class="pure-u-23-24" type="text" value="<%=user.adress.location%>" required>
	            </div>
	            
	            <div id="mydata-item" class="pure-u-1 pure-u-md-1-3">
	                <label for="postcode">PLZ</label>
	                <input id="postcode" name="postcode" class="pure-u-23-24" type="text" value="<%=user.adress.postcode%>" required>
	            </div>			
			
	            <div class="pure-u-1 pure-u-md-1-3">
	                <label for="state">Nutzertyp</label>
	                <select id="state" name="state" class="pure-input-1-2" required>
	                    <option value="customer" selected="<%=user.usertype==2?true:false%>" >Kunde</option>
	                    <option value="employee" selected="<%=user.usertype==3?true:false%>">Mitarbeiter</option>
	                    <option value="admin" selected="<%=user.usertype==1?true:false%>">Admin</option>
	                </select>
	            </div>
        </div>

        <button type="submit" name="update-data" class="pure-button pure-button-primary">�nderung speichern</button>
    </fieldset>
</form>

<form class="pure-form pure-form-stacked" id="security-form" action="users" method="post">
<fieldset>
    
    	<legend>
    		<h1>Passwort zur�cksetzen</h1>
    	</legend>
    	
    	<div class="pure-class-g">
            
            <div class="pure-u-1 pure-u-md-1-3">
	            <label for="password">Aktuelles Passwort</label>
	            <input id="password" name="password" type="password">
	        </div>
	        
	        <div class="pure-u-1 pure-u-md-1-3">
	            <label for="new-password">Neues Passwort</label>
	            <input id="new-password" name="new-password" type="password">
	        </div>
	        
	        <div class="pure-u-1 pure-u-md-1-3">
	            <label for="new-passwordRepeat">Neues Passwort wiederholen</label>
	            <input id="new-passwordRepeat" name="new-passwordRepeat" type="password">
	        </div>
	        
	        <button type="submit" name="update-password" class="pure-button pure-button-primary">�nderung speichern</button>
    	</div>
    </fieldset>
</form>
    