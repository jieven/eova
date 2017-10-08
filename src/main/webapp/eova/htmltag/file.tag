<div class="eova-file">
	<input type="text" id="${id!}" name="${name!}" value="${value!}" style="width: 158px;" readonly="readonly">
	<label for="${id!}_file"><i class="ei" title="点击选择文件"></i></label><input type="file" id="${id!}_file" name="${name!}_file" style="display: none;">
</div>

<script>
$(function() {
	var $input = $('#${id!}');
	
	var htmlOptions = eval('({${options!}})');
	if (htmlOptions.isReadonly) {
        $input.parent().mask();
        return;
    }
	
	// init input file
	$('.eova-file').each(function() {
		var offset = $(this).find('input[type=text]').offset();
		$(this).find('input[type=file]').css({
			left : offset.left-1,
			// top : offset.top-1,
			width : $(this).width()
		});
	});
	
	var upload = function(){
		
		var url = '/upload/file?name=${name!}_file&filedir=${filedir}&filename=${filename!}';
		
		var $this_file = $("#${id!}_file");
		var reader = new FileReader();
		var files = $this_file.prop('files');
		reader.readAsDataURL(files[0]);
		
		reader.onload = function(e) {
			$.ajaxUpload(url, "${id!}_file", "${name!}_file", function(o){
            	if (o.state == 'ok') {
        			$input.val(o.fileName);
        		} else {
        			$input.val("");
        			$.alert($, o.msg);
        		}
            })
		}
	};
	
	$(document).on("change","#${id!}_file", upload);
	
});
</script>