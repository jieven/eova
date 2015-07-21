<%if(item.type == "下拉框"){%>
	<div><#combo id="${item.en}" name="${item.en}" code="${item.object_code}" field="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" /></div>
<%} else if(item.type == "查找框"){%>
    <div><#find id="${item.en}" name="${item.en}" code="${item.object_code}" field="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" /></div>
<%} else if(item.type == "时间框"){%>
    <div><#time id="${item.en}" name="${item.en}" value="${strutil.formatDate(item.value!date(), 'yyyy-MM-dd HH:mm:ss')}" isNoN="${item.is_required!}" /></div>
<%} else if(item.type == "文本域"){%>
	<div><#texts id="${item.en}" name="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" placeholder="${item.placeholder!}" validate="${item.validator!}" style="width:758px;height:30px;" /></div>
<%} else if(item.type == "编辑框"){%>
	<div><#edit id="${item.en}" name="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" style="width: 758px;height:150px;margin-bottom: 5px;float: left;" /></div>
<%} else if(item.type == "复选框"){%>
	<div><#check id="${item.en}" name="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" /></div>
<%} else if(item.type == "图片框"){%>
	<div><#img id="${item.en}" name="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" /></div>
<%} else if(item.type == "图标框"){%>
    <div><#icon id="${item.en}" name="${item.en}" value="${item.value!item.defaulter}" isNoN="${item.is_required!}" /></div>
<%} else {// 默认为文本框%>
    <div><#text id="${item.en}" name="${item.en}" value="${item.value!item.defaulter}" placeholder="${item.placeholder!}" isNoN="${item.is_required!}" validate="${item.validator!}" options="" /></div>
<%}%>