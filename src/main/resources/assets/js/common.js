/**
 * 封装常用js方法
 *
 * @author WJK
 * @version:1.1 2014-12
 */
// 屏蔽鼠标右键
document.oncontextmenu = function () {
    return false;
};

/** 处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外 */
function banBackSpace(e) {
    var ev = e || window.event;// 获取event对象
    var obj = ev.target || ev.srcElement;// 获取事件源
    var t = obj.type || obj.getAttribute('type');// 获取事件源类型
    // 获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly || obj.getAttribute('readOnly');
    var vEnabled = obj.enabled || obj.getAttribute('enabled');
    // 处理null值情况
    vReadOnly = (vReadOnly == null) ? false : vReadOnly;
    vEnabled = (vEnabled == null) ? true : vEnabled;
    // 当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    // 并且readonly属性为true或readonly为readonly(IE10)或enabled属性为false的，则退格键失效
    var flag1 = (ev.keyCode == 8
        && (t == "password" || t == "text" || t == "textarea"||t == "number" || t == "email" || t == "tel") && (vReadOnly == true
            || vReadOnly == 'readonly' || vEnabled != true)) ? true : false;

    // 当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2 = (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" && t != "number" && t != "email" && t != "tel") ? true
        : false;

    // 判断
    if (flag2) {
        return false;
    }
    if (flag1) {
        return false;
    }
}

// 禁止后退键 作用于Firefox、Opera
document.onkeypress = banBackSpace;
// 禁止后退键 作用于IE、Chrome
document.onkeydown = banBackSpace;

/** 打开一个最大化的窗口并关闭当前窗口 */
function openMaxWindow(url) {
    // 注意IE7以后的浏览器需要把站点设置为可信任才能隐藏地址栏
    windowHandle = window
        .open(
            url,
            '',
            'resizable=yes,menubar=no,status=yes,toolbar=no,scrollbars=yes,location=no,directories=0');
    windowHandle.moveTo(0, 0);
    windowHandle.resizeTo(screen.availWidth, screen.availHeight);

    window.opener = null;
    window.open('', '_self');
    //self.close();
}

/** 打开新的一个标签页 */
function openPage(url) {
    window.open(url);
}

/** json对象复制 */
function cloneJson(jsonObj) {
    var buf;
    if (jsonObj instanceof Array) {
        buf = [];
        var i = jsonObj.length;
        while (i--) {
            buf[i] = clone(jsonObj[i]);
        }
        return buf;
    } else if (jsonObj instanceof Object) {
        buf = {};
        for (var k in jsonObj) {
            buf[k] = clone(jsonObj[k]);
        }
        return buf;
    } else {
        return jsonObj;
    }
}

/** js对象复制 */
function cloneObject(srcObj, destObj) {
    for (var property in srcObj) {
        var copy = srcObj[property];
        if (destObj == copy)
            continue;
        if (typeof copy == "object") {
            destObj[property] = cloneObject(destObj[property] || {}, copy);
        } else {
            destObj[property] = copy;
        }
    }
    return destObj;
}

// cookie
/** 创建cookie */
function createCookie(name, value, days) {
    var expires;
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toGMTString();
    } else
        expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}

/** 读取cookie */
function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0)
            return c.substring(nameEQ.length, c.length);
    }
    return null;
}

/** 删除cookie */
function eraseCookie(name) {
    createCookie(name, "", -1);
}

/** 判断字符串是否为空 */
function isEmpty(str) {
    if (typeof str === "boolean") {
        return false;
    }
    if (typeof str === "number") {
        return false;
    }
    if (str == "null" || !str) {
        return true;
    }
    if (str.match(/^\s*$/) || str.length == 0) {
        return true;
    }
    return false;
}

function isNotEmpty(str) {
    if (typeof str === "boolean") {
        return true;
    }
    if (typeof str === "number") {
        return true;
    }
    if (!str || str == 'null') {
        return false;
    }
    if (str.match(/^\s*$/) || str.length == 0) {
        return false;
    }
    return true;
}

