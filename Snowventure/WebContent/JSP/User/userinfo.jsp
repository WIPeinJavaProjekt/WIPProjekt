<!-- 
Beschreibung: �bersicht von Nutzerdaten. Zur Bearbeitung von Nutzerdaten (Eigene oder als Admin auch andere) oder zur Registrierung
Ansprechpartner: Jacob Markus, Garrit Kniepkamp
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form class="pure-form  textbox selecteduserbox" <c:choose>
    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">
        action="users"
    </c:when>
    <c:otherwise>
        action="register"
    </c:otherwise>
    </c:choose>
    method="post">

    <c:if test="${not empty selectedUser && not empty currentUser}">
        <legend>
            <h1>Ausgew�hlter Nutzer: "${selectedUser.username}"</h1>
        </legend>
    </c:if>

    <div class="pure-g textbox">
        <div class="pure-u-1-2 leftdiv fullwidth">
            <fieldset class="pure-group">
                <h3>Name</h3>
                <c:if test="${selectedUser == null || empty currentUser}">
                    <div class="pure-control-group">
                        <input class="boxedinput" id="username" name="username" maxlength="125" <c:choose>
                        <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.username}"</c:when>
                        <c:otherwise>value="${username}"</c:otherwise>
                        </c:choose> value="${username}" type="text" required placeholder="Benutzername">
                    </div>
                </c:if>
                <div class="pure-control-group">
                    <input class="boxedinput" id="name" name="name" maxlength="125" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.name}"</c:when>
                    <c:otherwise>value="${name}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Vorname">
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="surname" name="surname" maxlength="125" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.surname}"</c:when>
                    <c:otherwise>value="${surname}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Nachname">
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="email" name="email" maxlength="125" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.email}"</c:when>
                    <c:otherwise>value="${email}"</c:otherwise>
                    </c:choose> type="email" required placeholder="E-Mail-Addresse">
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="phone" name="phone" maxlength="125" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.phone}"</c:when>
                    <c:otherwise>value="${phone}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Telefon">
                </div>
            </fieldset>

        </div>
        <div class="pure-u-1-2 fullwidth">
            <fieldset class="pure-group rightdiv">
                <h3>Passwort</h3>
                <div class="pure-control-group">
                    <input class="boxedinput" id="password" name="password" type="password" placeholder="Passwort" maxlength="50" <c:if test="${empty currentUser && selectedUser == null}">required</c:if>>
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="passwordRepeat" name="passwordRepeat" type="password" placeholder="Passwort wiederholen" maxlength="50" <c:if test="${empty currentUser && selectedUser == null}">required</c:if>>
                </div>
                <c:if test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null && not empty error}">
                    <div class="pure-control-group">
                        <p class="error">${error}</p>
                    </div>
                </c:if>
            </fieldset>
        </div>
    </div>

    <div class="pure-g">
        <div class="pure-u-1-2 leftdiv fullwidth">
            <fieldset class="pure-group">
                <h3>Anschrift</h3>
                <div class="pure-control-group">
                    <input class="boxedinput" id="location" name="location" maxlength="125" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.adress.location}"</c:when>
                    <c:otherwise>value="${location}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Wohnort">
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="street" name="street" maxlength="125" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.adress.street}"</c:when>
                    <c:otherwise>value="${street}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Stra�e">
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="houseno" name="houseno" maxlength="50" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.adress.houseno}"</c:when>
                    <c:otherwise>value="${houseno}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Hausnummer">
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="postcode" name="postcode" maxlength="10" <c:choose>
                    <c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">value="${selectedUser.adress.postcode}"</c:when>
                    <c:otherwise>value="${postcode}"</c:otherwise>
                    </c:choose> type="text" required placeholder="Postleitzahl">
                </div>
            </fieldset>

            <fieldset class="pure-group">
                <c:if test="${not empty currentUser && currentUser.utid == '1'}">
                    <div class="pure-control-group">
                        <h3>Nutzertyp</h3>
                        <select id="state" name="state" class="boxedinput" required>
                 <option value="customer" <c:if test="${not empty currentUser && selectedUser.utid=='2'}"><c:out value="selected"/></c:if>>Kunde</option>
                 <option value="employee" <c:if test="${not empty currentUser && selectedUser.utid=='3'}"><c:out value="selected"/></c:if>>Mitarbeiter</option>
                 <option value="admin" <c:if test="${not empty currentUser && selectedUser.utid=='1'}"><c:out value="selected"/></c:if>>Admin</option>
             </select>
                    </div>
                </c:if>
            </fieldset>

        </div>
        <div class="pure-u-1-2 rightdiv fullwidth">
            <fieldset class="pure-group">
                <h3>Sicherheitsfrage</h3>
                <div class="pure-control-group">
                    <select class="boxedinput" id="safetyQuestion" name="safetyQuestion" required>
          	 <option value="">Bitte Sicherheitsfrage ausw�hlen</option>
	    <c:forEach items="${squestions}" var="squestion">
	        <option value="${squestion.getId()}"  <c:if test="${not empty currentUser && squestion.getId() == selectedUser.squestion.getId()}">selected</c:if>>${squestion.getQuestion()}</option>
	    </c:forEach>
	</select>
                </div>
                <div class="pure-control-group">
                    <input class="boxedinput" id="safetyAnswer" name="safetyAnswer" type="text" maxlength="200" <c:if test="${not empty currentUser && selectedUser != null}">value="${ selectedUser.squestion.getAnswer() }"</c:if> required placeholder="Antwort">
                </div>
            </fieldset>
            <c:if test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">
                <fieldset class="pure-group">
                    <h3>Nutzerstatus</h3>
                    <div class="pure-control-group">
                        <input style="margin-left: 10px" type="checkbox" name="user-status" id="user-status" <c:if test="${selectedUser.techisactive == 1}">checked</c:if>> aktiv</input>
            </div>
            </fieldset>
            </c:if>
        </div>
    </div>
    
	<div class="pure-g selecteduserbox">
		<div class="pure-u-1-2 leftdiv fullwidth">   
			<fieldset>
				<div class="pure-control-group">
					<c:choose>
						<c:when test="${empty currentUser || selectedUser == null}">
							<button class="pure-button pure-button-primary boxedinput" type="submit" name="submitRegister" class="pure-button pure-button-primary">Abschicken</button>
						</c:when>
						<c:when test="${not empty currentUser && currentUser.utid == '1' && selectedUser != null}">
							<button class="pure-button pure-button-primary boxedinput" type="submit" name="updateSelection" class="pure-button pure-button-primary">Speichern</button>
						</c:when>
					</c:choose>
				</div>
			</fieldset>
		</div>
	</div>    
</form>

<c:if test="${not empty currentUser && selectedUser != null}">
    <div class="pure-g selecteduserbox textbox" style="padding-bottom:5%;">
        <div class="pure-u-1-2 leftdiv fullwidth">
            <form action="users" method="post">
                <button class="pure-button pure-button-primary boxedinput" type="submit" name="back" class="pure-button pure-button-primary">Zur�ck</button>
            </form>
        </div>
    </div>
</c:if>


<div class="pure-u-1-5"></div>