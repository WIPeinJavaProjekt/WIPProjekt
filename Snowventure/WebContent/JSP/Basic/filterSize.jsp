<!-- 
Beschreibung: Filter für Größen
Ansprechpartner: Fabian Meise
 -->
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
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
						        <c:choose>
					        		<c:when test="${fn:contains(sizes, size)}">
					        			<input type="checkbox" name="size" checked value="${size}" />
					        		</c:when>
					        		<c:otherwise>
					            		<input type="checkbox" name="size" value="${size}" />
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