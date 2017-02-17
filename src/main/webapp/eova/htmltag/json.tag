<div id="${id!}" style="${style!}">
</div>
<input type="hidden" name="${name!}" value='${value!}' id="hidden_${id!}" />
<script type="text/javascript">
$(function(){
	var datason = '${value!}';
    if(datason != '') {
        datason = JSON.parse(datason);
    }
    var ${id!} = document.getElementById('${id!}');
    var options = {
        mode:'code',//默认
        modes: ['code', 'form', 'text', 'tree', 'view'], // 控件加载全部json视图模块
        onError: function (err) {
            alert("jsoneditor:"+err.toString());
        },
        //mode变更样式重新覆盖
        onModeChange: function (newMode, oldMode) {
//             _csshack();
        },
        onChange:function(){
            var txt=editor.getText();
            $("#hidden_${id!}").val($.jsonformat(txt,true));
        }
    };
    var editor = new JSONEditor(${id!}, options,datason);

//     _csshack();
//     /**
//      * 与eova样式冲突 手工解决
//      * 说明:
//      * common.css line 40-44 的.form th, .form td样式 与editor冲突
//      * //TODO 临时解决 待优化
//      */
//     function _csshack(){
//         $("#${id!}").find("table").find("th,td").css("border",0);
//     }
});
</script>
