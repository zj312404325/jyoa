(function ($) {
    $.fn.jQSelect = function (settings) {
        // alert(settings.id);
        var id = settings.id.toString();
        var vl = settings.vl.toString();
        var $div = this;
        var $cartes = $div.find(".cartes");
        var $lists = $div.find(".lists");

        var listTxt = $cartes.find(".listTxt");
        var listVal = $cartes.find(".listVal");

        var items = $lists.find("ul > li");
        var oLi = null;
        $div.click(function (event) {
        	var isIE = !!window.ActiveXObject || "ActiveXObject" in window;
			if(!!window.ActiveXObject || "ActiveXObject" in window) {//识别ie浏览器（包括ie11）
				window.event.cancelBubble = true;
			}else{
				event.stopPropagation();
			}
            //加背景颜色
            var sval = $.trim($div.find("input[type='text']").val());
            oLi = $div.find("li");
            oLi.each(function () {
                if ($.trim($(this).text()) == sval) {
                    $(this).addClass("cgray");
                }
            });
            $(this).addClass("hover");
        });
        $(document).on("click",function () {
        	$div.removeClass("hover");
        });

        //绑定点击事件
        items.on("click",function () {
            //listVal.val($(this).attr("id"));
        	$("#" + vl + "").val($(this).attr("vl"));
            $("#" + id + "").val($(this).attr("id"));
            listTxt.val($(this).text());
            JQSelectcallback();
            oLi.removeClass("cgray false");
            $div.removeClass("hover");
            event.stopPropagation();
        }).mouseover(function () {
            var cgray = $(this).attr("class");
            //有class属性
            if (cgray) {
                //不包含
                if (cgray.indexOf("cgray") == -1) {
                    $(this).removeClass("cwhite");
                    $(this).addClass("cgray false");
                }
            } else {
                //无class属性
                $(this).removeClass("cwhite");
                $(this).addClass("cgray false");
            }
        }).mouseout(function () {
            var cgray = $(this).attr("class");
            if (cgray.indexOf("false") != -1) {
                $(this).removeClass("cgray false");
                $(this).addClass("cwhite");
            }
        });
    };
})(jQuery);
