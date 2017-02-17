<script>
$(function () {

    // 初始化参数
    var $form = $("#${id!}");

    var masterId = "${masterId!}";
    var $masterGrid;
    if(masterId != ""){
    	$masterGrid = $("#" + masterId);
    }

    var objectCode = '${objectCode!}';// medaobject code
    var objectJson = '${objectJson!}';// object is json
    var fieldsJson = '${fieldsJson!}';// fiedlds is json
    var configJson = '${configJson!}';// config is json

	// 初始化组件
	EovaWidget.init(objectCode, objectJson, fieldsJson, configJson);
    var config = EovaWidget.data.config,
    	object = EovaWidget.data.object,
    	fields = EovaWidget.data.fields;


});
</script>