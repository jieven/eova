<%
var styles = "border: 1px solid #95B8E7;";
if(isTrue(isReadonly!)){
	styles = styles + "background:rgba(218, 218, 218, 0.4)";
}
%>
<textarea class="eova-texts" id="${id!}" name="${name}" placeholder="${placeholder!}" ${htmlattrs!} 
style="${styles};${style!}" ${isTrue(isReadonly!) ? 'disabled' : ''} >${value!}</textarea>