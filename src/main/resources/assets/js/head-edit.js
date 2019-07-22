//新增
var defaultInsertUrl = mainUrl + "/insert";// 默认新增url
//修改
var defaultUpdateUrl = mainUrl + "/update";// 默认新增url
//查询
var defaultQueryUrl = mainUrl + "/query";// 默认查询url

//表单对象
var form = null;

/**
 * @param verifyJson 自定义表单验证
 @param formJson 表单初始化值
 @param even 表单监听事件
 */
function initForm(verifyJson, formJson, event) {
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;
        //自定义验证规则
        form.verify(verifyJson);
        //监听提交
        form.on('submit(save-btn)', function (data) {
            var param = data.field;
            $.each($(".layui-form input[type='checkbox']"), function (i, dom) {
                //处理开关
                if (param[dom.name] == 'on') {
                    param[dom.name] = true;
                } else {
                    param[dom.name] = false;
                }
            });
            if (isNotEmpty(param[idProperty])) {
                //如果id不为null，调用updateBefore来验证参数是否正确
                if (!updateBefore(param)) {
                    return false;
                }
            } else {
                //如果id不为null，调用insertBefore来验证参数是否正确
                if (!insertBefore(param)) {
                    return false;
                }
            }
            //最后调用saveBefore来验证参数是否正确
            if (!saveBefore(param)) {
                return false;
            }
            //提交
            doPost({
                url: isNotEmpty(param[idProperty]) ? defaultUpdateUrl : defaultInsertUrl,
                data: param,
                success: function (result) {
                    showSuccessMsg("保存成功");
                    closeSelf();
                }
            });
            return false;
        });
        //为表单赋值
        if (formJson && formJson != null) {
            form.val('save-form', formJson)
        }
        //其他处理
        if (event) {
            event();
        }

    });
}

/**
 *   根据id获取该条记录
 *  @param id
 */
function getDataById(id) {
    var result = null;
    if (isNotEmpty(id)) {
        doGet({
            async: false,//同步
            modal: false,//不显示加载条
            url: defaultQueryUrl + "?" + idProperty + "=" + id,
            success: function (data) {
                if (data.list.length > 0) {
                    result = data.list[0];
                } else {
                    showErrorMsg("未找到相关数据");
                }
            }
        });
    }
    return result;
}