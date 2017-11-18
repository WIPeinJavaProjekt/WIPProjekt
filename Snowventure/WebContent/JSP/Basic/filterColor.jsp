<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<div class="pure-control-group">									
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
		            <c:forEach items="${articleColors}" var="color">
		            	<li>
	                        <label class="control control-checkbox">
					        	<div class="checkbox-colorbox" style="background-color:${color.GetHexcode()} !important;"></div> 
					        		${color.GetColorName()}
					        		<c:choose>
						        		<c:when test="${fn:contains(colors, color.GetColorName())}">
						        			<input type="checkbox" name="color" checked value="${color.GetAcolid()}" />
						        		</c:when>
						        		<c:otherwise>
						            		<input type="checkbox" name="color" value="${color.GetAcolid()}" />
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