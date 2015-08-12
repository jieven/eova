package com.eova.model;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 菜单配置
 * 
 * @author Jieven
 *
 */
public class MenuConfig {
	// 主对象Code
	private String objectCode;
	// 主对象外键字段
	private String objectField;
	// 子对象Code列表
	private List<String> objects;
	// 子对象关联字段列表
	private List<String> fields;

	public MenuConfig() {
	}

	public MenuConfig(String str) {
		JSONObject json = JSON.parseObject(str);
		this.objectCode = json.getString("objectCode");
		this.objectField = json.getString("objectField");
		this.objects = JSON.parseArray(json.getString("objects"), String.class);
		this.fields = JSON.parseArray(json.getString("fields"), String.class);
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getObjectField() {
		return objectField;
	}

	public void setObjectField(String objectField) {
		this.objectField = objectField;
	}

	public List<String> getObjects() {
		return objects;
	}

	public void setObjects(List<String> objects) {
		this.objects = objects;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
}
