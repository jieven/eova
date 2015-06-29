<textarea class="eova-texts" 
id="${id!}" 
name="${(isQuery!)=='true' ? QUERY+name:name}" ${isTrue(isNoN!) ? 'required' : ''}
placeholder='${placeholder!}'  
${htmlattrs!} 
style="${style!}">${value!}</textarea>
<script>
var validator = '${validator!}';
var $texts = $('#${id!}'); 
if(validator != ''){
	var obj = eval('({'+ validator +'})');
	if(obj.min){
	    $texts.attr('data-min', obj.min);
	}
	if(obj.max){
	    $texts.attr('data-max', obj.max);
	}
}
</script>