// 用法:openBlank('/member/succeed.html',{id:'6',describe:'添加控制器,
// 包括前台与后台',money:$('.money:first').text()});
function openBlank(action, data, n) {
    var form = $("<form/>").attr('action', action).attr('method', 'post');
    if (n)
        form.attr('target', '_blank');
    var input = '';
    $.each(data, function (i, n) {
        input += '<input type="hidden" name="' + i + '" value="' + n + '" />';
    });
    form.append(input).appendTo("body").css('display', 'none').submit();
}

/**
 * jquery ajax请求封装
 *
 * @param opts
 */
function doPost(opts) {
    opts.type = "POST";
    doAjax(opts);
}

function doGet(opts) {
    opts.type = "GET";
    doAjax(opts);
}

/**
 *
 * @param opts modal:是否加载模态框
 * @returns
 */
function doAjax(opts) {
    // 处理参数，如果参数为null,就剔除
    for (var key in opts.data) {
        if (opts.data[key] == null) {
            delete opts.data[key];
        }
        if (opts.data[key] instanceof Array) {//如果是数组则转化为json数组字符串
            opts.data[key] = JSON.stringify(opts.data[key]);
        }
    }
    if (typeof (opts.modal) == 'undefined') {//默认打开模态框
        opts.modal = true;
    }
    var index = null;
    if (opts.modal) {
        index = layer.load(0);
    }
    opts.successCP = opts.success;
    var success = function (result) {
        if (result.status == 200) {// 成功
            var suc = opts.successCP;
            suc(result.data);
        } else if (result.status == 201 || result.status == 202) {// TOKEN失效,没有权限
            showErrorMsg(result.msg);
            window.location.href = window.location.href;
        } else {
            console.log(result.msg);
            showErrorMsg(result.msg);
        }
        if (opts.modal) {
            layer.close(index);
        }
    };

    var error = function (jqXHR, textStatus, errorThrown) {
        /*错误信息处理*/
        showErrorMsg(jqXHR.responseJSON.msg);
        if (opts.modal) {
             layer.close(index);
        }
    }

    opts.success = success;
    opts.error = error;
    $.ajax(opts);
}

/**
 * 显示错误信息
 *
 * @param msg
 */
function showErrorMsg(msg) {
    window.top.toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
    window.top.toastr.error(msg);
}

/**
 * 显示成功信息
 *
 * @param msg
 */
