<div class="eova-check" id="${id!}" name="${(isQuery!)=='true' ? QUERY+name:name}" data-options="${!isEmpty(options!) ? options : '' }"></div>
<script>
var val = '${value!}';
var flag = false;
if(val == '1' || val == 'true'){
	flag = true;
}
$('#${id!}').eovacheck().setChecked(flag);
</script>