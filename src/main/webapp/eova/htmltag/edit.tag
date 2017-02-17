<textarea id="${id!}" name="${name!}" style="${style!}">${value!}</textarea>
<style>.menu-item {overflow: visible!important;}</style>
<script type="text/javascript">
	$(function(){
		var editor = new wangEditor('${id!}');
		editor.create();
	});
</script>