function showSuccessMsg(msg) {
    window.top.toastr.options = {
        "closeButton": false,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "2000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    window.top.toastr.success(msg);
}

/**
 * 显示提示信息
 *
 * @param msg
 */
function showWarningMsg(msg) {
    window.top.toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "onclick": null,
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    window.top.toastr.warning(msg);
}
/** *************确认并发送post请求************ */
function confirmAndPost(opts) {
    var msg = (opts.msg ? opts.msg : "确认提交?");
    opts.method = "POST";
    layer.confirm(msg, {
        btn: ['确定', '取消'], //按钮
        shade: 0.3 //显示遮罩
    }, function (result) {
        doPost(opts);
        layer.close(result);
    }, function (result) {

    });
}

/** *************确认并发送get请求************ */
function confirmAndGet(opts) {
    var msg = (opts.msg ? opts.msg : "确认提交?");
    opts.method = "GET";
    layer.confirm(msg, {
        btn: ['确定', '取消'], //按钮
        shade: 0.3 //显示遮罩
    }, function (result) {
        doGet(opts);
        layer.close(result);
    }, function (result) {

    });
}

/**
 *layer关闭自身
 */
function closeSelf() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/** 表单清空 */
function clearForm(id) {
    $(':input', '#' + id).not(':button, :submit, :reset, :hidden').val('')
        .removeAttr('checked').removeAttr('selected');
    /**
     * 去除chosen框的值
     */
    $('#' + id + ' .chosen-select').chosen("destroy");
    $('#' + id + ' .chosen-select').val("");
    $('#' + id + ' .chosen-select').chosen({
        allow_single_deselect: true
    });
    if(start != null){
        start.min = '1900-01-01 00:00:00';
        start.max = '2100-01-01 00:00:00'
    }
    if(end != null){
        end.min = '1900-01-01 00:00:00';
        end.max = '2100-01-01 00:00:00'
    }
}

/**
 * 修改chosen选择框的选择的值
 * @param $dom chosen 选择框jquery对象
 * @param value 要设置的值
 */
function updateChosen($dom, value) {
    $dom.chosen("destroy");
    $dom.val(value);
    $dom.chosen({
        allow_single_deselect: true
    });
}

/** 含隐藏域清空 */
function clearFullForm(id) {
    /**
     * 还原到最初的select
     */
    $('#' + id + ' .chosen-select').chosen("destroy");
    $(':input', '#' + id).not(':button, :submit, :reset').val('').removeAttr(
        'checked').removeAttr('selected');
    $('#' + id + ' .chosen-select').chosen({
        allow_single_deselect: true
    });
    if(start != null){
        start.min = '1900-01-01 00:00:00';
        start.max = '2100-01-01 00:00:00'
    }
    if(end != null){
        end.min = '1900-01-01 00:00:00';
        end.max = '2100-01-01 00:00:00'
    }
}

/**
 * 区域取值。区域内所有包含name属性的取值。 flag:是否取隐藏值,ture:取，false：不取。默认为true
 */
function getAreaVal(areaId, flag) {
    if (isEmpty(areaId)) {
        showErrorMsg("getAreaVal需要dom id！");
        return;
    }
    var doms;
    if (flag == false) {
        doms = $("#" + areaId).find("*").not(':hidden');
    } else {
        doms = $("#" + areaId).find("*");
    }
    var result = {};
    doms.each(function (index, dom) {
        if (dom.name) {
            if (isNotEmpty(dom.name) && dom.value != null) {
                if (dom.nodeName == "SELECT" || dom.nodeName == "INPUT") {
                    if ($(dom).hasClass("ace-switch")) {//是否使用ace-switch
                        result[dom.name] = dom.checked;
                    } else {
                        result[dom.name] = dom.value;
                    }
                } else {
                    result[dom.name] = dom.value;
                }
            }
        }
    });
    return result;
}

/**
 * 区域设值 flag:是否根据name设置，true：是，false：否。默认为true
 */
function setAreaVal(areaId, data, flag) {
    if (isEmpty(areaId) || !data || data == null) {
        showErrorMsg("setAreaVal需要dom id及设值数据！");
        return;
    }
    //选择出所有的input标签元素
    $(':input', '#' + areaId).each(function (index, dom) {
        for (var attr in data) {
            if (dom.name == attr) {
                if (dom.nodeName == "INPUT") {
                    if ($(dom).hasClass("ace-switch")) {//是否使用ace-switch
                        if (isNotEmpty(data[attr])) {
                            dom.checked = data[attr];
                        }
                    } else {
                        dom.value = data[attr];
                    }
                } else if (dom.nodeName == "SELECT") {
                    if ($(dom).hasClass("chosen-select")) {//是否使用choosen插件
                        updateChosen($(dom), data[attr]);
                    } else {
                        dom.value = data[attr];
                    }
                } else {
                    dom.value = data[attr];
                }
            } else if (flag == false) {
                // do nothing
            }
        }
    });
    $("#" + areaId).find("*").not(':input').each(function (index, dom) {
        for (var attr in data) {
            if (dom.name == attr) {
                dom.innerHTML = data[attr];
            } else if (flag == false) {
                // do nothing
            }
        }
    });
}


/**
 * 判断是否为json对象
 *
 * @param obj
 * @returns {Boolean}
 */
function isJson(obj) {
    var isjson = (typeof obj == "object");
    return isjson;
}

String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
}
/**
 * 以特殊符号分割array
 *
 */
