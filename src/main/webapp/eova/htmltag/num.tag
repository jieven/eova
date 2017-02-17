<div id="${id!}" name="${name!}" class="eova-num">
	<select name="condition_${name!}">
		<option selected="selected">=</option>
		<option><</option>
		<option>></option>
		<option>∩</option>
		<option>U</option>
	</select><input name="start_${name!}" placeholder=" ?" style="width: 143px">
	<span> < N < </span>
	<span> > N or N < </span>
	<input name="end_${name!}" placeholder=" ?"  style="display: none;">
</div>
<script>
$(function(){
	var spans = $('#${id!}').children('span');
	var $start = $('input[name="start_${name!}"]');
	var $end = $('input[name="end_${name!}"]');
	$('#${id!} select').change(function(){
		
		$start.val('');
		$end.val('');
		$end.hide();
		
		$.each(spans, function(){
			$(this).hide();
		});
		var s = $(this).val(),
			span;
		$start.width(30);
		if(s == '='){
// 			span = spans[0];
			$start.width(143);
		} else if(s == '<'){
// 			span = spans[1];
			$start.width(143);
		} else if(s == '>'){
// 			span = spans[2];
			$start.width(143);
		} else if(s == '∩'){
			span = spans[0];
			$end.show();
		} else if(s == 'U'){
			span = spans[1];
			$end.show();
		}
		$(span).show();
	});
});
</script>