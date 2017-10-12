<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Mein Konto</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to SnowSki</title>
<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/default.css">

<style type="text/css">

.pure-menu {
width: 15%;
text-align: left;
float: left;
margin-top:5%;
}

.custom-restricted {
display: inline-box;
}

#mydata-form {
	width: 50%;
	margin-left: 10%;
	margin-right: 10%;
	margin-top: 5%;
	float: left;	
}


</style>

</head>
<body>

	<%@include file = "header.jsp" %>
		
	<div class="pure-menu custom-restricted-width">
	    <span class="pure-menu-heading">Mein Konto</span>
	
	    <ul class="pure-menu-list">
	        <li class="pure-menu-item"><a href="useraccount.jsp?page=mydata" class="pure-menu-link">Meine Daten</a></li>
			<li class="pure-menu-item"><a href="useraccount.jsp?page=users" class="pure-menu-link" >Nutzerübersicht</a></li>
	        <li class="pure-menu-item"><a href="useraccount.jsp?page=articles" class="pure-menu-link">Artikelübersicht</a></li>
	        <li class="pure-menu-item"><a href="useraccount.jsp?page=orders" class="pure-menu-link">Bestellungen</a></li>
	        <li class="pure-menu-item"><p><i>Eingeloggt als: TestUser</i></p></li>
	    </ul>
	</div>
	
	<div id="content">
	
		<script type="text/javascript">
			
			$.urlParam = function(name){
			    var results = new RegExp('[\?&]' + name + '=([^]*)').exec(window.location.href);
			    if (results==null){
			       return null;
			    }
			    else{
			       return results[1] || 0;
			    }
			}
		
			// example.com?param1=name&param2=&id=6
			var file = $.urlParam('page')+'.jsp';
		
	    	if(file == 'mydata.jsp')
	        document.getElementById("content").InnerHTML = $('#content').load("mydata.jsp");			
		</script>
	
	</div>
	
</body>
</html>