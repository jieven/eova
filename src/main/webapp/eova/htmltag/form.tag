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
				<%if(isTrue(item.is_update)){%>
					<div class="eova-form-field" title="${item.cn}[${item.en}]" ${item.type == '文本域' || item.type == '编辑框' ? 'style="width:98%"' : ''} >
					<div class="eova-form-lbl${isTrue(item.is_required!) ? ' red' : ''}">${item.cn}:${item.hint!}</div>
					<#formfield item="${item}" />
					</div>
				<%}%>
			<%} else {%>
				<%if(isTrue(item.is_add)){%>
				<div class="eova-form-field" title="${item.cn}[${item.en}]" ${item.type == '文本域' || item.type == '编辑框' ? 'style="width:98%"' : ''} >
				<div class="eova-form-lbl${isTrue(item.is_required!) ? ' red' : ''}">${item.cn}:${item.hint!}</div>
				<#formfield item="${item}" />
				</div>
				<%}%>
			<%}%>
		<%}%>
	</div>
</form>
<script>
$(function(){
    var formId = '${id}';
    var $form = $('#' + formId);
    $form.validator({
        debug: false,
        stopOnError: true,
        focusInvalid : false,
        showOk: false,
        timely: 0,
        msgMaker: false,
        fields: {
        <%for(item in items){%>
        <%if(isTrue(item.is_required)){%>
                ${item.en} : { rule: '${item.cn}:required;${item.validator!}' },
            <%}%>
        <%}%>
        }
    });

    $form.on("validation", function(e, current){
        // 当前字段未验证通过，Tip提示
        if(!current.isValid){
            var tip = $.tipwarn($(current.element).parent(), current.msg);
            // 开始输入销毁提示
            $(current.element).keydown(function(event){
                tip.tooltip('destroy');
                $(this).unbind("keydown");
            });
        }
    });
});
</script>