<form id="${id}" method="post" class="form">
	<div class="eova-form">
		<%for(item in items){%>
			<%if(item.type == "自增框"){%>
				<%// 只有编辑时显示自增框 %>
				<%if(isTrue(isUpdate!)){%>
				<div class="eova-form-field" title="${item.cn}[${item.en}]">
					<div class="eova-form-lbl">${item.cn}:</div>
					<div><div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-auto"></div></div>
                    <% // <#auto id="${item.en}" name="${item.en}" value="${item.value!}"/>%>
				</div>
				<%}%>
			<%continue;}%>
				
			<%// 该字段是否允许编辑%>
			<%if(isTrue(isUpdate!)){%>
				<%if(isTrue(item.isUpdate)){%>
					<div class="eova-form-field" title="${item.cn}[${item.en}]" ${item.type == '文本域' || item.type == '编辑框' ? 'style="width:98%"' : ''} >
					<div class="eova-form-lbl${isTrue(item.isNotNull!) ? ' red' : ''}">${item.cn}:${item.hint!}</div>
					<#formfield item="${item}" />
					</div>
				<%}%>
			<%} else {%>
				<%if(isTrue(item.isAdd)){%>
				<div class="eova-form-field" title="${item.cn}[${item.en}]" ${item.type == '文本域' || item.type == '编辑框' ? 'style="width:98%"' : ''} >
				<div class="eova-form-lbl${isTrue(item.isNotNull!) ? ' red' : ''}">${item.cn}:${item.hint!}</div>
				<#formfield item="${item}" />
				</div>
				<%}%>
			<%}%>
		<%}%>
	</div>
</form>
<script>
$('#${id}').html5Validate(function() {
	alert("全部通过！");
	//this.submit();	
}, {
	novalidate: true,
	// labelDrive: false
});
</script>