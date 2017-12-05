/**
 * Beschreibung: Funktionen zum Zeigen und Verstecken eines Pop-Up-Fensters.
 * @author Jacob Markus 
 *
 */
//Function To Display Popup
function div_show(divName) {
    document.getElementById(divName).style.display = "block";
}

//Function to Hide Popup
function div_hide(divName) {
    document.getElementById(divName).style.display = "none";
}