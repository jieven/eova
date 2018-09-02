<form id="${id}" name="${id}" method="post" class="form">
<div class="eova-form">
<%
		// Form Widget: 神罗天征 -> 以自己为中心将周围的一切物体弹开，以天道为中心制造斥力场，产生类似斥的能力。
		// 下面的代码，类似神罗天征，新手勿看，小心走火入魔，必须非常熟悉Beetl再看如下代码。
		// 用到Beetl各种常规语法+自定义函数+Tag
		
		// 获取分组元字段数据
		var fields = query("fields", objectCode); 
		
		var lastFieldset = "";
		var isFirstEnd = false;

		for(f in fields){
			if(!isEmpty(data!)){
				// 有数据为更新模式
				if(f.update_status < 50){		 	// 未禁用
					
					// 如果当前分组名和上一个分组名不一样，输出分组标签
					if(!isEmpty(f.fieldset) && f.fieldset != lastFieldset){
						
						// 第一个分组结束闭合标签
						if(isFirstEnd){
							print('</fieldset>');
						}
						
						isFirstEnd = true;				// 分组标签待闭合标记
						lastFieldset = f.fieldset;		// 记录当前分组名
						
						print('<fieldset style="margin: 10px;">');
						print('<legend>' + f.fieldset + '</legend>');
					}
					
					// 获取当前字段的值
					var value = @data.get(f.en);
					// 固定值覆盖当前值
					if(!isEmpty(fixed)){
						var t = @fixed.get(f.en);
						if(!isEmpty(t)) {
							value = t;
						}
					}
					var isReadOnly = false;		// 当前字段是否只读
					if(f.update_status == 10){
						isReadOnly = true;
					}
					
					// debug(value);
						
					if(f.update_status == 20){		// 隐藏字段
						%><input type="hidden" name="${f.en}" value="${value!f.defaulter}" /><%
					}else{
						%>
						<div class="eova-form-field" title="${f.cn}" style="${f.type == '文本域' || f.type == '编辑框' || f.type == '图片框' || f.type == '多图框' || f.type == 'JSON框' ? 'width:95.5%;' : ''}${f.type == '图片框' ? 'height: '+ (f.height!100 + 35) +'px;' : ''}" >
						<div class="eova-form-lbl${isTrue(f.is_required!) ? ' red' : ''}">${f.cn}</div>
						<#field item="${f}" value="${value!f.defaulter}" readnoly="${isReadOnly}" />
						</div>
						<%
					}
				}
			} else {
				// 无数据为新增模式
				if(f.add_status < 50){			// 未禁用
					
					// 如果当前分组名和上一个分组名不一样，输出分组标签
					if(!isEmpty(f.fieldset) && f.fieldset != lastFieldset){
						
						// 第一个分组结束闭合标签
						if(isFirstEnd){
							print('</fieldset>');
						}
						
						isFirstEnd = true;				// 分组标签待闭合标记
						lastFieldset = f.fieldset;		// 记录当前分组名
						
						print('<fieldset style="margin: 10px;">');
						print('<legend>' + f.fieldset + '</legend>');
					}
				
					var value = null;
					var isReadOnly = false;		// 当前字段是否只读
					if(f.add_status == 10){
						isReadOnly = true;
					}
					
					// 固定值覆盖当前值
					if(!isEmpty(fixed)){
						var t = @fixed.get(f.en);
						if(!isEmpty(t)) {
							value = t;
						}
					}
					
					if(f.add_status == 20){		// 隐藏字段
						%><input type="hidden" name="${f.en}" value="${value!f.defaulter}" /><%
					}else{
						%>
						<div class="eova-form-field" title="${f.cn}" style="${f.type == '文本域' || f.type == '编辑框' || f.type == '图片框' || f.type == '多图框' || f.type == 'JSON框' ? 'width:95.5%;' : ''}${f.type == '图片框' ? 'height: '+ (f.height!100 + 35) +'px;' : ''}" >
						<div class="eova-form-lbl${isTrue(f.is_required!) ? ' red' : ''}">${f.cn}</div>
						<#field item="${f}" value="${value!f.defaulter}" readnoly="${isReadOnly}" />
						</div>
						<%
					}
				}
			}
	
			// 最后一个分组进行闭合
			if(fLP.last && !isEmpty(f.fieldset!)){
				%></fieldset><%
			}
			
		}
%>
</div>
</form>
<script>
$(function(){
    var $form = $('#${id}');
    $form.validator({
        debug: false,
        stopOnError: true,
        focusInvalid : false,
        showOk: false,
        timely: 0,
        msgMaker: false,
        fields: {
	        <%for(f in fields){%>
	        	<%if((f.add_status == 0 || f.update_status == 0) && (isTrue(f.is_required) || !isEmpty(f.validator))){%>
	                ${f.en} : { rule: "${isTrue(f.is_required)?f.cn + ':required;' : ''}${f.validator!}" },
	            <%}%>
	        <%}%>
        }
    });
    $form.on("validation", $.validation);
});
</script>