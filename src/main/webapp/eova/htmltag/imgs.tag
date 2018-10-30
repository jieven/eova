<%
var uploadDir = filedir;
var imgUrl = null;
if(isEmpty(uploadDir!)){
	uploadDir = "temp";
}
imgUrl = IMG! + uploadDir + '/';
%>
<div id="${name!}_file" style="margin-left: 90px;" class="eova-imgs"></div>
<input type="hidden" id="${name!}" name="${name!}" value="${value!}" style="width:90%;">

<script>
$(function() {

	var $input = $('#${name!}');
	var val = $input.val();
	var vals = [];
	
	// 初始化数据
	$.each(val.split(','), function (index, o) {
		if(!$.isNull(o)){
			vals.push(o)
		}
	});
	
	// 初始化插件
	var $zyUpload = $("#${name!}_file").zyUpload({
		
		urlPrefix : '${imgUrl!}', // 图片服务域名+图片目录
		successImgs : '${value!}'.split(','), // 已经上传好的图片(用于回显)
		
		itemWidth : "${meta.config.width!150}px", // 图片项的宽度
		itemHeight : "${meta.config.height!200}px", // 图片项的高度
		url : "/upload/img?filedir=${filedir}", // 上传图片的路径
		
		width : "auto", // 控件宽度
		height : "auto", // 控件高度
		multiple : true, // 是否可以多个图片上传
		dragDrop : true, // 是否可以拖动上传图片
		del : true, // 是否可以删除图片
		finishDel : false, // 是否在上传图片完成后删除预览
		
		onSelect : function(files, allFiles) {},
		onDelete : function(file, surplusFiles) {
				if(!file.newName){
					return;
				}
				// 删除图片
				for (var i = 0; i < vals.length; i++) {
					if (vals[i] == file.newName) {
						vals.splice(i, 1);
						break;
					}
				}
				// 更新值
				$input.val(vals.join(','));
		},
		onSuccess : function(file, json) {
			vals.push(json.fileName);
		},
		onFailure : function(file) {
			console.info("图片上传失败：");
			console.info(file);
		},
		onComplete : function(responseInfo) {
			$input.val(vals.join(','));
		}
	});
});
</script>