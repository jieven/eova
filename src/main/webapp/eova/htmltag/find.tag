<%
// 默认URL
var findUrl = "";
if(!isEmpty(code) && !isEmpty(field)){
	findUrl = "/widget/find?code=" + code +"&field="+ field;
}
if(!isEmpty(url!)){
	// 自定义URL
	findUrl = url;
} else {
	// 自定义表达式
	findUrl = "/widget/find?";
	// 存在自定义表达式
	if(!isEmpty(exp)){
		findUrl = findUrl + "exp=" + exp; 
	} else {
		findUrl = findUrl + "code="+ code + "&field=" + field;
	}
}
// 将URL作为属性放置于值所在的隐藏文本框上，方面级联时JS修改URL
%>
<div id="${id!}" name="${(isQuery!)=='true' ? QUERY+name:name}" value="${value!}" code="${code!}" field="${field!}" class="eova-find" url="${findUrl!}"
data-options="required : ${isNoN!false} ${!isEmpty(options!) ? ', ' + options : '' }"></div>