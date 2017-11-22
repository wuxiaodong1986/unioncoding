/**
 * Created by 吴晓冬 on 2017/11/4.
 */
function logout()
{
    $("#logoutForm").submit();
}

function getMainContainer(url)
{
    $.ajax({
        url: url,
        success: function(data){
            $("#mainContainer").html(data);
        },
        complete: function(){
            jQuery.getScript("/ui/amazeui/assets/js/amazeui.min.js");
            var event = document.createEvent('HTMLEvents');
            event.initEvent("load", true, true);
            window.dispatchEvent(event);
        },
        error : function() {
            alert("error");
        }
    });
}

function postMainContainer(formId)
{
    var url = $("#"+formId).attr("action");
    $.ajax({
        url: url,
        type: 'POST',
        data:$("#"+formId).serialize(),
        success: function(data){
            $("#mainContainer").html(data);
        },
        complete: function(){
            jQuery.getScript("/ui/amazeui/assets/js/amazeui.min.js");
            var event = document.createEvent('HTMLEvents');
            event.initEvent("load", true, true);
            window.dispatchEvent(event);
        },
        error : function() {
            alert("error");
        }
    });
    return false;
}

function formDownLoad(formId, url)
{
    window.location.href=url + "?" + $("#"+formId).serialize();
}

function deleteMainContainer(url,returnUrl)
{
    if (!confirm("确定要删除吗？"))
    {
        return false;
    }
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: url,
        type: 'DELETE',
        beforeSend: function(request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
        },
        dataType: "json",
        success: function(data){
            if (data.retCode == '0000')
            {
                alert(data.retMsg);
                getMainContainer(returnUrl)
            }
            else
            {
                alert(data.retMsg);
            }
        },
        error : function() {
            alert("error");
        }
    });

    return false;
}

function ajaxMainContainer(formId)
{
    var url = $("#"+formId).attr("action");
    var returnUrl = $("#"+formId).attr("returnUrl");
    $.ajax({
        url: url,
        type: 'POST',
        data:$("#"+formId).serialize(),
        dataType: "json",
        success: function(data){
            if (data.retCode == '0000')
            {
                alert(data.retMsg);
                getMainContainer(returnUrl)
            }
            else
            {
                alert(data.retMsg);
            }
        },
        error : function() {
            alert("error");
        }
    });
    return false;
}