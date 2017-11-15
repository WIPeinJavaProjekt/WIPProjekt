<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="./CSS/w3.css">

	<div class="pure-g" id ="searchfilter">
		<div class="pure-u-1-5"></div>
		<div id="wrapper" class="pure-u-3-5">
			<div id="search-container" class="searchbox">
				<form class="pure-form" action="articles" method="post">
				    <fieldset>
						<div class="pure-g" id="filters">
					        <div class="pure-u-1-5">
						        <select name="categorie" class="boxedinput">
							        <option value="-1">Alle</option>
							        <c:forEach items="${categories}" var="categories">
							        <option value="${categories.GetACID()}">${categories.GetName()}</option>
						    		</c:forEach>
								</select>
							</div>
					        <div class="pure-u-3-5">
					        	<input type="text" class="boxedinput" name ="searchArticlePattern" placeholder="Wähle deine Ausrüstung">
							</div>
							<div class="pure-u-1-5">
				        		<button type="submit" id="search" name="search" class="pure-button pure-button-primary boxedinput">Suchen</button>
				        	</div>
				        </div>
				    </fieldset>
				 
				 	<a id="filterbtn-show" href="javascript:void(0)">Filter &#x25bc;</a>
				    <fieldset id="filtersfield" style="display:none;">
					    <div id="filter_manufacturer">
					    
					    
						<div class="dropdown"> 
						    <div>
						    <a href="javascript:void(0)">
						      <span class="hida"><u>Hersteller</u></span>    
						      <p class="multiSel"></p>  
						    </a>
						    </div>
						  
						    <dd>
						        <div class="mutliSelect">
						            <ul>
							            <c:forEach items="${manufacturers}" var="manufacturer">
							            	<li>
							                        <label class="control control-checkbox">
												        ${manufacturer}
												            <input type="checkbox" name="manufacturer" value="${manufacturer}" />
												        <div class="control_indicator"></div>
												    </label>
											 </li>
							            </c:forEach>
						            </ul>
						        </div>
						    </dd>
						</div>
					    
					    
					    </div>
				    
					    <div id="filter_color" class="dropdown"> 
						    <div>
						    <a href="javascript:void(0)">
						      <span class="hida"><u>Farbe</u></span>    
						      <p class="multiSel"></p>  
						    </a>
						    </div>
						  
						    <dd>
						        <div class="mutliSelect">
						            <ul>						            
							            <c:forEach items="${colors}" var="color">
							            	<li>
						                        <label class="control control-checkbox">
										        	<div class="checkbox-colorbox" style="background-color:${color.GetHexcode()} !important;"></div> ${color.GetColorName()} 
										            <input type="checkbox" name="colors" value=${color.GetAcolid()} />
											        <div class="control_indicator"></div>
											    </label>
											</li>
							            </c:forEach>			            
						            </ul>
						        </div>
						    </dd>
						</div>
						
						<div id="filter_size" class="dropdown"> 
						    <div>
						    <a href="javascript:void(0)">
						      <span class="hida"><u>Größe</u></span>    
						      <p class="multiSel"></p>  
						    </a>
						    </div>
						  
						    <dd>
						        <div class="mutliSelect">
						            <ul>
						            	<c:forEach items="${sizes}" var="size">
							            	<li>
							                        <label class="control control-checkbox">
												        ${size}
												            <input type="checkbox" name="sizes" value="${size}" />
												        <div class="control_indicator"></div>
												    </label>
											 </li>
							            </c:forEach>  
						            </ul>
						        </div>
						    </dd>
						</div>
						<div class="pure-control-group">									
									<div id="filter_color" class="dropdown"> 
									    <div>
									    <a href="javascript:void(0)">
									      <span class="hida"><u>Geschlecht</u></span>    
									      <p class="multiSel"></p>  
									    </a>
									    </div>
									  
									    <dd>
									        <div class="mutliSelect">
									            <ul>						            
										            	<li>
									                        <label class="control control-checkbox">
													        	Herren 
													            <input type="checkbox" name="genders" value="Herren" />
														        <div class="control_indicator"></div>
														    </label>
														</li>	
														<li>
									                        <label class="control control-checkbox">
													        	Damen 
													            <input type="checkbox" name="genders" value="Damen" />
														        <div class="control_indicator"></div>
														    </label>
														</li>		
														<li>
									                        <label class="control control-checkbox">
													        	Kinder 
													            <input type="checkbox" name="genders" value="Kinder" />
														        <div class="control_indicator"></div>
														    </label>
														</li>			            
									            </ul>
									        </div>
									    </dd>
									</div>
						        </div>						        
						
						
						<div style="clear:both;">
						<br>
						<u style="font-size: 16px;">Preis</u>
						<span> von </span><input name="minprice" type="number" min="0" max="10000" step=".01">
					    <span> bis </span><input name="maxprice" type="number" min="0" max="10000" step=".01">
						</div>
					</fieldset>
				</form>
			</div>			
		</div>
		<div class="pure-u-1-5"></div>
	</div>

<center>

	<form name="cardForm" id="cardForm" action="articles" method="post">
		<div id="articleresultcontainer" class="search-results" align="left">	
			<c:if test="${ not empty articles }">
			 	<c:forEach var="article" items="${articles}">
				 		<div class="productCard pure-u-1-3" onclick="location.href='./articleshopping?ID=${article.GetId()}&version=${article.GetSelectedVersion()}';">
					 		<div  style="width:100%">
							    <div class="productCardimage">
							    	<span class="articleimagehelper"></span><img src="${pageContext.request.contextPath}/images/${article.GetAllVersions().get(article.GetSelectedVersion()).getArticleHeadPicture().GetImageId()}" class="articlesearchimage">
							    </div>
							    <div class="w3-container w3-center">
									<p>
									<b>${article.GetManufacturer()}</b> - ${article.GetName()}</p>
									<div style="height:1px; width:100%; background-color:rgb(75,75,75);"></div>
									<p>${article.GetPriceFormatted()} EUR <br></p>
						    	</div>
						  	</div>
						</div>
					
				</c:forEach>
			</c:if>
			<c:if test="${noArticleFound}">
				<form class="pure-form pure-form-aligned" action="users?page=articlesearch" method="get">
					<p class="error">Es wurde keine Suchergebnisse gefunden.</p>
				</form>	
			</c:if>	
		</div>
	</form>
</center>
<script type="text/javascript" src="./JS/dropdown.js"></script>	
<div class="article-info">
	
</div>