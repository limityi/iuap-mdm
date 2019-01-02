define(['text!pages/service/service.html', 'pages/service/meta', 'css!pages/service/service.css', 'uuitree', 'uuigrid'], function (html) {
    var init = function (element) {
        var listUrl = ctx + '/serviceArea/list?admin=admin';
        var delUrl = ctx + '/serviceArea/del/';
        var saveUrl = ctx + '/serviceArea/save';
        var exportExcelUrl = ctx + '/serviceArea/exportExcel';

        var viewModel = {
            updateOperation: false,
            draw: 1,
            totlePage: 0,
            pageSize: 5,
            totleCount: 0,
            dt1: new u.DataTable(metaCardTable),
            dtnew: new u.DataTable(metaCardTable),

            drawOnly: 1,
            totlePageOnly: 0,
            pageSizeOnly: 5,
            totleCountOnly: 0,
            dtonly: new u.DataTable(metaCardTable),

            drawRequired: 1,
            totlePageRequired: 0,
            pageSizeRequired: 5,
            totleCountRequired: 0,
            dtrequired: new u.DataTable(metaCardTable),

            stationCompareSyncTime: '',
            stationOnlySyncTime: '',
            stationRequiredSyncTime: '',

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
                exportClick: function () {
                    window.location.href = exportExcelUrl;
                },

                updateClick: function () {
                    viewModel.updateOperation = true;
                    viewModel.event.initCardTableList();
                },

                clearDt: function (dt) {
                    dt.removeAllRows();
                    dt.clear();
                },

                initCardTableList: function () {
                    var jsonData = {
                        pageIndex: viewModel.draw - 1,
                        pageSize: viewModel.pageSize
                    };
                    $(element).find("#search").each(function () {
                        if (this.value == undefined || this.value == '' || this.value.length == 0) {
                        } else {
                            jsonData['search_searchParam'] = encodeURI(this.value.replace(/(^\s*)|(\s*$)/g, ""));  //去掉空格
                        }
                    });
                    jsonData['search_pageIndexOnly'] = viewModel.drawOnly - 1;
                    jsonData['search_pageSizeOnly'] = viewModel.pageSizeOnly;
                    jsonData['search_pageIndexRequired'] = viewModel.drawRequired - 1;
                    jsonData['search_pageSizeRequired'] = viewModel.pageSizeRequired;
                    jsonData['search_updateOperation'] = viewModel.updateOperation;
                    $.ajax({
                        type: 'get',
                        url: listUrl,
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            viewModel.updateOperation = false;
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        viewModel.totleCount = res.detailMsg.data.stationCompareData.totalElements;
                                        viewModel.totlePage = res.detailMsg.data.stationCompareData.totalPages;
                                        viewModel.event.comps.update({
                                            totalPages: viewModel.totlePage,
                                            pageSize: viewModel.pageSize,
                                            currentPage: viewModel.draw,
                                            totalCount: viewModel.totleCount
                                        });
                                        viewModel.dt1.removeAllRows();
                                        viewModel.dt1.clear();
                                        viewModel.dt1.setSimpleData(res.detailMsg.data.stationCompareData.content, {unSelect: true});

                                        viewModel.totleCountOnly = res.detailMsg.data.stationOnlyData.totalElements == null ? viewModel.totleCountOnly : res.detailMsg.data.stationOnlyData.totalElements;
                                        viewModel.totlePageOnly = res.detailMsg.data.stationOnlyData.totalPages == null ? viewModel.totlePageOnly : res.detailMsg.data.stationOnlyData.totalPages;
                                        if (viewModel.totleCountOnly != 0 && viewModel.totlePageOnly != 0) {
                                            viewModel.event.comps_only.update({
                                                totalPages: viewModel.totlePageOnly,
                                                pageSize: viewModel.pageSizeOnly,
                                                currentPage: viewModel.drawOnly,
                                                totalCount: viewModel.totleCountOnly
                                            })
                                        }

                                        viewModel.dtonly.removeAllRows();
                                        viewModel.dtonly.clear();
                                        viewModel.dtonly.setSimpleData(res.detailMsg.data.stationOnlyData.content, {unSelect: true});

                                        viewModel.totleCountRequired = res.detailMsg.data.stationRequiredData.totalElements;
                                        viewModel.totlePageRequired = res.detailMsg.data.stationRequiredData.totalPages;
                                        viewModel.event.comps_required.update({
                                            totalPages: viewModel.totlePageRequired,
                                            pageSize: viewModel.pageSizeRequired,
                                            currentPage: viewModel.drawRequired,
                                            totalCount: viewModel.totleCountRequired
                                        })
                                        viewModel.dtrequired.removeAllRows();
                                        viewModel.dtrequired.clear();
                                        viewModel.dtrequired.setSimpleData(res.detailMsg.data.stationRequiredData.content, {unSelect: true});

                                        $("#stationCompareTimeSpan").text(res.detailMsg.data.stationCompareTime == null ? "" : res.detailMsg.data.stationCompareTime);
                                        $("#stationOnlyTimeSpan").text(res.detailMsg.data.stationOnlyTime == null ? "" : res.detailMsg.data.stationOnlyTime);
                                        $("#stationRequiredTimeSpan").text(res.detailMsg.data.stationRequiredTime == null ? "" : res.detailMsg.data.stationRequiredTime);

                                    }
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
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        },
                        error: function (er) {
                            u.messageDialog({msg: '请求超时', title: '请求错误', btnText: '确定'});
                        }
                    });
                },

                genDataList: function (data) {
                    var datalist = [];
                    datalist.push(data);
                    return datalist;
                },

                checkedCardtable: function (data) {
                    var defaultNum = 0;
                    return data != null;

                },

                deleteData: function (data) {
                    var datalist = viewModel.event.genDataList(data);
                    var json = JSON.stringify(datalist);
                    $.ajax({
                        url: delUrl,
                        data: json,
                        dataType: 'json',
                        type: 'post',
                        contentType: 'application/json',
                        success: function (res) {
                            //md.close();
                            if (res) {
                                if (res.success == 'success') {
                                    u.messageDialog({msg: '删除成功', title: '操作提示', btnText: '确定'});
                                } else {
                                    var msg = "";
                                    for (var key in res.message) {
                                        msg += res.message[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: 'msg', title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }

                        },
                        error: function (er) {
                            u.messageDialog({msg: '操作请求失败，' + er, title: '操作提示', btnText: '确定'});
                        }
                    });
                },

                saveData: function (data) {
                    var datalist = viewModel.event.genDataList(data);
                    var json = JSON.stringify(datalist);
                    $.ajax({
                        url: saveUrl,
                        type: 'post',
                        data: json,
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    viewModel.event.initCardTableList();
                                    u.messageDialog({msg: '操作成功', title: '操作提示', btnText: '确定'});
                                } else {
                                    var msg = "";
                                    if (res.success == 'fail_global') {
                                        msg = res.message;
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key] + "<br/>";
                                        }
                                    }
                                    u.messageDialog({msg: msg, title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '没有返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }
                    });
                },

                pageChange: function () {
                    viewModel.event.comps.on('pageChange', function (pageIndex) {
                        viewModel.draw = pageIndex + 1;
                        viewModel.event.initCardTableList();
                    });
                },

                sizeChange: function () {
                    viewModel.event.comps.on('sizeChange', function (arg) {
                        viewModel.pageSize = parseInt(arg);
                        viewModel.draw = 1;
                        viewModel.event.initCardTableList();
                    });
                },

                pageChangeOnly: function () {
                    viewModel.event.comps_only.on('pageChange', function (pageIndex) {
                        viewModel.drawOnly = pageIndex + 1;
                        viewModel.event.initCardTableList();
                    });
                },

                sizeChangeOnly: function () {
                    viewModel.event.comps_only.on('sizeChange', function (arg) {
                        viewModel.pageSizeOnly = parseInt(arg);
                        viewModel.drawOnly = 1;
                        viewModel.event.initCardTableList();
                    });
                },

                pageChangeRequired: function () {
                    viewModel.event.comps_required.on('pageChange', function (pageIndex) {
                        viewModel.drawRequired = pageIndex + 1;
                        viewModel.event.initCardTableList();
                    });
                },

                sizeChangeRequired: function () {
                    viewModel.event.comps_required.on('sizeChange', function (arg) {
                        viewModel.pageSizeRequired = parseInt(arg);
                        viewModel.drawRequired = 1;
                        viewModel.event.initCardTableList();
                    });
                },

                mdClose: function () {
                    refmd.close();
                },

                bindClickButton: function (ele, data, functionevent) { //对某一个按钮进行  点击事假绑定 ele:被绑定的元素，  data：需要传递的数据，functionevent：绑定的方法
                    $(ele).unbind('click'); //取消之前的绑定
                    $(ele).bind('click', data, functionevent); //重新绑定
                },

                pageInit: function () {
                    $(element).html(html);
                    app = u.createApp({
                        el: element,
                        model: viewModel
                    });

                    var paginationDiv = $(element).find('#pagination')[0];
                    this.comps = new u.pagination({el: paginationDiv, jumppage: true});

                    this.comps_only = new u.pagination({el: $(element).find('#paginationOnly')[0], jumppage: true});
                    this.comps_required = new u.pagination({
                        el: $(element).find('#paginationRequired')[0],
                        jumppage: true
                    });

                    this.initCardTableList();
                    viewModel.event.pageChange();
                    viewModel.event.sizeChange();

                    viewModel.event.pageChangeOnly();
                    viewModel.event.sizeChangeOnly();

                    viewModel.event.pageChangeRequired();
                    viewModel.event.sizeChangeRequired();

                    //回车搜索
                    $('.input_enter').keydown(function (e) {
                        if (e.keyCode == 13) {
                            $('#searchBtn').trigger('click');

                        }
                    });
                },

                editClick: function () {
                    $('#editPage').find('.u-msg-title').html("编辑");
                    viewModel.event.clearDt(viewModel.dtnew);
                    var row = viewModel.dt1.getSelectedRows()[0];
                    if (row) {
                        viewModel.dtnew.setSimpleData(viewModel.dt1.getSimpleData({type: 'select'}));
                        window.md = u.dialog({id: 'editDialog', content: '#editPage', hasCloseMenu: true});
                        /*$('#editDialog').css('width', '70%')*/
                    } else {
                        u.messageDialog({msg: '请选择要编辑的数据！', title: '操作提示', btnText: '确定'});
                    }
                },

                addClick: function () {
                    $('#editPage').find('.u-msg-title').html("新增");
                    viewModel.event.clearDt(viewModel.dtnew);
                    var newr = viewModel.dtnew.createEmptyRow();
                    viewModel.dtnew.setRowSelect(newr);
                    window.md = u.dialog({id: 'addDialog', content: '#editPage', hasCloseMenu: true});
                    $('#addDialog').css('width', '70%');
                },

                delClick: function () {
                    var row = viewModel.dt1.getSelectedRows()[0];
                    if (row) {
                        var data = viewModel.dt1.getSelectedRows()[0].getSimpleData()
                        u.confirmDialog({
                            msg: "是否删除" + data.name + "?",
                            title: "删除确认",
                            onOk: function () {
                                viewModel.event.deleteData(data);
                                viewModel.dt1.removeRow(viewModel.dt1.getSelectedIndexs());
                            },
                            onCancel: function () {
                            }
                        });
                    } else {
                        u.messageDialog({msg: '请选择要删除的数据！', title: '操作提示', btnText: '确定'});
                    }
                },

                searchClick: function () {
                    viewModel.draw = 1;
                    viewModel.updateOperation = true;
                    viewModel.event.initCardTableList();
                },

                saveOkClick: function () {
                    var data = viewModel.dtnew.getSimpleData()[viewModel.dtnew.getSelectedIndexs()];
                    if (viewModel.event.checkedCardtable(data)) {
                        viewModel.event.saveData(data);
                        md.close();
                    }
                },

                saveCancelClick: function (e) {
                    md.close();
                }
            }
        }

        $(element).html(html);
        viewModel.event.pageInit();
    }
    return {
        'template': html,
        init: init
    }
});

