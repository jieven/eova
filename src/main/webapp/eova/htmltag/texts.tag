<%
// name
if(isTrue(isQuery!)){
	name = QUERY + name;
}
%>
<textarea class="eova-texts" id="${id!}" name="${name}" placeholder="${placeholder!}" ${htmlattrs!} 
style="${style!}" ${isTrue(disable!) ? 'disabled' : ''} >${value!}</textarea>