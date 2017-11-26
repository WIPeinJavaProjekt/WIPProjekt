/**
 * Beschreibung: 
 * @author Ansprechpartner 
 *
 */
function readURL(input) {
	console.log(input);
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#uploadedImage')
                .attr('src', e.target.result)
        };

        reader.readAsDataURL(input.files[0]);
    }
}