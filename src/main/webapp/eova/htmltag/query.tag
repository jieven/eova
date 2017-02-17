<%// 默认返回false防止浏览器自动提交表单%>
<form id="${id}" onsubmit="return false;">
	<div id="eova-query" class="eova-form">	
	<%
	
	var diyHeight = 0;
	
	// 自动计算高度
	var count = 0;

	// 获取分组元字段数据
	var fields = query("fields", objectCode); 
	for(f in fields){
		// 只输出允许查询的条件,count累计一共有多少个查询条件
		if(!isTrue(f.is_query)){
			continue;
		}
		count++;
		
		// 特殊处理，因为复选框可能独占一行(本来无一物何处惹尘埃)，如果不是跟钱过不去，还是用推荐用下拉框
		if(f.type == "复选框"){
			diyHeight = diyHeight + 25;
		}
		// 未来会通过，拖拽式控件布局，来彻底优化此处！
		
	%>
		<div class="eova-form-field" title="${f.cn}[${f.en}]">
			<div class="eova-form-lbl">${f.cn}:${f.value!}</div>
			<%if(f.type == "文本框" || f.type == "文本域" || f.type == "编辑框" || f.type == "图片框" || f.type == "文件框"){%>
				<div><#text id="${f.en}" name="query_${f.en}" /></div>
			<%}else if(f.type == "日期框" || f.type == "时间框"){%>
				<div><#times id="${f.en}" name="${f.en}" /></div>
			<%}else if(f.type == "数字框"){%>
				<div><#num id="${f.en}" name="${f.en}" /></div>
			<%}else{%>
				<#field item="${f}" isQuery="true" />
			<%}%>
		</div>
	<%}%>
	</div>
</form>
<script>
var count = ${count};
var diyHeight = ${diyHeight!0};
var $query = $('#eova-query');
var height = $.eovaAutoHeight($query, count);
$query.parent().parent().css("height", height + diyHeight + "px");

$('div[class="eova-text"]').eovatext();
$('div[class="eova-time"]').eovatime();
$('div[class="eova-combo"]').eovacombo();
$('div[class="eova-find"]').eovafind();
</script>