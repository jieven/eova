<%
// data-options
var data = "";
if(!isEmpty(options!)){
	data = data + options;
}
if(!isEmpty(placeholder!)){
	data = data + ", placeholder : '" + placeholder + "'";
}
if(!isEmpty(isReadonly!)){
	data = data +  ", isReadonly : " + isReadonly ;
}
if(!isEmpty(multiple!)){
	data = data +  ", multiple : " + multiple ;
}
if(strutil.startWith(data, ",")){
	data = strutil.subString(data,1);
}

// 默认URL
var queryUrl = "";
// 编码 或 表达式 必须存在其一
if(!isEmpty(code) || !isEmpty(exp)){
	// 存在自定义URL
	if(!isEmpty(url!)){
		// 自定义URL
		queryUrl = url;
	} else {
		queryUrl = "/widget/comboTreeJson";
		if(!isEmpty(code) && !isEmpty(field)){
			// 存在编码和字段
			queryUrl = queryUrl +"/"+  code +"-"+ field;
		} else if(!isEmpty(exp)){
			// 存在自定义表达式
			queryUrl = queryUrl + "?exp=" + exp; 
		}
	}
}

%>
<div class="eova-combotree" id="${id!}" name="${name!}" value="${value!}" url="${queryUrl!}" data-options="${data}"></div>