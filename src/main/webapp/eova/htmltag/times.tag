<div class="eova-times">
<input id="start_${id}" name="start_${name}" style="width: 70px;" type="text" onFocus="var end=$dp.$('end_${id}');WdatePicker({onpicked:function(){end.focus();},maxDate:'#F{$dp.$D(\'end_${id}\')}'})"/>
<span class="eova-times-to">-</span>
<input id="end_${id}" name="end_${name}" style="width: 70px;" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_${id}\')}'})"/>
<i class="eova-times-icon" title="点击文本框选择时间"></i>
</div>