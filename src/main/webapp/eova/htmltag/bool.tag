<div class="eova-bool" id="${id!}" name="${name}" data-options="${!isEmpty(options!) ? options : '' }"></div>
<script>
var val = '${value!}';
var flag = false;
if(val == '1' || val == 'true'){
	flag = true;
}
$('#${id!}').eovabool().setChecked(flag);
</script>