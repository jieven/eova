<table id="${id}" width="auto" height="auto"
			data-options="
					fit : true,
					border : false,
					striped : true,
					singleSelect : ${obj.isSingleSelect!true},
					align : 'right',
					collapsible : false,
					remoteSort : true,
					multiSort : false,
					rownumbers : true,
					pagination : true,
					<%if(isEmpty(url)){%>
					<%// 默认模式%>
					url : '/widget/gridJson/${obj.code}',
					<%}else{%>
					<%// 自定义URL%>
					url : '${url}',						
					<%}%>
					method : 'post',
					<%//title : '${obj.title}',%>
					idField : '${obj.pkName}',
					<%// 默认主键排序%>
					<%if(obj.isDefaultPkDesc){%>
					sortName : '${obj.pkName}',
					sortOrder : 'desc',
					<%}%>
					pageSize : 15,
					pageList : [ 15, 30, 50, 100, 200, 500, 1500, 2000],
					<%//frozenColumns : [[{field:'nickName',title:'昵称',width:80}]],%>
					toolbar : '#toolbar'
				">
			<thead>
				<tr>
				<%if(obj.isSingleSelect == false){%>
					<th data-options="field:'ck',checkbox:true,"></th>
				<%}%>
				
				</tr>
			</thead>
		</table>
<script>

	$.extend($.fn.datagrid.methods, {
	    editCell: function(jq,param){
	        return jq.each(function(){
	            var opts = $(this).datagrid('options');
	            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid('getColumnOption', fields[i]);
	                col.editor1 = col.editor;
	                if (fields[i] != param.field){
	                    col.editor = null;
	                }
	            }
	            $(this).datagrid('beginEdit', param.index);
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid('getColumnOption', fields[i]);
	                col.editor = col.editor1;
	            }
	        });
	    }
	});

	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){return true}
	    if ($("#${id}").datagrid('validateRow', editIndex)){
	        $("#${id}").datagrid('endEdit', editIndex);
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}
	function onClickCell(index, field){
		console.log('onClickCell');
	    if (endEditing()){
	        $("#${id}").datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
	        editIndex = index;
	        console.log('hehe....');
	    }
	    console.log('onClickCelled');
	}
	
	$(function(){
		$("#${id}").datagrid({
			columns:[[
				<%if(obj.isSingleSelect == false){%>
					{field:'ck',checkbox:true,},
				<%}%>
				<%for(item in items){%>
					<%if(item.isShow!true){%>
						{field:'${item.en}',
						title:'${item.cn}',
						editor:'text',
						width:${isEmpty(item.width!)?"150":item.width},
						<%if(item.isOrder!true){%>
							sortable:true,
						<%}%>
						<%if(item.type == '复选框'){%>
							align:'center',
							formatter:function(value, row, index, field){
								var flag = "";
								if(value == "1"){
									flag = "checked='true'";
								}
							 	var s = '<input type="checkbox" '+ flag +' disabled="true" >';  
							 	return s;
							}
						<%} else {%>
							formatter:function(value, row, index, field){
								if(value.length > 10){
									return '<span title="' + value + '">' + value + '</span>';
								}
							 	return value;
							}
						<%}%>
						},
					<%}%>
				<%}%>
			]]
		})
	})
</script>