<form id="${id}" method="post" class="form">
	<table class="table" style="width: 100%;">
		<%for(item in items){%>
			<%if(item.type == "自增框"){%>
				<%// 只有编辑时显示自增框 %>
				<%if(isTrue(isUpdate!)){%>
				<tr>
					<th title="${item.en}">${item.cn}</th>
					<td><#auto id="${item.en}" name="${item.en}" value="${item.value!}"/></td>
				</tr>
				<%}%>
			<%continue;}%>
			
			<%// 该字段是否允许编辑%>
			<%if(isTrue(isUpdate!)){%>
				<%if(isTrue(item.isUpdate)){%>
				<tr>
					<th title="${item.en}">${item.cn}</th>
					<td>
					<%if(item.type == "下拉框"){%>
						<#combo id="${item.en}" name="${item.en}" code="${item.objectCode}" value="${item.value!}" isNoN="${item.isNotNull!}" multiple="${item.isMultiple!}" />
					<%} else if(item.type == "查找框"){%>
						<#find id="${item.en}" name="${item.en}" code="${item.objectCode}" en="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />
					<%} else if(item.type == "时间框"){%>
						<#time id="${item.en}" name="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />
					<%} else if(item.type == "文本域"){%>
						<#texts id="${item.en}" name="${item.en}" value="${item.value!}" style="width:471px;height:80px" />
					<%} else if(item.type == "编辑框"){%>
						<#edit id="${item.en}" name="${item.en}" value="${item.value!}" style="width:550px;height:100px" />
					<%} else if(item.type == "复选框"){%>
						<#check id="${item.en}" name="${item.en}" value="${item.value!}" />
					<%}else if(item.type == "图标框"){%>
						<#icon id="${item.en}" name="${item.en}" value="${item.value!}" />
					<%} else {// 默认为文本框%>
						<#text id="${item.en}" name="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />
					<%}%>
					</td>
				</tr>
				<%}%>
			<%} else {%>
				<%if(isTrue(item.isAdd)){%>
				<tr>
					<th title="${item.en}">${item.cn}</th>
					<td>
					<%if(item.type == "下拉框"){%>
						<#combo id="${item.en}" name="${item.en}" code="${item.objectCode}" value="${item.value!}" isNoN="${item.isNotNull!}" multiple="${item.isMultiple!}" />
					<%} else if(item.type == "查找框"){%>
						<#find id="${item.en}" name="${item.en}" code="${item.objectCode}" en="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />
					<%} else if(item.type == "时间框"){%>
						<#time id="${item.en}" name="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}"/>
					<%} else if(item.type == "文本域"){%>
						<#texts id="${item.en}" name="${item.en}" value="${item.value!}" style="width:471px;height:80px" />
					<%} else if(item.type == "编辑框"){%>
						<#edit id="${item.en}" name="${item.en}" value="${item.value!}" style="width:550px;height:100px;" />
					<%} else if(item.type == "复选框"){%>
						<#check id="${item.en}" name="${item.en}" value="${item.value!}" />
					<%}else if(item.type == "图标框"){%>
						<#icon id="${item.en}" name="${item.en}" value="${item.value!}" />
					<%} else {// 默认为文本框%>
						<#text id="${item.en}" name="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />
					<%}%>
					</td>
				</tr>
				<%}%>
			<%}%>
		<%}%>
	</table>
</form>