$(document).ready(function(){
	var $province = $('#province');
    var $city= $('#city');
    var $region= $('#region');

    // 初始禁用
    $city.mask();
    $region.mask();
    
 	// 省级联市
    $province.eovacombo({onChange: function (oldValue, newValue) {
        $city.eovacombo().setValue("");
        $region.eovacombo().setValue("");

        if (newValue == "") {
            $city.mask();
            return;
        }
        
        $city.unmask();
        var url = '/widget/comboJson?exp=select id ID,name CN from area where lv = 2 and pid = ' + newValue;
        $city.eovacombo({url : url}).reload();
    }});
    // 市级联区县
    $city.eovacombo({onChange: function (oldValue, newValue) {
        $region.eovacombo().setValue("");

        if (newValue == "") {
            $region.mask();
            return;
        }
        
        $region.unmask();
        var url = '/widget/comboJson?exp=select id ID,name CN from area where lv = 3 and pid = ' + newValue;
        $region.eovacombo({url : url}).reload();

    }});

    
    
    var $admin_province = $('#admin_province');
    var $admin_city= $('#admin_city');
    var $admin_region= $('#admin_region');

    // 初始禁用
    $admin_city.mask();
    $admin_region.mask();
    
 	// 省级联市
    $admin_province.eovacombo({onChange: function (oldValue, newValue) {
        $admin_city.eovacombo().setValue("");
        $admin_region.eovacombo().setValue("");

        if (newValue == "") {
            $admin_city.mask();
            return;
        }
        
        $admin_city.unmask();
        var url = '/widget/comboJson?exp=select id ID,name CN from area where lv = 2 and pid = ' + newValue;
        $admin_city.eovacombo({url : url}).reload();
    }});
    
    // 市级联区县
    $admin_city.eovacombo({onChange: function (oldValue, newValue) {
        $admin_region.eovacombo().setValue("");

        if (newValue == "") {
            $admin_region.mask();
            return;
        }
        
        $admin_region.unmask();
        var url = '/widget/comboJson?exp=select id ID,name CN from area where lv = 3 and pid = ' + newValue;
        $admin_region.eovacombo({url : url}).reload();

    }});
});