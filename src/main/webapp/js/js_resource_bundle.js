var pageName = location.pathname;
var lastIndex = pageName.lastIndexOf('/');
pageName = pageName.slice(lastIndex, pageName.length).substr(1);

$.ajax({
	async : false,
	type : "POST",
	cache : false,
	url : contextPath+'/getResourceBundleData',
	data : {
		keyInitials : 'ask.' + pageName + '.js'
	},
	success : function(data) {
		window.resourceBundle = data;
	}
});

/*$("body").on("contextmenu",function(e){
    return false;
});*/

function parameterizedResourceBundleData(resourceKey, param){
	if(param === null){
		param = new Array();
		param.push("0");
	}
	var localizedString;
	$.ajax({
		async : false,
		type : "POST",
		cache : false,
		dataType:'html',
		url : contextPath+'/getParameterizedResourceBundleData',
		data : {
			resourceKey : resourceKey,
			resourceKeyParameter : param
		},
		success : function(data) {
			localizedString = unescape(data);
		}
	});
	return localizedString;
}
