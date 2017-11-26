<!-- 
Beschreibung: Filter für Hersteller
Ansprechpartner: Fabian Meise
 -->
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
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
		            <c:forEach items="${availableManufacturers}" var="manufacturer">
		            	<li>
	                        <label class="control control-checkbox">
						        ${manufacturer}
							        <c:choose>
						        		<c:when test="${fn:contains(manufacturers, manufacturer)}">
						        			<input type="checkbox" name="manufacturer" checked value="${manufacturer}" />
						        		</c:when>
						        		<c:otherwise>
						            		 <input type="checkbox" name="manufacturer" value="${manufacturer}" />
					            		</c:otherwise>
				            		</c:choose>						           
						        <div class="control_indicator"></div>
						    </label>
						 </li>
		            </c:forEach>
	            </ul>
	        </div>
	    </dd>
	</div>
</div>