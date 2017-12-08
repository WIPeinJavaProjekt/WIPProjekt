<!-- 
Beschreibung: Seitenbereich zum Anzeigen der Daten des eingeloggten Nutzers.
Ansprechpartner: Jacob Markus
 -->
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    <div class="pure-g">
        <div class="pure-u-1-5 sizezero"></div>
        <div class="pure-u-3-5 fullwidth">

            <div id="popup-div">
                <div id="popup-div-content">
                    <form id="popup-div-form" name="delete-user-form" action="users" method="POST">
                        <i id="close" class="fa fa-close fa-1g" aria-hidden="true" onclick="div_hide('popup-div')"></i>
                        <center>
                            <h4>Deaktivierung</h4>
                            <hr>
                            <p>Wollen Sie wirklich Ihr Nutzerkonto deaktivieren?</p>
                            <p>Bei Fortsetzung dieses Vorgangs geht der Zugriff auf alle mit Ihrem Konto in Verbindung stehenden Daten für Sie verloren.</p>
                            <p>Um fortzufahren klicken Sie auf "Deaktivieren".</p>
                            <input class="pure-button pure-button-primary" name="deleteUserByUser" type="submit" id="popup-submit" value="Deaktivieren">
                        </center>
                    </form>
                </div>
            </div>

			<c:if test="${not empty passworderror}">
                <form id="change-password-error" class="pure-form pure-form-stacked security-form  textbox" action="users?page=mydata" method="get">
                    <p class="error">${passworderror}</p>
                </form>
            </c:if>

            <form class="pure-form pure-form-stacked  textbox" id="mydata-form" action="users?page=mydata" method="post">
                <fieldset>

                    <legend>
                        <h3>Meine Personenangaben</h3>
                    </legend>

                    <div class="pure-g">

                        <div id="mydata-item name" class="pure-u-1 pure-u-md-1-3">
                            <label for="first-name">Vorname</label>
                            <input id="first-name" name="name" class="pure-u-24-24" type="text" value="${currentUser.name}" required>
                        </div>

                        <div id="mydata-item last-name" class="pure-u-1 pure-u-md-1-3">
                            <label for="last-name">Nachname</label>
                            <input id="last-name" name="surname" class="pure-u-24-24" type="text" value="${currentUser.surname}" required>
                        </div>

                        <div id="mydata-item email" class="pure-u-1 pure-u-md-1-3">
                            <label for="email">E-Mail</label>
                            <input id="email" name="email" class="pure-u-24-24" type="email" value="${currentUser.email}" required>
                        </div>

                        <div id="mydata-item phone" class="pure-u-1 pure-u-md-1-3">
                            <label for="phone">Telefon</label>
                            <input id="phone" name="phone" class="pure-u-24-24" type="text" value="${currentUser.phone}" required>
                        </div>

                        <div id="mydata-item street" class="pure-u-1 pure-u-md-1-3">
                            <label for="street">Straße</label>
                            <input id="street" name="street" class="pure-u-24-24" type="text" value="${currentUser.adress.getStreet()}" required>
                        </div>

                        <div id="mydata-item houseno" class="pure-u-1 pure-u-md-1-3">
                            <label for="houseno">Hausnummer</label>
                            <input id="houseno" name="houseno" class="pure-u-24-24" type="text" value="${currentUser.adress.getHouseno()}" required>
                        </div>

                        <div id="mydata-item location" class="pure-u-1 pure-u-md-1-3">
                            <label for="location">Stadt</label>
                            <input id="location" name="location" class="pure-u-24-24" type="text" value="${currentUser.adress.getLocation()}" required>
                        </div>

                        <div id="mydata-item postcode" class="pure-u-1 pure-u-md-1-3">
                            <label for="postcode">PLZ</label>
                            <input id="postcode" name="postcode" class="pure-u-24-24" type="text" value="${currentUser.adress.postcode}" required>
                        </div>
                        <c:if test="${currentUser.utid == '1'}">
                            <div class="pure-u-1 pure-u-md-1-3">
                                <label for="state">Nutzertyp</label>
                                <select id="state" name="state" class=" boxedinput" required>
		                    <option value="customer" <c:if test="${currentUser.utid=='2'}"><c:out value="selected"/></c:if>>Kunde</option>
		                    <option value="employee" <c:if test="${currentUser.utid=='3'}"><c:out value="selected"/></c:if>>Mitarbeiter</option>
		                    <option value="admin" <c:if test="${currentUser.utid=='1'}"><c:out value="selected"/></c:if>>Admin</option>
		                </select>
                            </div>
                        </c:if>
                    </div>

                    <button type="submit" name="update-data" class="pure-button pure-button-primary boxedinput">Änderung speichern</button>
                </fieldset>
            </form>

            <form class="pure-form pure-form-stacked security-form  textbox" action="users?page=mydata" method="post">
                <fieldset>

                    <legend>
                        <h3>Mein Passwort zurücksetzen</h3>
                    </legend>

                    <div class="pure-class-g">

                        <div class="pure-u-1 pure-u-md-1-3">
                            <label for="password">Aktuelles Passwort</label>
                            <input id="password" name="password" type="password" class="boxedinput">
                        </div>

                        <div class="pure-u-1 pure-u-md-1-3">
                            <label for="new-password">Neues Passwort</label>
                            <input id="new-password" name="new-password" type="password" class="boxedinput">
                        </div>

                        <div class="pure-u-1 pure-u-md-1-3">
                            <label for="new-passwordRepeat">Neues Passwort wiederholen</label>
                            <input id="new-passwordRepeat" name="new-passwordRepeat" type="password" class="boxedinput">
                        </div>

                        <button type="submit" name="update-password" class="pure-button pure-button-primary boxedinput">Änderung speichern</button>
                    </div>
                </fieldset>
            </form>

            <form class="pure-form pure-form-stacked security-form  textbox" action="users?page=mydata" method="post">
                <fieldset>

                    <legend>
                        <h3>Meine Sicherheitsfrage</h3>
                    </legend>

                    <div class="pure-class-g">
                        <div class="pure-control-group">
                            <select class=" boxedinput" id="safetyQuestion" name="safetyQuestion" required>
					    <c:forEach items="${squestions}" var="squestion">
							<option value="${squestion.getId()}" <c:if test="${currentUser.squestion.getId()==squestion.getId()}">
																	<c:out value="selected"/>
																 </c:if> >${squestion.getQuestion()}
							</option>
						</c:forEach>
					</select>
                        </div>
                        <div class="pure-control-group">
                            <input class="boxedinput" id="safetyAnswer" name="safetyAnswer" type="text" value="${currentUser.squestion.getAnswer()}" required placeholder="Antwort">
                        </div>

                        <button type="submit" name="update-squestion" class="pure-button pure-button-primary boxedinput">Änderung speichern</button>
                    </div>
                </fieldset>
            </form>
            <form class="pure-form pure-form-stacked security-form  textbox">
                <fieldset>
                    <legend>
                        <h3>Mein Nutzerkonto deaktivieren</h3>
                    </legend>
                    <button type="button" name="delete-user" class="pure-button pure-button-primary boxedinput" onclick="div_show('popup-div')">Nutzerkonto deaktivieren</button>
                </fieldset>
            </form>
        </div>
        <div class="pure-u-1-5 sizezero"></div>
    </div>