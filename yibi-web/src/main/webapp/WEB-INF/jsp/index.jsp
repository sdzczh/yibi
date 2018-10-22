<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/static/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/homeindex.css" />
<%@ include file="/static/commons/commoncss.jsp"%>
<%@ include file="/static/commons/commonjs.jsp"%>
	<script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
<script type="text/javascript">
    var index_tabs;
    $(function() {
        index_tabs = $("#index_tabs").tabs({
            fit : true,
            border : false,
            tools : [ {
                iconCls : "icon_home",
                handler : function() {
                    index_tabs.tabs("select", 0);
                }
            }, {
                iconCls : "icon_refresh",
                handler : function() {
                    var currentTab = $("#index_tabs").tabs("getSelected");
                    topRefreshTab(currentTab);
                }
            }, {
                iconCls : "icon_del",
                handler : function() {
                    var index = index_tabs.tabs("getTabIndex", index_tabs.tabs("getSelected"));
                    var tab = index_tabs.tabs("getTab", index);
                    if (tab.panel("options").closable) {
                        index_tabs.tabs("close", index);
                    }
                }
            } ]
        });

    });

    var winTop;
    function showTopDialog(title, url, width, height, scroll) {
        var topheight = top.$(window).height();
        var topwidth = top.$(window).width();
        var toph = (topheight - height) / 2;
        var lefth = (topwidth - width) / 2;

        var content = '<iframe id="topDialogiframe" src="' + url + '" width="100%" height="98%" frameborder="0" scrolling="' + scroll + '"></iframe>';
        winTop = $("#msgDialogWin").dialog({
            content : content,
            width : width,
            height : height,
            top:toph,
            left:lefth,
            modal : true,
            title : title,
            resizable : true,
            onClose : function() {
                $(this).empty();
            }
        });
    }

    function closeTopDialog() {
        if(winTop!=undefined){
            $("#msgDialogWin").dialog("close");
        }
    }

	//退出登录
	function logout(){
        $.messager.confirm('提示', '确定要退出?', function(r) {
            if (r) {
                window.location.href = "${ctx}/login/loginout.do";
            }
        });
	}

	//打开新的选项卡
    function addTab(title, url) {
        closeTopDialog();
        if ($("#index_tabs").tabs("exists", title)) {
            $("#index_tabs").tabs("select", title);
            var currentTab = $("#index_tabs").tabs("getSelected");
            topRefreshTab(currentTab);
        } else {
            var content = "<iframe scrolling='auto' frameborder='0'  src='" + url + "' style='width:100%;height:100%;display:block;'></iframe>";
            $("#index_tabs").tabs("add", {
                title : title,
                content : content,
                closable : true
            });
        }
    }

    function topRefreshTab(currentTab) {
        var url = $(currentTab.panel("options")).attr("href");
        $("#index_tabs").tabs("update", {
            tab : currentTab,
            options : {
                href : url
            }
        });
        currentTab.panel("refresh");
    }




</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="topbackgroundimg">
		<span class="header"></span>
		<div style="float: right;margin-right: 20px;">
			<ul class="navright">
				<li style="width: 110px;">
					<a href="javascript:void(0);" style="width: 100px;">
						<div style="width: 100px;height: 66px;border: 0px;display: table-cell;vertical-align: bottom;text-align: center;font-size:120%;color: black;">${loginUser.userName}</div>
					</a>
				</li>
				<li>
					<a href="javascript:void(0);">
						<img src="${ctx}/static/images/userinfo.png" title="用户信息" onclick="getManagerInfo();" />
					</a>
				</li>
				<li>
					<a href="javascript:void(0);">
						<img src="${ctx}/static/images/loginout.png" title="安全退出" onclick="logout();" />
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单'" style="width:220px;">
		<div id="leftpeneldiv" class="easyui-accordion  i_accordion_menu" fit="true" border="false">
			<c:forEach items="${sysMenuList }" var="sysMenu">
				<div title="${sysMenu.name }" selected="false" style="overflow: auto;">
					<c:forEach items="${sysMenu.children }" var="sysMenuSub" varStatus="varStatusObj">
						<div class="nav-item">
							<a href="javascript:addTab('${sysMenuSub.name }','${ctx}${sysMenuSub.url }')">
								<span class="menu_icon_datadeal"></span>
								<span>${sysMenuSub.name}</span>
							</a>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="index_tabs" class="easyui-tabs" style="width:100%;height:100%;">
			<div title="首页" class="indexbackgroundimg">
				<button onclick="getIndexInfo()"  class="btn-mini">查看币种总额</button>

				<div id="container"></div>
			</div>

		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:20px;text-align: center;"><span style="font-family:Arial;">Copyright&copy; HuoLi International</span></div>
	<div id="msgDialogWin"></div>
</body>
<script type="text/javascript">
	function getManagerInfo(){
        showTopDialog("登录用户信息", "${ctx}/manager/infoPage.do?id="+${loginUser.userid}, "700", "300", "no");
	}


    function getIndexInfo(){
        $.get("${ctx}/resource/getIndexInfo.do",function(result){
            var chart = Highcharts.chart('container',{
                chart: {
                    type: 'column'
                },
                title: {
                    text: '截止到现在的各币种的数量'
                },
                subtitle: {
                    text: '数据来源: YB新数据库.com'
                },
                xAxis: {
                    categories: [
                        'KN','BTC','USDT','TLC','ETH','ETC','DK','RCC','LKS','DEGO','OIO','EOS'
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '各币种数量 (总量)'
                    }
                },
                tooltip: {
                    // head + 每个 point + footer 拼接成完整的 table
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} 单位（个）</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '币种数量',
                    data: [result.knAmount, result.btcAmount , result.usdtAmount , result.ltcAmount ,
                        result.ethAmount , result.etcAmount ,
                        result.dkAmount , result.rccAmount ,
                        result.lksAmount , result.dogeAmount ,
                        result.oioAmount , result.eosAmount ]
            }]
        });
        });
    }


</script>



</html>