Array.prototype.split = function (seperator) {
    var str = "";
    for (var i = 0; i < this.length; i++) {
        str += this[i] + ",";
    }
    return str.substr(0, str.length - 1);
}


/**
 * layer打开一个窗口，
 * @param title 标题
 * @param width 宽 px 或者百分比
 * @param height 高 px 或者百分比
 * @param url 相对路径或者绝对路径
 * @param func 关闭窗口回调函数
 */
function openDialog(title, width, height, url, func) {
    window.top.layer.open({
        type: 2,
        title: !title ? false : title,
        shadeClose: false,
        shade: 0.3,
        maxmin: true, //开启最大化最小化按钮
        area: [width, height],
        content: url,
        cancel: function (index) {
            //如果是取消操作直接不需要回调
            func = function () {
            };
            return true;
        },
        end: function () {
            if (func) {
                func();
            }
        }
    });
}

/**
 * 当前iframe下打开一个最大窗口，
 * @param title 标题
 * @param url 相对路径或者绝对路径
 * @param func 关闭窗口回调函数
 */
function openCurrentMaxDialog(title, url, func) {
    layer.open({
        type: 2,
        title: !title ? false : title,
        shadeClose: false,
        shade: 0.3,
        maxmin: true, //开启最大化最小化按钮
        area: ['100%', '100%'],
        content: url,
        cancel: function (index) {
            //如果是取消操作直接不需要回调
            func = function () {
            };
            return true;
        },
        end: function () {
            if (func) {
                func();
            }
        }
    });
}


/**
 * layer打开相册，
 * @param photoStr 逗号隔开的相册地址
 */
function openPhoto(photoStr) {
    var photos = null;
    if (photoStr instanceof Array) {
        photos = photoStr;
    } else {
        photos = photoStr.split(',');
    }
    var json = {
        "title": "", //相册标题
        "id": 123, //相册id
        "start": 0 //初始显示的图片序号，默认0
    };
    var data = [];
    var height = document.body.offsetHeight;
    photos.forEach(function (pic, i) {
        if (pic.indexOf("http") != 0 && pic.indexOf("img") != 0) {
            if(pic.toLowerCase().endWith(".gif")){
                pic = "common/show?filePath=" + pic;
            }else{
                pic = "common/show?filePath=" + pic+"&height="+height;
            }
        }
        data.push({
            "pid": (i + 1), //图片id
            "src": pic, //原图地址
            "thumb": pic //缩略图地址
        });
    });
    json.data = data;
    window.top.layer.photos({
        photos: json,
        anim: 5 //0-6 切換方式
    });
}

/**
 * layer打开一个窗口，
 * @param title 标题
 * @param width 宽 px 或者百分比
 * @param height 高 px 或者百分比
 * @param url 相对路径或者绝对路径
 * @param func 关闭窗口回调函数
 */
function playVideo(url, func) {
    if (url.indexOf("http") != 0) {
        url = "/common/videoShow?filePath=" + url;
    }
    window.top.layer.open({
        type: 2,
        shadeClose: false,
        title: "视频播放",
        shade: 0.3,
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '70%'],
        content: 'hui/ckplayer.html?url=' + url,
        end: function () {
            if (func) {
                func();
            }
        }
    });
}

//type 允许上传文件类型,不传默认图片 gif,jpg,jpeg,bmp,png
//size 文件个数
//成功后的回调
function openUpload(size, func, type) {
    openDialog('文件上传', '80%', '70%', 'webuploader.html?size=' + size + (type ? "&type=" + type : ""), func);
}

/**
 * 字段消息提示
 *
 * @param selector
 *           类选择器,或者dom对象
 * @param msg
 *            提示消息
 */
function tip(selector, msg) {
    layer.tips(msg, selector);
}

/**
 * 下载文件
 * @param filepath
 */
function download(filepath) {
    window.location.href = "common/file/download?filePath=" + filepath;
}

/**
 * 从url中或去参数
 * @param {Object} name
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


