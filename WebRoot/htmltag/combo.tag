<input class="easyui-combobox" id="${id!}" name="${(isQuery!)=='true' ? QUERY+name:name}" value="${value!}" style="width: 179px;" data-options="
			url: '${isEmpty(url!) ? '/widget/comboJson/'+ code +'-'+ name : url }',
            method:'get',
            valueField:'ID',
            textField:'CN',
            <%if(isTrue(multiple!)){%>
            multiple:true,
            <%}else{%>
            multiple:false,
            <%}%>
            ${isTrue(isNoN!) ? "required:true," : ""}
            editable:false
">