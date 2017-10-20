/**
 * Function to add an html object from another JSP-file having the same name as the given value of parameter page of the url.
 */

					
// example_link to work with: example.com?param1=name&param2=&id=6		
$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       return results[1] || 0;
    }
}

if($.urlParam('page')!= null)
{
	var page = $.urlParam('page');
	
	var path = (page == 'articlesearch' ? './JSP/Articles/articlesearch.jsp' : './JSP/User/' + page + '.jsp');
	document.getElementById("content").InnerHTML = $('#content').load(path);
}