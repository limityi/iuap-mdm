require(['knockout', 'director',], function (ko) {
    $.ajaxSetup({
        cache: false
    });

    window.addRouter = function (path, func) {
        /*var username=localStorage.getItem("mdmusername");
        var timestamp=localStorage.getItem("mdmtimestamp");
        var curTime=new Date().getTime();
        if(username==null||(curTime-timestamp>10800000)){
            window.location.href="http://128.14.1.55:809/portal"
        }*/
        var pos = path.indexOf('/:');
        var truePath = path;
        if (pos != -1)
            truePath = path.substring(0, pos);
        func = func || function () {
            var params = arguments;
            initPage('pages/' + truePath, params);
        }
        var tmparray = truePath.split("/");
        if (tmparray[1] in router.routes && tmparray[2] in router.routes[tmparray[1]] && tmparray[3] in router.routes[tmparray[1]][tmparray[2]]) {
            return;
        } else {
            // router.on(path, func);
            //检查路由是否存在
            var tmparray = path.split("/");
            var routes = router.routes;
            var routerExist = true;
            while (tmparray.length > 0) {
                var _path = tmparray.shift();
                if (_path === '') {
                    continue;
                } else if (_path.indexOf(':') == 0) {
                    routes = routes['([._a-zA-Z0-9-%()]+)'];
                } else if (routes[_path]) {
                    routes = routes[_path];
                } else {
                    routerExist = false;
                    break;
                }
            }
            //路由去重判断
            if (!routerExist) {
                router.on(path, func);
            }
        }
    }

    window.router = Router();
    window.ko = ko;
    ctx = "/yueyun";

    $(function () {
        $('#menu,#nav-zone').find("a[href*='#']").each(function (e) {
            var path = this.hash.replace('#', '');
            addRouter(path);
            var location = window.location.hash;
            if (location == $(this).attr('href')) {
                $(this).parents('li').addClass('specli').siblings().removeClass('specli')
            }
        });
        $('#nav-zone').find("a[href*='#']").each(function (e) {
            var location = window.location.hash;
            if (location == $(this).attr('href')) {
                $(this).parents('li').addClass('specli').siblings().removeClass('specli')
            }
        });
        window.router.init();
        if (window.location.href.indexOf("#") < 0) {
            window.router.setRoute($('#menu').find("a[href*='#']")[0].hash.replace('#', ''));
        }
        ;

        $('#menu  li').click(function (e) {
            //console.log($(e.target).parents('li'));
            var index = $(this).index();
            //console.log(index);
            $(e.target).parents('li').addClass('specli').siblings().removeClass('specli');
            /*收缩菜单同步改变*/
            $('#nav-zone  li').eq(index).addClass('specli').siblings().removeClass('specli');

        })
        $('#nav-zone  li').click(function (e) {
            var index = $(this).index();
            //console.log(index);
            $(e.target).parents('li').addClass('specli').siblings().removeClass('specli');
            /*展开菜单同步改变*/
            $('#menu li').eq(index).addClass('specli').siblings().removeClass('specli');
        })


        $('.foldingpad').click(function () {
            if ($(this).hasClass('rotate')) {
                _unfold();//展开
            } else {
                _shrink();
            }
        });

        // left nav shrink 收缩
        function _shrink() {
            $('.nav-li').addClass('live-hover');
            $('.foldingpad').addClass('rotate');
            // $('.nav-item-list').css('left','-180px');
            $('.page-container').css('margin-left', '55px');
            $('.foldingpad').css('left', '65px')
            $('.page-sidebar').css('margin-left', '-200px');
            $('.page-small-sidebar').css('margin-left', '0px');
            $('.global-notice').css('left', '90px')
            // setCookie('menu','2');
        }

        // left nav unfold 展开
        function _unfold() {
            $('.nav-li').removeClass('live-hover');
            $('.foldingpad').removeClass('rotate');
            // $('.nav-item-list').css('left','75px');
            $('.page-container').css('margin-left', '200px');
            $('.page-sidebar').css('margin-left', '0px');
            $('.page-small-sidebar').css('margin-left', '-55px');
            $('.global-notice').css('left', '235px')
            $('.foldingpad').css('left', '210px')
        }

    })

    /**统一设置ajax的参数信息，发送信息前加载 loading 图标，请求完成后去掉 loading进度条图片 */
    $(function () {
        $.ajaxSetup({
            beforeSend: function (xhr) {
                var centerContent = '<i class="uf uf-fluffycloudsilhouette u-loader-centerContent"></i>';
                var opt1 = {
                    hasback: true,
                    hasDesc: true,//是否含有加载内容描述
                    centerContent: centerContent
                };
                u.showLoader(opt1);
            },
            complete: function (xhr, status) {
                setTimeout("u.hideLoader({hasback:true});", 200);

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {//对错误进行统一处理
                var info = '';
                if (XMLHttpRequest.readyState == 0) {
                    info = '请求超时' + XMLHttpRequest.responseText;
                } else {
                    info = '请求异常，请检查。' + XMLHttpRequest.responseText;
                }
                u.messageDialog({msg: info, title: '请求错误', btnText: '确定'});
            }
        });
    })

    function initPage(p, id) {
        var module = p;
        var truePath = p.substring(6);
        //修改标题
        updateTitle(truePath);
        var aa = "a[href*='" + truePath + "']";
        $($('#menu,#nav-zone').find(aa)[0]).parent().addClass('specli');
        var content = document.getElementById("content");
        require([module], function (module) {
            ko.cleanNode(content);
            content.innerHTML = "";
            module.init(content);

        })
    }

    function updateTitle(title) {
        if (title == 'monitor/monitor') {
            $("#titile").text(" 主 数 据 集 成 监 控 ");
        } else {
            $("#titile").text(" 主 数 据 质 量 报 告 ");
        }

    }
})
