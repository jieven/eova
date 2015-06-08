<div class="eova-file">
	<input type="text" id="${id!}" name="${name!}" style="width: 158px;"><img src="" style="width: 20px; height: 20px;">
	<input type="file" id="${id!}_file" name="${name!}_file" accept="image/*" style="left: 49px; top: 216px; width: 190px;border:1px solid red;cursor: pointer;">
</div>
<script>
$(function() {
	// init input file
	$('.eova-file').each(function() {
		var offset = $(this).find('input[type=text]').offset();
		$(this).find('input[type=file]').css({
			left : offset.left-1,
			top : offset.top-1,
			width : $(this).width()
		});
	});
	
	var $input = $('#${id!}');
	
	// 异步传图(动态绑定事件)
	$(document).on("change","#${id!}_file",function(){
		var $this = $(this);
		console.log($this.val());
		$this.siblings('input').val($this.val());
		var reader = new FileReader();
		var files = $this.prop('files');
		reader.readAsDataURL(files[0]);
		reader.onload = function(e) {
			$('.eova-file img').attr('src', e.target.result);
		}
		
		$.ajaxFileUpload({
			url : '/singleGrid/uploadImg?name=${name!}_file',
			secureuri : false,
			fileElementId : '${id!}_file',
			dataType : 'json',
			success : function(data, status) {
				// 将返回数据转换为JSON对象
				json = eval(data);
				var msg = json.msg;
				if (json.success) {
					$input.val(json.fileName);
					console.log("图片上传成功！");
				} else {
					alert(msg);
				}
			},
			error : function(data, status, e) {
				alert('网络异常，请稍后再试！');
			}
		});
	});
});
</script>