/**
 * Created by 吴晓冬 on 2017/11/4.
 */
function bindMarkdown(name, divId) {
    initMarkdown(name, divId);

    $("textarea[name='"+name+"']").bind('input propertychange', function(){
        initMarkdown(name, divId);
    });

    //黏贴操作时，如果粘贴内容是图片，则上传图片
    $("textarea[name='"+name+"']").bind('paste', function(event){
        var pasteEvent = event.originalEvent;
        if (pasteEvent.clipboardData && pasteEvent.clipboardData.items)
        {
            if (pasteEvent.clipboardData.items[0].type.indexOf("image") !== -1)
            {
                var formData = new FormData();
                formData.append('file', pasteEvent.clipboardData.items[0].getAsFile());

                var csrfToken = $("meta[name='_csrf']").attr("content");
                var csrfHeader = $("meta[name='_csrf_header']").attr("content");

                $.ajax({
                    beforeSend: function(request) {
                        request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
                    },
                    type: "POST",
                    url: "/pasteImage",
                    data: formData,
                    dataType: "json",
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (data, textStatus) {
                        if (data.retCode == '0000')
                        {
                            var text = '!['+data.body.filename+']('+data.body.url+')';
                            $("textarea[name='"+name+"']").insertContent(text);
                            initMarkdown(name, divId);
                        }
                        else
                        {
                            alert(data.retMsg);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status + " 你懂的");
                    }
                });
            }
        }
    });
}

$(function() {
    /* 在textarea处插入文本--Start */
    (function($) {
        $.fn.extend({
            insertContent : function(myValue, t) {
                var $t = $(this)[0];
                if (document.selection) { // ie
                    this.focus();
                    var sel = document.selection.createRange();
                    sel.text = myValue;
                    this.focus();
                    sel.moveStart('character', -l);
                    var wee = sel.text.length;
                    if (arguments.length == 2) {
                        var l = $t.value.length;
                        sel.moveEnd("character", wee + t);
                        t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart( "character", wee - t - myValue.length);
                        sel.select();
                    }
                } else if ($t.selectionStart
                    || $t.selectionStart == '0') {
                    var startPos = $t.selectionStart;
                    var endPos = $t.selectionEnd;
                    var scrollTop = $t.scrollTop;
                    $t.value = $t.value.substring(0, startPos)
                        + myValue
                        + $t.value.substring(endPos,$t.value.length);
                    this.focus();
                    $t.selectionStart = startPos + myValue.length;
                    $t.selectionEnd = startPos + myValue.length;
                    $t.scrollTop = scrollTop;
                    if (arguments.length == 2) {
                        $t.setSelectionRange(startPos - t,
                            $t.selectionEnd + t);
                        this.focus();
                    }
                } else {
                    this.value += myValue;
                    this.focus();
                }
            }
        })
    })(jQuery);
    /* 在textarea处插入文本--Ending */
});

function initMarkdown(name, divId) {
    var text = $("textarea[name='"+name+"']").val();
    //创建实例
    var converter = new showdown.Converter();
    //进行转换
    var html = converter.makeHtml(text);
    $("#"+divId).html(html);
    $("#"+divId).height($("#"+divId).next().height());
}