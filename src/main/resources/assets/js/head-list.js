//查询
var defaultQueryUrl = mainUrl + "/query";// 默认查询url
//修改
var defaultEditUrl = mainUrl + "/edit";// 默认修改url
//无效
var defaultInvalidUrl = mainUrl + "/invalid";// 默认逻辑删除url
//批量无效
var defaultInvalidBatchUrl = mainUrl + "/invalidBatch";// 默认批量逻辑删除url
//表格实例
var tableIns = null;
var start = null;
var end = null;
$(function () {
    //日期范围限制
    start = {
        elem: '#startDate',
        format: 'YYYY-MM-DD hh:mm:ss',
        //min: laydate.now(), //设定最小日期为当前日期
        //max: '2099-06-16 23:59:59', //最大日期
        istime: true,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    //结束时间
    end = {
        elem: '#endDate',
        format: 'YYYY-MM-DD hh:mm:ss',
        //min: laydate.now(),
        //max: '2099-06-16 23:59:59',
        istime: true,
        istoday: false,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    if ($('#startDate').length > 0) {
        laydate(start);
    }
    if ($('#endDate').length > 0) {
        laydate(end);
    }
    // 下拉框
    $('.chosen-select').chosen({
        allow_single_deselect: true
    });
});

/**
 * 初始化表格
 * @Param cols 列
 * @Param func_back 执行请求后的数据处理
 * @Param func_after 頁面渲染完后的处理
 */
function initTable(cols, func_back, func_after) {
    layui.use('table', function () {
        //搜索表单的高度
        var searchFormHeight = $('#searchForm > .col-md-12').height();
        //获取表单输入的查询条件
        var param = getAreaVal('searchForm');
        //把公共查询条件写入表单输入的查询条件
        for (var key in condition) {
            param[key] = condition[key];
        }
        for (var key in param) {
            if (param[key] == null) {
                //如果值为null，则删除该条件
                delete param[key];
            }
            if (param[key] instanceof Array) {
                //如果是数组则转化为json数组字符串
                param[key] = JSON.stringify(param[key]);
            }
        }
        var renderParam = {
            elem: '#simple-table'
            , toolbar: "#toolbarDemo"
            , defaultToolbar: ["filter"] // filter: 显示筛选图标exports: 显示导出图标 print: 显示打印图标
            , cellMinWidth: 120 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , height: 'full-' + (searchFormHeight + 30) //文档 :https://www.layui.com/doc/modules/table.html#height
            , skin: "" // line （行边框风格）row （列边框风格）nob （无边框风格）
            , cols: [cols]
            , url: defaultQueryUrl
            , method: 'get'
            , request: {
                pageName: 'pageNo' //页码的参数名称，默认：page
                , limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            , where: param
            , limit: 10//每页大小
            , limits: [10, 20, 40, 80, 100]
            , page: true//是否前端分页
            , parseData: function (res) {
                if(res.status != 200){
                    showErrorMsg(res.msg);
                    return;
                }
                //返回的数据处理
                func_back(res.data)
                //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": 0, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.totalCount, //解析数据长度
                    "data": res.data.list //解析数据列表
                };
            }
            , done: function (res, curr, count) {
                //表头展示不全的问题
                //count || this.elem.next('.layui-table-view').find('.layui-table-header').css('overflow', 'auto');
                //重置表格尺寸，不知道有些页面点击操作栏的操作后就不浮动了
                layui.table.resize('simple-table');
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                if (func_after) {
                    func_after(res, curr, count);
                }
            }
        };
        //如果工具栏为空，则删除表格工具栏
        var toolbarDemo = document.getElementById("toolbarDemo");
        if (toolbarDemo == null) {
            delete renderParam.toolbar;
        } else {
            //如果工具栏下没有按钮与链接地址，则删除表格工具栏
            var btns = $(toolbarDemo.innerHTML).find("a,button");
            if (btns.length == 0) {
                delete renderParam.toolbar;
            }
        }
        tableIns = layui.table.render(renderParam);
        //监听工具栏事件
        layui.table.on('toolbar(filter)', function (obj) {
            var checkStatus = layui.table.checkStatus(obj.config.id);
            //选中的数据
            var data = checkStatus.data;
            switch (obj.event) {
                //批量删除
                case 'delete':
                    if (data.length == 0) {
                        showWarningMsg("请至少选择1条数据");
                        return;
                    }
                    var ids = [];
                    data.forEach(function (single, i) {
                        ids.push(single[idProperty]);
                    });
                    confirmAndPost({
                        msg: "确认删除选中的数据?",
                        url: defaultInvalidBatchUrl,
                        data: {
                            ids: ids.split(",")
                        },
                        success: function () {
                            query();
                        }
                    });
                    break;
                case 'resize':
                    //重置表格
                    layui.table.resize('simple-table');
                    break;
                default:
                    //筛选列点击事件
                    if(obj.event == 'LAYTABLE_COLS'){
                        break
                    }
                    //其他类型事件，直接调用以该事件命名的方法
                    //比如 事件名为 addBatch, 那直接调用 addBatch(data)方法;
                    try {
                        var func = eval(obj.event);
                        func(data);
                    } catch (e) {
                        showErrorMsg("请添加" + obj.event + "(data)方法,处理点击事件!");
                    }
                    break;
            }
        });
        //监听行工具事件
        layui.table.on('tool(filter)', function (obj) {
            switch (obj.event) {
                case 'del':
                    confirmAndPost({
                        msg: "确认删除该数据?",
                        url: defaultInvalidUrl,
                        data: {
                            id: obj.data[idProperty]
                        },
                        success: function () {
                            query();
                        }
                    });
                    break;
                default:
                    //其他类型事件，直接调用以该事件命名的方法
                    //比如 事件名为 updateById, 那直接调用 updateById(obj.data)方法;
                    try {
                        var func = eval(obj.event);
                        func(obj.data);
                    } catch (e) {
                        showErrorMsg("请添加" + obj.event + "(data)方法,处理点击事件!");
                    }
                    break;
            }
        });
    });
}

//根据条件进行查询数据，填充表格
function query() {
    //获取表单输入的查询条件
    var param = getAreaVal('searchForm');
    //把公共查询条件写入表单输入的查询条件
    for (var key in condition) {
        param[key] = condition[key];
    }
    for (var key in param) {
        if (param[key] == null) {
            //如果值为null，则删除该条件
            delete param[key];
        }
        if (param[key] instanceof Array) {
            //如果是数组则转化为json数组字符串
            param[key] = JSON.stringify(param[key]);
        }
    }
    //表单重载
    tableIns.reload({
        where: param
        , page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}

//默认编辑
function edit(data) {
    openCurrentMaxDialog("修改", defaultEditUrl + "?" + idProperty + "=" + data[idProperty], function () {
        query();
    });
}

//默认添加 data为选中的数据数组
function add(data) {
    openCurrentMaxDialog("新增", defaultEditUrl, function () {
        query();
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