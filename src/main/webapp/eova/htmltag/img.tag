<%
var uploadDir = filedir;
//debug(filedir);
var imgUrl = null;
if(!isEmpty(value!)){
	if(isEmpty(uploadDir!)){
		uploadDir = "temp";
	}
	imgUrl = IMG! + uploadDir + '/' + value;
	debug(imgUrl);
}
%>
<div class="eova-img">
	<input type="text" id="${id!}" name="${name!}" value="${value!}" style="width: 158px;" readonly="readonly">
	<label for="${id!}_file"><i class="ei" title="点击选择图片"></i></label><br>
	<img src="${imgUrl!}" alt="${memo!'待上传...'}" height="${height!100}">
	<input type="file" id="${id!}_file" name="${name!}_file" style="display: none;">
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
		
		var url = '/upload/img?name=${name!}_file&filedir=${filedir}&filename=${filename!}';
		
		var $this_file = $("#${id!}_file");

		var reader = new FileReader();
		var files = $this_file.prop('files');
		reader.readAsDataURL(files[0]);
		
		reader.onload = function(e) {
			$this_file.parent().find('img').attr('alt', '上传中,请稍候...');
            $.ajaxUpload(url, "${id!}_file", "${name!}_file", function(o){
            	if (o.state == 'ok') {
            		$this_file.parent().find('img').attr('src', e.target.result);
        			$input.val(o.fileName);
        		} else {
        			$input.val("");
        			$this_file.parent().find('img').attr('alt', '请重新选择图片上传');
        			$.alert($, o.msg);
        		}
            })
		}
	
	};
	
	$(document).on("change","#${id!}_file", upload);
	
});
</script>