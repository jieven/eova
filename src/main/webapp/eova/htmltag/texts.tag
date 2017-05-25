<%
if(isTrue(isReadonly!)){
    styles = styles + "background:rgba(218, 218, 218, 0.4)";
}
%>
<textarea class="eova-texts" id="${id!}" name="${name}" placeholder="${placeholder!}" ${htmlattrs!}
style="${style!}" ${isTrue(isReadonly!) ? 'disabled' : ''} >${value!}</textarea>