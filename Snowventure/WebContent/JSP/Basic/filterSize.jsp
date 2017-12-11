<!-- 
Beschreibung: Filter für Größen
Ansprechpartner: Fabian Meise
 -->
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
						        <c:forEach items="${sizes}" var="inputSize">
			                		<c:if test="${size == inputSize}">
			                			<input type="checkbox" name="size" checked value="${size}" />
			                		</c:if>
			                		<c:if test="${size != inputSize}">
			                			<input type="checkbox" name="size"  value="${size}" />
			                		</c:if>
			                	</c:forEach>		            
						        <div class="control_indicator"></div>
						    </label>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </dd>
    </div>
</div>