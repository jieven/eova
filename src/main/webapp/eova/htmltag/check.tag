<div class="eova-check" id="${id!}" name="${(isQuery!)=='true' ? QUERY+name:name}" value="${value!}"></div>
<script>
var val = '${value!}';
var flag = false;
if(val == '1' || val == 'true'){
	flag = true;
}
$('#${id!}').eovacheck().setChecked(flag);
</script>