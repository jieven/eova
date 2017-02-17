<%var count = 0;%>
<%// 默认返回false防止浏览器自动提交表单%>
<form id="${id}" onsubmit="return false;">
	<div id="eova-query" class="eova-form">
		<%for(f in items){%>
		
			<%// 只输出允许查询的条件,count累计一共有多少个查询条件%>
			<%if(!isTrue(f.is_query)){continue;}%>
			<%count++;%>
			
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
var x = $("#eova-query").width();
// console.log(x);
var max = parseInt(x/290);
// console.log('每行最多能显示：'+ max);
if(max != 0){
	var count = ${count};
	var zs = parseInt(count / max); 
	var ys = count % max;
	if(ys > 0){
		zs++;
	}
	// 计算完整显示所需高度
	var y = 40 + 25 * zs;
	$("#eova-query").parent().parent().css("height", y + "px");
}

$('div[class="eova-text"]').eovatext();
$('div[class="eova-time"]').eovatime();
$('div[class="eova-combo"]').eovacombo();
$('div[class="eova-find"]').eovafind();
</script>