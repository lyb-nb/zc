<%@ page contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/pages/include-head.jsp" %>
    <link rel="stylesheet" href="../../css/pagination.css">
    <script type="text/javascript" src="../../script/jquery.min.js"></script>
    <script type="text/javascript" src="../../script/jquery.pagination.js"></script>
    <script type="text/javascript" src="../../script/my-admin.js"></script>
    <script type="text/javascript">
        $(function () {
            window.totalRecord =${requestScope['PAGE-INFO'].total};
            window.pageNum =${requestScope['PAGE-INFO'].pageNum};
            window.pageSize =${requestScope['PAGE-INFO'].pageSize};
            window.keyword = "${param.keyword}";
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
                var confirmResult = confirm("您真的要删除" + loginAcct + "这条记录吗？");
                if (!confirmResult) {
                    return;
                }
                batchDelete(adminArray);
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
                batchDelete(adminArray);
            });
        });
    </script>
</head>
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
                    <a class="btn btn-primary" style="float:right;"
                       href="to/admin/add"><i class="glyphicon glyphicon-plus"></i> 新增
                    </a>
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
                                            <a href="to/admin/update?adminId=${admin.id}&pageNum=${requestScope['PAGE-INFO'].pageNum}"
                                               class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i></a>
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
