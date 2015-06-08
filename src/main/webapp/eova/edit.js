var x = $("#eova-query").width();
alert(x/295);
var x = $("#eova-query").parent().css("width", "66px");

var str = "";

var json = eval(data);
for(var i=0;i<json.length;i++){
	var x = json[i];
	console.log(x.fid+'|'+x.rid);
}