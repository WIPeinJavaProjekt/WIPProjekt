<div class="pure-control-group">
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
	            	<c:forEach items="${availableSizes}" var="size">
		            	<li>
	                        <label class="control control-checkbox">
						        ${size}
					            <input type="checkbox" name="size" value="${size}" />
						        <div class="control_indicator"></div>
						    </label>
						 </li>
		            </c:forEach>  
	            </ul>
	        </div>
	    </dd>
	</div>
</div>