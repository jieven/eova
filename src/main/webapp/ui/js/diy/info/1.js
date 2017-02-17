$(document).ready(function() {
	
	$(document.body).append('<div id="diy_info"></div>');
	$('#diy_info').dialog({
		title : '小伊提示',
		width : 600,
		height : 400,
		closed : false,
		cache : false,
		modal : true,
		content : '本弹窗由 元对象的 DIY_JS 控制，对应的JS文件为:/ui/js/diy/info/1.js'
	});
	
});