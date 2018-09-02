<%
// 查询控件name自动追加前缀
var name = item.en;
if(isTrue(isQuery!)){
	name = "query_" + name;
}
%>
<%if(item.type == "下拉框"){%>
	<div><#combo id="${item.en}" name="${name}" code="${item.object_code}" field="${item.en}" value="${value!}" multiple="${item.is_multiple}" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "下拉树"){%>
	<div><#combotree id="${item.en}" name="${name}" code="${item.object_code}" field="${item.en}" value="${value!}" multiple="${item.is_multiple}" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "查找框"){%>
    <div><#find id="${item.en}" name="${name}" code="${item.object_code}" field="${item.en}" value="${value!}" multiple="${item.is_multiple}" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "时间框"){%>
	<%if(!isEmpty(value!)){value = strutil.formatDate(value, 'yyyy-MM-dd HH:mm:ss');}%>
    <div><#time id="${item.en}" name="${name}" value="${value!}" isReadonly="${readnoly!false}" options="format:'yyyy-MM-dd HH:mm:ss'" /></div>
<%} else if(item.type == "日期框"){%>
	<%if(!isEmpty(value!)){value = strutil.formatDate(value, 'yyyy-MM-dd');}%>
    <div><#time id="${item.en}" name="${name}" value="${value!}" isReadonly="${readnoly!false}" options="format:'yyyy-MM-dd'"/></div>
<%} else if(item.type == "文本域"){%>
	<div style="margin-left: 90px;"><#texts id="${item.en}" name="${name}" value="${value!}" placeholder="${item.placeholder!}" validator="${item.validator!}" style="width:99.9%;height:${item.height!20}px;" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "编辑框"){%>
	<div style="margin-left: 90px;"><#edit id="${item.en}" name="${name}" value="${value!}" style="width: 100%;height:${item.height!250}px;" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "布尔框"){%>
	<div><#bool id="${item.en}" name="${name}" value="${value!}" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "图片框"){%>
	<div><#img id="${item.en}" name="${name}" value="${value!}" options="isReadonly : ${readnoly!false}" filedir="${item.config.filedir!}" filename="${item.config.filename!}" memo="${item.config.memo!}" height="${item.height!100}" /></div>
<%} else if(item.type == "多图框"){%>
	<div><#imgs id="${item.en}" name="${name}" value="${value!}" options="isReadonly : ${readnoly!false}" filedir="${item.config.filedir!}" meta="${item}" /></div>
<%} else if(item.type == "文件框"){%>
	<div><#file id="${item.en}" name="${name}" value="${value!}" options="isReadonly : ${readnoly!false}" filedir="${item.config.filedir!}" filename="${item.config.filename!}" /></div>
<%} else if(item.type == "图标框"){%>
    <div><#icon id="${item.en}" name="${name}" value="${value!}" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "自增框" || item.type == "文本框" || item.type == "数字框"){%>
    <div><#text id="${item.en}" name="${name}" value="${value!}" placeholder="${item.placeholder!}" validator="${item.validator!}" options="" isReadonly="${readnoly!false}" /></div>
<%} else if(item.type == "JSON框"){%>
	<div style="margin-left: 90px;"><#json id="${item.en}" name="${name}" value="${value!}" style="margin-top: 3px;width:99.9%;height:${item.height > 200 ? item.height : 200}px;" isReadonly="${readnoly!false}" /></div>
<%} else {// 默认为文本框%>
	<% var widgets = query("widget"); %>
    <%for(widget in widgets){%>
	    <%if(widget.value == item.type){%>
	    <div><%include(widget.path, {'field' : item, 'name' : name, 'value' : value!}){}%></div>
	    <%}%>
    <%}%>
<%}%>