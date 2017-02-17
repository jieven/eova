/**
 * Eova组件
 */
var EovaWidget = (function() {

	var data = {
		object: {}, // 元对象
		fields: {}, // 元字段
		config: {} // 当前功能配置
	};

	var init = function(objectCode, objectJson, fieldsJson, configJson) {
		if (objectJson && objectJson != '') {
			data.object = JSON.parse(objectJson);
		} else {
			$.syncGetJson('/meta/object/' + objectCode, function(json) {
				data.object = json;
			});
		}
		if (fieldsJson && fieldsJson != '') {
			data.fields = JSON.parse(fieldsJson);
		} else {
			$.syncGetJson('/meta/fields/' + objectCode, function(json) {
				data.fields = json;
			});
		}
		if (configJson && configJson != '') {
			data.config = JSON.parse(configJson);
		}
	}

	return {
		data: data,
		init: init
	};
})();