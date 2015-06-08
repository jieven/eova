<table id="${id}" width="auto" height="auto"></table>
<script>
$(function() {
	$("#roleMenu").treegrid({
        url: '/auth/getFunJson',
        method: 'get',
        rownumbers: true,
        idField: 'id',
        treeField: 'name',
        fit: true,
        toolbar: '#toolbar',
        columns: [[
        {
            field: 'name',
            title: '菜单',
            width: '180'
        },
        ]]
    })
});
</script>