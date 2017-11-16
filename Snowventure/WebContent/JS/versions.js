function changeVersion(aid, page){
	var version = $('#selectedVersion').find(':selected').text();
	location.href='./'+page+'?ID='+aid+'&version='+version;
}

function customerChangeVersion(aid, page, version){
	console.log(version);
	location.href='./'+page+'?ID='+aid+'&version='+version;
}