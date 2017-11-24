<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<c:choose>
			<c:when test="${updateArticle}">
				<title>Artikel bearbeiten</title>
			</c:when>
			<c:otherwise>
				<title>Artikel anlegen</title>
			</c:otherwise>
		</c:choose>
		
		<link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./CSS/default.css">
		<link rel="stylesheet" type="text/css" href="./CSS/startpageslider.css">
		<link rel="stylesheet" type="text/css" href="./CSS/w3.css">
		
		<script type="text/javascript" src="/Snowventure/JS/jquery-3.2.1.min.js"></script>		
		<script type="text/javascript" src="./JS/header.js"></script>
		<script type="text/javascript" src="./JS/displayImage.js"></script>
		<script type="text/javascript" src="./JS/versions.js"></script>
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="32x32">
		<link rel="icon" type="image/png" href="./Images/favicon.png" sizes="96x96">
		
		<script src="./JS/jquery-3.2.1.min.js"></script>
    	<script src="./JS/header.js"></script>
	</head>
	
	<body>
	
		<%@include file = "../Basic/header.jsp" %> 
	
		<div class="pure-g" id="articleadminbox">
		
			<div class="pure-u-1-5 sizezero"></div>
			<div class="pure-u-3-5 fullwidth">			
				<c:choose>
					<c:when test="${updateArticle}">
						<h1>Artikel bearbeiten</h1>
					</c:when>
					<c:otherwise>
						<h1>Artikel anlegen</h1>
					</c:otherwise>
				</c:choose>
			
				<form id="articleadminboxform" class="pure-form  textbox" enctype="multipart/form-data" action="article" method="post">
					
				    <fieldset class="pure-group">
				    	<div class="pure-g">
							<div class="pure-u-1-2 fullwidth">
						    	<div class="pure-control-group">
						            <input required class="boxedinput" id="articleName"  name="articleName" value="${article.GetName()}" type="text" placeholder="Artikelbezeichnung">
						        </div>
						        <div class="pure-control-group">
						            <input required class="boxedinput" id="manufacturer"  name="manufacturer" value="${article.GetManufacturer()}" type="text" placeholder="Hersteller">
						        </div>
						        <div class="pure-control-group">
						        	<select name="category" class="boxedinput">
								        <c:forEach items="${categories}" var="categories">
							        		<option value="${categories.GetACID()}">${categories.GetName()}</option>
							    		</c:forEach>
									</select>
						        </div>				        
						        <div class="pure-control-group">
						            <textarea required class="boxedinput" style="resize: vertical;" id="articleDescription" name="articleDescription" value="${article.GetDescription()}" type="text" 
						            	placeholder="Artikelbeschreibung <h1>HTML möglich</h1>">${article.GetDescription()}</textarea>
						        </div>
						        <c:choose>
									<c:when test="${updateArticle}">
											<div class="boxedinput">
												<label> Ausgewählte Version:</label><br>
												<select required id="selectedVersion" name="selectedVersion" class="boxedinput" 
												onchange="changeVersion(${article.GetId()}, 'article')">
													<c:forEach items="${article.GetAllVersions()}" var="version">
										        		<option value="${article.GetAllVersions().indexOf(version)}"<c:if test="${article.GetSelectedVersion()==article.GetAllVersions().indexOf(version)}"><c:out value="selected"/></c:if>>${article.GetAllVersions().indexOf(version)}</option>
										    		</c:forEach>
												</select>
									        </div>
									</c:when>
									<c:otherwise>
										<div class="pure-control-group">
											<select required name="selectedVersion" class="boxedinput">
									        		<option value="0">0</option>
											</select>
								        </div>	
									</c:otherwise>
								</c:choose>
								<br>
							</div>
							<div class="pure-u-1-2 fullwidth">
								<div class="pure-control-group">
						            	<input required class="boxedinput custom-file-input" type="file" id="articleImage" name="articleImage" onchange="readURL(this);">
						        </div>
						        <div class="pure-control-group">
						        	<c:if test="${updateArticle}">
							            <button class="pure-button pure-button-primary boxedinput" type="submit" name="addImage" onclick="changeVersion(${article.GetId()}, 'article')" >Bild hinzufügen</button>
							        </c:if>
						        </div>
								<div class="pure-control-group">
						            <input class="boxedinput" id="property" name="property" value="${article.GetProperty()}" type="text" placeholder="Eigenschaft">
						        </div>
						        <div class="pure-control-group">
						            <input class="boxedinput" id="propertyValue" name="propertyValue" value="${article.GetPropertyValue()}" type="text" placeholder="Wert der Eigenschaft">
						        </div>
						        <br>
						        <%@include file = "../Basic/filterColor.jsp" %>
						        <br>
						        <%@include file = "../Basic/filterGender.jsp" %>				        
						        <br>
						        <%@include file = "../Basic/filterSize.jsp" %>
						        <br>
						        <script type="text/javascript" src="./JS/dropdown.js"></script>	
						        <div class="pure-control-group">
						            <input required class="boxedinput" id="price" name="price" value="${article.GetPrice()}" type="number" step="0.01" placeholder="Preis">
						        </div>
						        <div class="pure-control-group">
						        	<c:if test="${updateArticle}">
						            	<button class="pure-button pure-button-primary boxedinput" type="submit" name="addArticleVersion" 
						            		onclick="changeVersion(${article.GetId()}, 'article')" >Als neue Artikelversion speichern</button>
					            	</c:if>
						        </div>
						        
							</div>
						</div>
			        </fieldset>
			        
			        <fieldset>
						<div class="pure-control-group">
							<c:choose>
								<c:when test="${updateArticle}">
						            <button class="pure-button pure-button-primary boxedinput" type="submit" name="updateArticle">Artikelversion aktualisieren</button>
						        </c:when>
						        <c:otherwise>
						        	<button class="pure-button pure-button-primary boxedinput" type="submit" name="addArticle">Artikel anlegen</button>
						        </c:otherwise>
					        </c:choose>   
				        </div>
			       	</fieldset>
			       	<fieldset>
						<div class="pure-control-group">
			       			<button class="pure-button pure-button-primary" type="submit" name="back">Zurück zur Artikelübersicht</button>
			       		</div>
			       	</fieldset>
		       	</form>
			       	<div class="w3-content w3-display-container w3slidmod fullwidth" align="center">

						<c:forEach items="${article.GetAllVersions().get(article.GetSelectedVersion()).getArticlesPictures()}" var="ap">
					   		<div class="w3-display-container mySlides ">
								<div class="productCardimage">
							   		<span class="articleimagehelper"></span><img src="${pageContext.request.contextPath}/images/${ap.GetImageId()}" class="articlesearchimage"/>
							   	</div>
							</div>
						</c:forEach>
						<c:if test="${article.GetAllVersions().get(article.GetSelectedVersion()).getArticlesPictures().size()>1}"> 
							<button class="w3-button w3-display-left w3-black" onclick="plusDivs(-1)">&#10094;</button>
							<button class="w3-button w3-display-right w3-black" onclick="plusDivs(1)">&#10095;</button>
						</c:if>
					
					</div>
				

				<c:if test="${article.GetAllVersions().get(article.GetSelectedVersion()).getArticlesPictures().size()>0}"> 
					<script>
						var slideIndex = 1;
						showDivs(slideIndex);
						
						function plusDivs(n) {
						  showDivs(slideIndex += n);
						}
						
						function showDivs(n) {
						  var i;
						  var x = document.getElementsByClassName("mySlides");
						  if (n > x.length) {slideIndex = 1}    
						  if (n < 1) {slideIndex = x.length}
						  for (i = 0; i < x.length; i++) {
						     x[i].style.display = "none";  
						  }
						  x[slideIndex-1].style.display = "block";  
						}
					</script>
				</c:if>

				<c:if test="${not empty errorArticle}">
					<div class="pure-g">
					    <div class ="pure-u-1-2 fullwidth">
							<form class="pure-form pure-form-aligned" action="article" method="get">
									<p class="error">${errorArticle}</p>
							</form>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty successArticle}">
					<div class="pure-g">
					    <div class ="pure-u-1-2 fullwidth">
							<form class="pure-form pure-form-aligned" action="article" method="get">
									<p class="success">${successArticle}</p>
							</form>
						</div>
					</div>
				</c:if>
		   
			</div>
			<div class="pure-u-1-5 sizezero"></div>
		</div>
		
		<%@include file = "../Basic/footer.jsp" %>
	</body>
	
</html>