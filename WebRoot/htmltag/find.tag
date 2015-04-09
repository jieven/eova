<%
// 自定义表达式
var findUrl = "";
if(!isEmpty(url!)){
	findUrl = url;
} else {
	findUrl = "/widget/find?";
	// 存在自定义表达式
	if(!isEmpty(exp)){
		findUrl = findUrl + "exp=" + exp; 
	} else {
		findUrl = findUrl + "code="+ code + "&en=" + en;
	}
}
// 将URL作为属性放置于值所在的隐藏文本框上，方面级联时JS修改URL
%>
<span class="combo" style="width: 177px; height: 20px;" onclick="eova_findDialog($('#${id!}_val'), $('#${id!}'), $('#${id!}_val').attr('url') )">
<input id="${id!}" name="${isTrue(isQuery!) ? QUERY+name:name}_cn" value="${value!}" type="text" readonly="readonly" class="combo-text${isTrue(isNoN!) ? ' easyui-validatebox' : ''}" data-options="${isTrue(isNoN!) ? 'required:true' : ''}" autocomplete="off" style="cursor: pointer; width: 155px; height: 20px; line-height: 20px; "><span><span title="点击查找数据" onmouseover="eova_onMouseOverBg($(this))" onmouseout="eova_onMouseOutBg($(this))" class="combo-arrow ext-icon-zoom" style="height: 20px; "></span></span></span>
<input type="hidden" id="${id!}_val" name="${isTrue(isQuery!) ? QUERY+name:name}" value="${value!}" url="${findUrl}"/>
<script>
<%// 初始化翻译，如果存在value则根据表达式翻译成对应的CN %>
var val = '${value!}';
if(val != ''){
	var url = '/widget/findCnByEn/${code!}-${en!}-${value!}';
	$.ajax({
		url : url,
		dataType : 'text',
		success : function(o) {
			$('#${id!}').val(o);
		}
	});
}
</script>