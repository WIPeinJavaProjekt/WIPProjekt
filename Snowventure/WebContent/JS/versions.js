/**
 * Beschreibung: Redirect auf Artikelseite für "onClick"-Funktionen
 * @author Garrit Kniepkamp
 *
 */

function changeVersion(aid, page) {
    var version = $('#selectedVersion').find(':selected').text();
    location.href = './' + page + '?ID=' + aid + '&version=' + version;
}

function changeToStock(aid, page) {
    var version = $('#selectedVersion').find(':selected').text();
    var url = './' + page + '?ID=' + aid + '&version=' + version + '&stock';
    location.href = url;
}

function customerChangeVersion(aid, page, version) {
    console.log(version);
    location.href = './' + page + '?ID=' + aid + '&version=' + version;
}