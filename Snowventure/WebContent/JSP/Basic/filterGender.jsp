<!-- 
Beschreibung: Filter für Geschlecht
Ansprechpartner: Garrit Kniepkamp, Fabian Meise
 -->
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
    <div class="pure-control-group">
        <div id="filter_gender" class="dropdown">
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
				        	<c:choose>
				        		<c:when test="${fn:contains(genders, 'Herren')}">
				        			<input type="checkbox" name="genders" checked value="Herren" />
				        		</c:when>
				        		<c:otherwise>
				            		<input type="checkbox" name="genders" value="Herren" />
			            		</c:otherwise>
		            		</c:choose>
					        <div class="control_indicator"></div>
					    </label>
                        </li>
                        <li>
                            <label class="control control-checkbox">
				        	Damen 
				        	<c:choose>
				        		<c:when test="${fn:contains(genders, 'Damen')}">
				        			<input type="checkbox" name="genders" checked value="Damen" />
				        		</c:when>
				        		<c:otherwise>
				            		<input type="checkbox" name="genders" value="Damen" />
			            		</c:otherwise>
		            		</c:choose>
					        <div class="control_indicator"></div>
					    </label>
                        </li>
                        <li>
                            <label class="control control-checkbox">
				        	Kinder
				        	<c:choose>
				        		<c:when test="${fn:contains(genders, 'Kinder')}">
				        			<input type="checkbox" name="genders" checked value="Kinder" />
				        		</c:when>
				        		<c:otherwise>
				            		<input type="checkbox" name="genders" value="Kinder" />
			            		</c:otherwise>
		            		</c:choose>
					        <div class="control_indicator"></div>
					    </label>
                        </li>
                    </ul>
                </div>
            </dd>
        </div>
    </div>