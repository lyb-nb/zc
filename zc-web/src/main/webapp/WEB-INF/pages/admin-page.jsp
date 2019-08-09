<%@ page contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/pages/include-head.jsp" %>
<link rel="stylesheet" href="../../css/pagination.css">
<script type="text/javascript" src="../../script/jquery.min.js"></script>
<script type="text/javascript" src="../../script/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        // 对分页导航条显示进行初始化
        initPagination();

        $("#summarybox").click(function () {
            $(".itembox").attr("checked", this.checked);
        });
        // 给单条删除按钮绑定单击响应函数
        $(".singleDelete").click(function () {
            // 获取当前adminId值
            var adminId = $(this).attr("adminId");
            // 获取当前记录的loginAcct
            var loginAcct = $(this).parents("tr").children("td:eq(2)").text();
            // 为了能够使用批量删除的操作，将adminId存入数组
            var adminArray = new Array();
            adminArray.push(adminId);
            // 检查adminIdArray是否包含有效数据
            if (adminArray.length == 0) {
                // 给出提示
                alert("请勾选您要删除的记录！");
                // 函数停止执行
                return;
            }
            var requestBody = JSON.stringify(adminArray);
            var confirmResult = confirm("您真的要删除" + loginAcct + "这条记录吗？");
            if (!confirmResult) {
                return;
            }
            $.ajax({
                "url": "admin/batch/delete",
                "type": "post",
                "contentType": "application/json;charset=UTF-8",
                "data": requestBody,
                "dataType": "json",
                "success": function (response) {
                    console.log(response);
                    var result = response.result;
                    if ("SUCCESS" == result) {
                        window.location.href = "admin/page/query?pageNum=" + ${requestScope['PAGE-INFO'].pageNum} +"&keyword=${param.keyword}";
                    }
                    if ("FAILID" == result) {
                        alert(response.message + response.data);
                        return;
                    }
                },
                "error": function (response) {
                    alert(response.message + response.data);
                    return;
                }
            })
        });
        $("#batchRemoveBtn").click(function () {
            var adminArray = new Array();
            var loginAcctArray = new Array();
            $(".itembox:checked").each(function () {
                var adminId = $(this).attr("adminId");
                adminArray.push(adminId);
                var loginAcct = $(this).parent("td").next().text();
                loginAcctArray.push(loginAcct);
            });
            var requestBody = JSON.stringify(adminArray);
            // 检查adminIdArray是否包含有效数据
            if (adminArray.length == 0) {
                // 给出提示
                alert("请勾选您要删除的记录！");
                // 函数停止执行
                return;
            }
            // 给出确认提示，让用户确认是否真的删除这两条记录
            var confirmResult = confirm("您真的要删除" + loginAcctArray + "信息吗？操作不可逆，请谨慎决定！");
            // 如果用户点击了取消，那么让函数停止执行
            if (!confirmResult) {
                return;
            }
            $.ajax({
                "url": "admin/batch/delete",
                "type": "post",
                "contentType": "application/json;charset=UTF-8",
                "data": requestBody,
                "dataType": "json",
                "success": function (response) {
                    console.log(response);
                    var result = response.result;
                    if ("SUCCESS" == result) {
                        window.location.href = "admin/page/query?pageNum=" + ${requestScope['PAGE-INFO'].pageNum} +"&keyword=${param.keyword}";
                    }
                    if ("FAILID" == result) {
                        alert(response.message + response.data);
                        return;
                    }
                },
                "error": function (response) {
                    alert(response.message + response.data);
                    return;
                }
            })
        });
    });

    // 声明函数封装导航条初始化操作
    function initPagination() {
        // 声明变量存储总记录数
        var totalRecord = ${requestScope['PAGE-INFO'].total};
        // 声明变量存储分页导航条显示时的属性设置
        var paginationProperties = {
            num_edge_entries: 3,			//边缘页数
            num_display_entries: 4,		//主体页数
            callback: pageselectCallback,	//回调函数
            items_per_page: ${requestScope['PAGE-INFO'].pageSize},	//每页显示数据数量，就是pageSize
            current_page: ${requestScope['PAGE-INFO'].pageNum - 1},//当前页页码
            prev_text: "上一页",			//上一页文本
            next_text: "下一页"			//下一页文本
        };

        // 显示分页导航条
        $("#Pagination").pagination(totalRecord, paginationProperties);
    }

    // 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
    function pageselectCallback(pageIndex) {
        // pageIndex从0开始，pageNum从1开始
        var pageNum = pageIndex + 1;
        // 跳转页面
        window.location.href = "admin/page/query?pageNum=" + pageNum + "&keyword=${param.keyword}";
        return false;
    }
</script>
<body>
<%@ include file="/WEB-INF/pages/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="/admin/page/query" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summarybox" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope['PAGE-INFO'].list}">
                                <tr>
                                    <td style="text-align: center" colspan="6">
                                        抱歉，没有查询到您想要的结果!!!
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope['PAGE-INFO'].list}">
                                <c:forEach items="${requestScope['PAGE-INFO'].list}" var="admin">
                                    <tr>
                                        <td>${admin.id}</td>
                                        <td><input adminId="${admin.id}" class="itembox" type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-xs"><i
                                                    class=" glyphicon glyphicon-check"></i></button>
                                            <button type="button" class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i></button>
                                            <button type="button" adminId="${admin.id }"
                                                    class="btn btn-danger btn-xs singleDelete"><i
                                                    class="glyphicon glyphicon-remove"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
