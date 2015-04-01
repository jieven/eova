<span class="combo" style="width: 177px; height: 20px;" onclick="eova_iconDialog($('#${id!}'), $('#${id!}_span'));"><input id="${id!}" name="${isTrue(isQuery!)?QUERY+name:name}" value="${value!}" readonly="readonly" type="text" class="combo-text${isTrue(isNoN!) ? ' easyui-validatebox' : ''}" autocomplete="off" style="cursor: pointer; width: 155px; height: 20px; line-height: 20px; "><span><span id="${id!}_span" onmouseover="eova_onMouseOverBg($(this))" onmouseout="eova_onMouseOutBg($(this))" class="combo-arrow ext-icon-picture_empty" style="height: 20px; "></span></span></span>
<script>
<%// 初始化显示%>
var val = '${value!}';
if(val != ''){
	$('#${id!}_span').removeClass();
	$('#${id!}_span').addClass("combo-arrow").addClass(val);
}
</script>