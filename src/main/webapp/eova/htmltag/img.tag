<%
var uploadDir = filedir;
//debug(filedir);
var imgUrl = "";
if(!isEmpty(value!)){
	if(isEmpty(uploadDir!)){
		uploadDir = "temp";
	}
	imgUrl = IMG + uploadDir + '/' + value;
	debug(imgUrl);
}
%>
<div class="eova-img">
	<input type="text" id="${id!}" name="${name!}" value="${value!}" style="width: 158px;" readonly="readonly">
	<label for="${id!}_file"><i class="ei" title="点击选择图片"></i></label><br><img src="${imgUrl!}" style="height: 50px;max-width: 179px;" alt="请选择一张图片"><input type="file" id="${id!}_file" name="${name!}_file" style="display: none;">
</div>
<script>

$(function() {
	var $input = $('#${id!}');
	
	var htmlOptions = eval('({${options!}})');
	if (htmlOptions.disable) {
        // 灰色遮罩实现禁用
        $input.parent().mask();
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
	
	// 异步传图(动态绑定事件)
	var myfun = function(){
		
		var $this_file = $("#${id!}_file");
		console.log($this_file.val());
		$input.val($this_file.val());
		var reader = new FileReader();
		var files = $this_file.prop('files');
		reader.readAsDataURL(files[0]);
		reader.onload = function(e) {
			$this_file.parent().find('img').attr('src', e.target.result);
		}
	
		$input.val("Loading...");
		
		$('#${id!}_file').upload({
			action: '/upload/img?name=${name!}_file&filedir=${filedir}',
			name: "${name!}_file",
			onsuccess: function(json) {
				$input.val(json.fileName);
			}
		});
	};
	
	$(document).on("change","#${id!}_file", myfun);
	
});
</script>