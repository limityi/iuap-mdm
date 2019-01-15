define(['text!pages/monitor/monitor.html','pages/monitor/meta','css!pages/monitor/monitor.css', 'uuitree', 'uuigrid', 'config/sys_const'], function (template) {
   
  //开始初始页面基础数据
    var init =  function (element, params) {
        var viewModel = {
            clickCount:0,
            draw: 1,//页数(第几页)
            pageSize: 5,

            childdraw:1,
            searchURL: ctx + '/Monitor/list',
            addURL: ctx + "/Monitor/add",
            updateURL: ctx + "/Monitor/update",
            delURL: ctx + "/Monitor/delBatch",
            formStatus: _CONST.FORM_STATUS_ADD, 
            MonitorDa: new u.DataTable(metaDt),
            MonitorFormDa: new u.DataTable(metaDt),
			Monitor_data_type:[{name:"客运线路",value:"mdm_line"},{name:"站场",value:"mdm_station"},{name:"车辆",value:"mdm_bus"},{name:"线路牌",value:"mdm_lisence"},{name:"公交线路",value:"mdm_busline"},{name:"客商联系人",value:"mdm_merchants_lxrxx"},{name:"客商银行账号",value:"mdm_merchants_yhxx"},{name:"组织",value:"mdm_nc_org"},{name:"服务区",value:"mdm_service_area"},{name:"结算方式",value:"mdm_settlementmethod"},{name:"南粤通代售点",value:"nyt_agent"},{name:"南粤通车辆",value:"nyt_bus"},{name:"南粤通结算单位",value:"nyt_company"},{name:"南粤通客运线路",value:"nyt_line"},{name:"南粤通站场",value:"nyt_station"},{name:"智慧客运车辆",value:"zhky_bus"},{name:"智慧客运线路",value:"zhky_line"},{name:"智慧客运站场",value:"zhky_station1"}], //从后台拉取数据
			Monitor_integration_mode:[{name:"ETL",value:"0"}], //从后台拉取数据
			Monitor_integration_type:[{name:"接收",value:"0"}], //从后台拉取数据
			Monitor_integration_strategy:[{name:"增量",value:"0"}], //从后台拉取数据

			MonitorLog_sync_status:[{name:"成功",value:"end"},{name:"失败",value:"stop"}], //从后台拉取数据
           
            MonitorLogDa: new u.DataTable(metaMonitorLog),
            MonitorLogFormDa: new u.DataTable(metaMonitorLog),
            MonitorLogType:null,
            MonitorLogDataType:null,
            MonitorLogData:null,

            /**树默认设置 */
            treeSetting: {
                view: {
                    showLine: false,
                    selectedMulti: false
                },
                callback: {
                    onClick: function (e, id, node) {
                        var rightInfo = node.name + '被选中';
                    }
                }
            },
            
            event: {
                mdlayoutClick:function () {
                    /*var display=$("#monitorLogDetail").css("display");
                    if(display=="block"){
                        $("#monitorLogDetail").hide();
                    }*/
                },
                rowChildClick: function (row,field,data) {
                    if(viewModel.clickCount>0) {
                        viewModel.MonitorLogType=row.data.data_type;
                        viewModel.MonitorLogDataType=field;
                        viewModel.MonitorLogData=data;
                        viewModel.childdraw=1;
                        viewModel.event.getUserJobList();
                    }
                },
                /**20161205修改最外层框架按钮组的显示与隐藏 */
                userListBtn: function () {  //显示user_list_button_2
                    $('#user_list_button_2').parent('.u-mdlayout-btn').removeClass('hide');
                    $('.form-search').removeClass('hide');
                    $('#user_card_button').parent('.u-mdlayout-btn').addClass('hide');

                },
                userCardBtn: function () {   //显示user_card_button
                    $('#user_list_button_2').parent('.u-mdlayout-btn').addClass('hide');
                    $('.form-search').addClass('hide');
                    $('#user_card_button').parent('.u-mdlayout-btn').removeClass('hide');
                },
                /**判断对象的值是否为空 */
                isEmpty: function (obj) {
                    return obj.value == undefined || obj.value == '' || obj.value.length == 0;
                },
                /**清除 datatable的数据  */
                clearDa: function (da) {
                    da.removeAllRows();
                    da.clear();
                },

                //加载初始列表
                initUerList: function () {
                    viewModel.clickCount=0;
                    var jsonData = {
                        pageIndex: viewModel.draw - 1,
                        pageSize: viewModel.pageSize,
                        sortField: "ts",
                        sortDirection: "desc"
                    };
                    $(element).find(".input_search").each(function () {
                        if (!viewModel.event.isEmpty(this)) {
                            jsonData['search_' + $(this).attr('name')] = removeSpace(this.value);
                        }
                    });

                    $.ajax({
                        type: 'get',
                        url: viewModel.searchURL,
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        var totleCount = res.detailMsg.data.totalElements;
                                        var totlePage = res.detailMsg.data.totalPages;
                                        viewModel.comps.update({
                                            totalPages: totlePage,
                                            pageSize: viewModel.pageSize,
                                            currentPage: viewModel.draw,
                                            totalCount: totleCount
                                        });
                                        viewModel.event.clearDa(viewModel.MonitorDa);
                                        viewModel.event.clearDa(viewModel.MonitorLogDa);
                                        viewModel.MonitorDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                    }
                                    viewModel.clickCount=1;
                                } else {
                                    var msg = "";
                                    for (var key in res.detailMsg) {
                                        msg += res.detailMsg[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        } 
                    });
                    //end ajax
                },

                pageChange: function () {
                    viewModel.comps.on('pageChange', function (pageIndex) {
                        viewModel.draw = pageIndex + 1;
                        viewModel.event.initUerList();
                    });
                    viewModel.child_list_pcomp.on('pageChange', function (pageIndex) {
                    	viewModel.childdraw = pageIndex + 1;
                    	viewModel.event.getUserJobList();
                    });
                },
                //end pageChange
                sizeChange: function () {
                    viewModel.comps.on('sizeChange', function (arg) {
                        //数据库分页
                        viewModel.pageSize = parseInt(arg);
                        viewModel.draw = 1;
                        viewModel.event.initUerList();
                    });
                    viewModel.child_list_pcomp.on('sizeChange', function (arg) {
                    	//数据库分页
                    	viewModel.pageSize = parseInt(arg);
                    	viewModel.childdraw = 1;
                    	viewModel.event.getUserJobList();
                    });
                    viewModel.child_card_pcomp.on('sizeChange', function (arg) {
                    	viewModel.pageSize = parseInt(arg);
                    	viewModel.childdraw = 1;
                    	viewModel.event.getUserJobList();
                    });
                },
                //end sizeChange

                search: function () {
                    viewModel.draw = 1;
                    viewModel.event.initUerList();
                },
                cleanSearch: function () {
                    $(element).find('.form-search').find('input').val('');
                },
                //以下用于check checkbox
                afterAdd: function (element, index, data) {
                    if (element.nodeType === 1) {
                        u.compMgr.updateComp(element);
                    }
                },
                goBack: function () {
                    //只显示新增编辑删除按钮
                    viewModel.event.userListBtn();
                    viewModel.md.dBack();
                    viewModel.event.initUerList();
                    $('#child_list_pagination').hide(); //隐藏子表的分页层
                },

                addClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_ADD;
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    viewModel.event.clearDa(viewModel.MonitorFormDa);
                    viewModel.MonitorFormDa.createEmptyRow();
                    viewModel.MonitorFormDa.setRowSelect(0);
                    viewModel.event.clearDa(viewModel.MonitorLogFormDa);
                    //设置业务操作逻辑
                    var row = viewModel.MonitorFormDa.getCurrentRow();
                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                },
                lookLogClick:function () {
                    var selectArray=viewModel.MonitorLogDa.selectedIndices();
                    if(selectArray.length<1){
                        u.messageDialog({
                            msg: "请先选中需要查看的日志!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    if (selectArray.length > 1) {
                        u.messageDialog({
                            msg: "一次只能查看一条记录，请重新选择的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    $("#logContent").text(viewModel.MonitorLogDa.getValue("log_field"));
                    $("#monitorLogDetail").show();
                },
                closeLogClick: function () {
                    $("#monitorLogDetail").hide();
                },
                editClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
                    var selectArray = viewModel.MonitorDa.selectedIndices();
                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    if (selectArray.length > 1) {
                        u.messageDialog({
                            msg: "一次只能编辑一条记录，请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    //获取选取行数据
                    viewModel.MonitorDa.setRowSelect(selectArray);
                    viewModel.MonitorFormDa.clear();
                    viewModel.MonitorLogFormDa.clear();
                    viewModel.MonitorFormDa.setSimpleData(viewModel.MonitorDa.getSimpleData({type: 'select'}));
                    viewModel.MonitorLogFormDa.setSimpleData(viewModel.MonitorLogDa.getSimpleData(), {unSelect: true});

                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                },

                saveClick: function () {
                    // compsValidate是验证输入格式。
                    if (! app.compsValidate($(element).find('#user-form')[0])) {
                        return;
                    }
                   
                    var user = viewModel.MonitorFormDa.getSimpleData();
                    var userJob = viewModel.MonitorLogFormDa.getSimpleData();
                    var jsondata =user[0];
                    jsondata.id_log = userJob;
                    var sendurl = viewModel.addURL;
                    if (viewModel.formStatus === _CONST.FORM_STATUS_EDIT) {
                        sendurl = viewModel.updateURL;
                    }
                    $.ajax({
                        type: "post",
                        url: sendurl,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsondata),//将对象序列化成JSON字符串
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    u.showMessage({
                                        msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>操作成功",
                                        position: "bottom"
                                    });
                                    viewModel.event.goBack();
                                } else {
                                    var msg = "";
                                    if (res.success == 'fail_global') {
                                        msg = res.message;
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key] + "<br/>";
                                        }
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '没有返回数据', title: '操作提示', btnText: '确定'});
                            }
                        } 
                    });
                },
                /**删除选中行*/
                delRow: function () {
                    var selectArray = viewModel.MonitorDa.selectedIndices();
                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    u.confirmDialog({
                        msg: '<div class="pull-left col-padding" >' +
                        '<i class="fa fa-exclamation-circle margin-r-5 fa-3x orange" style="vertical-align:middle"></i>确认删除这些数据数据吗？</div>',
                        title: '警告',
                        onOk: function () {
                            viewModel.event.delConfirm();
                        }
                    });
                },
                /**确认删除*/
                delConfirm: function () {
                    var jsonDel = viewModel.MonitorDa.getSimpleData({type: 'select'});
                    $.ajax({
                        type: "post",
                        url: viewModel.delURL,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsonDel),
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    /*u.showMessage({
                                        msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                        position: "center"
                                    })*/
                                    viewModel.event.initUerList();
                                } else {
                                    u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }
                        
                    });
                },
                rowClick: function (row, e) {
                    var ri = e.target.parentNode.getAttribute('rowindex')
                    if (ri != null) {
                        viewModel.MonitorDa.setRowFocus(parseInt(ri));
                        viewModel.MonitorDa.setRowSelect(parseInt(ri));
                    }
                    viewModel.MonitorFormDa.setSimpleData(viewModel.MonitorDa.getSimpleData({type: 'select'}));
                    var userId = viewModel.MonitorFormDa.getValue("id");
                    if (userId == null || userId == "") {
                        viewModel.MonitorLogDa.removeAllRows();
                        viewModel.MonitorLogDa.clear();
                    } else {
                        viewModel.event.getUserJobList();
                    }
                },
                selectUserJob: function (row, e) {
                    $("#monitorLogDetail").hide();
                    var ri = e.target.parentNode.getAttribute('rowindex')
                    if (ri != null) {
                        viewModel.MonitorLogDa.setRowFocus(parseInt(ri));
                        viewModel.MonitorLogDa.setRowSelect(parseInt(ri));
                    }
                },

                /**
                 * 树弹窗公共方法中取消按钮
                 */

                mdClose: function () {
                    md.close();
                },                

                /**绑定弹出层 树的按钮 */
                bindClickButton: function (ele, data, functionevent) { //对某一个按钮进行  点击事假绑定 ele:被绑定的元素，  data：需要传递的数据，functionevent：绑定的方法
                    $(ele).unbind('click'); //取消之前的绑定
                    $(ele).bind('click', data, functionevent); //重新绑定
                },

                /**子表列表 */
                getUserJobList: function () {
                    $("#monitorLogDetail").hide();
                    //var userId = viewModel.MonitorFormDa.getValue("id");
                    var jsonData = {
                        pageIndex: viewModel.childdraw-1,
                        pageSize: viewModel.pageSize,
                        sortField: "ts",
                        sortDirection: "asc"
                    };
                    jsonData['search_type'] = viewModel.MonitorLogType;
                    jsonData['search_dataType'] = viewModel.MonitorLogDataType;
                    jsonData['search_dataNumber'] = viewModel.MonitorLogData;
                    $.ajax({
                        type: 'GET',
                        url: ctx + '/MonitorLog/list',
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        viewModel.MonitorLogDa.removeAllRows();
                                        viewModel.MonitorLogDa.clear();
                                        viewModel.MonitorLogDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        
                                        viewModel.MonitorLogFormDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        var totleCount = res.detailMsg.data.totalElements;
                                        var totlePage = res.detailMsg.data.totalPages;
                                        viewModel.child_list_pcomp.update({ //列表页子表的分页信息
                                            totalPages: totlePage,
                                            pageSize: viewModel.pageSize,
                                            currentPage: viewModel.childdraw,
                                            totalCount: totleCount
                                        });
                                        viewModel.child_card_pcomp.update({ //卡片页子表的分页信息
                                        	totalPages: totlePage,
                                        	pageSize: viewModel.pageSize,
                                        	currentPage: viewModel.childdraw,
                                        	totalCount: totleCount
                                        });
                                        /*if(totleCount > viewModel.pageSize ){//根据总条数，来判断是否显示子表的分页层
                                        	$('#child_card_pagination').show();
                                        	$('#child_list_pagination').show();
                                        }else{
                                        	$('#child_card_pagination').hide();
                                        	$('#child_list_pagination').hide();
                                        }*/

                                    }
                                } else {
                                    var msg = "";
                                    for (var key in res.message) {
                                        msg += res.message[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        } 
                    });
                },
                //
                addUserJob: function () {
                    viewModel.MonitorLogFormDa.createEmptyRow();
                },
                delUserJob: function () {
                    var userJobs = viewModel.MonitorLogFormDa.getSimpleData({type: 'select'})
                    if (userJobs.length < 1) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                    }

                    if (confirm("确定要删除吗?")) {
                        var jsonDel = viewModel.MonitorLogFormDa.getSimpleData({type: 'focus'});
                        var index = viewModel.MonitorLogFormDa.getFocusIndex();
                        if (jsonDel[0].id == null) {
                            viewModel.MonitorLogFormDa.removeRows(index);
                            return;
                        }
                        $.ajax({
                            type: "post",
                            url: ctx + "/MonitorLog/del",
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify(jsonDel[0]),
                            success: function (res) {
                                if (res) {
                                    if (res.success == 'success') {
                                       /* u.showMessage({
                                            msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                            position: "center"
                                        })*/
                                        viewModel.MonitorLogFormDa.removeRows(index);
                                    } else {
                                        u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                    }
                                } else {
                                    u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                                }
                            } 
                        });
                    }
                },              
                
				/**枚举类型渲染 */
				changeMonitordata_type: function (id) {
                    var v = id();
                    for( var i= 0 ;i< viewModel.Monitor_data_type.length;i++ ){
                    	if(v == viewModel.Monitor_data_type[i].value ){
                    		return viewModel.Monitor_data_type[i].name ;
                    	} 
                    }
                },
				/**枚举类型渲染 */
				changeMonitorintegration_mode: function (id) {
                    var v = id();
                    for( var i= 0 ;i< viewModel.Monitor_integration_mode.length;i++ ){
                    	if(v == viewModel.Monitor_integration_mode[i].value ){
                    		return viewModel.Monitor_integration_mode[i].name ;
                    	} 
                    }
                },
				/**枚举类型渲染 */
				changeMonitorintegration_type: function (id) {
                    var v = id();
                    for( var i= 0 ;i< viewModel.Monitor_integration_type.length;i++ ){
                    	if(v == viewModel.Monitor_integration_type[i].value ){
                    		return viewModel.Monitor_integration_type[i].name ;
                    	} 
                    }
                },
				/**枚举类型渲染 */
				changeMonitorintegration_strategy: function (id) {
                    var v = id();
                    for( var i= 0 ;i< viewModel.Monitor_integration_strategy.length;i++ ){
                    	if(v == viewModel.Monitor_integration_strategy[i].value ){
                    		return viewModel.Monitor_integration_strategy[i].name ;
                    	} 
                    }
                },
				/**枚举类型渲染 */
				changeMonitorLogsync_status: function (id) {
                    var v = id();
                    for( var i= 0 ;i< viewModel.MonitorLog_sync_status.length;i++ ){
                    	if(v == viewModel.MonitorLog_sync_status[i].value ){
                    		return viewModel.MonitorLog_sync_status[i].name ;
                    	} 
                    }
                },
 


            } // end  event

        };
        //end viewModel


        $(element).html(template);
        var app = u.createApp({
            el: '#content',
            model: viewModel
        });
        viewModel.md = $(element).find('#user-mdlayout')[0]['u.MDLayout'];
        var paginationDiv = $(element).find('#pagination')[0];
        viewModel.comps = new u.pagination({el: paginationDiv, jumppage: true});
        
        viewModel.child_list_pcomp = new u.pagination({el: $(element).find('#child_list_pagination')[0], jumppage: true});
        viewModel.child_card_pcomp = new u.pagination({el: $(element).find('#child_card_pagination')[0], jumppage: true});
        viewModel.childdraw=1 ;

        viewModel.event.initUerList();
        viewModel.event.pageChange();
        viewModel.event.sizeChange();

    }  //end init

    return {
        'model': init.viewModel,
        'template': template,
        init: function (params, arg) {
            init(params, arg);
            /*回车搜索*/
            $('.search-enter').keydown(function (e) {
                if (e.keyCode == 13) {
                    $('#user-action-search').trigger('click');

                }
            });
        }
    }
});
//end define
