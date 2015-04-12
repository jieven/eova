<style>
#eova-query {
	margin-top:5px;
}
#eova-query div{
	float: left;
}
#eova-query .field{
	width: 280px;
	margin-left: 10px;
}
#eova-query .lbl{
	line-height: 25px;
	width: 90px;
}
</style>
<%var count = 0;%>
<%// 默认返回false防止浏览器自动提交表单%>
<form id="${id}" onsubmit="return false;">
	<div id="eova-query">
		<%for(item in items){%>
		
			<%// 只输出允许查询的条件,count累计一共有多少个查询条件%>
			<%if(!isTrue(item.isQuery)){continue;}%>
			<%count++;%>
			
			<div class="field" title="${item.cn}[${item.en}]">
			<div class="lbl">${item.cn}:</div>
				<%if(item.type == "下拉框"){%>0
					<%// 是否多选%>
					<%if(isTrue(item.isMultiple)){%>
						<div><#combo id="${item.en}" name="${item.en}" code="${item.objectCode}" value="${item.value!}" isQuery="true" multiple="true" /></div>
					<%}else{%>
						<div><#combo id="${item.en}" name="${item.en}" code="${item.objectCode}" value="${item.value!}" isQuery="true" /></div>
					<%}%>
				<%}else if(item.type == "查找框"){%>
					<div><#find id="${item.en}" name="${item.en}" en="${item.en}" code="${item.objectCode }" isQuery="true" /></div>
				<%}else if(item.type == "时间框"){%>
					<div><#time id="${item.en}" name="${item.en}" code="${item.objectCode }" isQuery="true" /></div>
				<%}else if(item.type == "数字框"){%>
					<div><#number id="${item.en}" name="${item.en}" code="${item.objectCode }" isQuery="true" /></div>
				<%}else if(item.type == "复选框"){%>
					<div><#check id="${item.en}" name="${item.en}" isQuery="true" /></div>
				<%}else {%>
					<%// 默认为文本框%>
					<div><#text id="${item.en}" name="${item.en}" value="${item.value!}" isQuery="true" /></div>
				<%}%>
			</div>
		<%}%>
	</div>
</form>
<script>
var x = $("#eova-query").width();
console.log(x);
var max = parseInt(x/290);
console.log('每行最多能显示：'+ max);
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
	//console.log('ys'+ ys);
	//console.log('共需：'+ zs);
	//console.log('height：'+ y);
}
</script>