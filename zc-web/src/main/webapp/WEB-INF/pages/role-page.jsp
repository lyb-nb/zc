<%@ page contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/pages/include-head.jsp" %>
    <link rel="stylesheet" href="/css/pagination.css">
    <script type="text/javascript" src="/script/my-role.js"></script>
    <script type="text/javascript" src="/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/script/jquery.pagination.js"></script>
    <script type="text/javascript" src="/layui/layer.js"></script>
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            initGlobalVariable();
            showPage();
            $("#searchBtn").click(function () {
                //获取输入文本框的值
                var keyword = $.trim($("#keywordInput").val());
                // 如果有效，赋值给window.keyword
                window.keyword = keyword;
                // 调用showPage()重新分页
                showPage();
            });
            $("#roleSummarybox").click(function () {
                $(".roleItembox").prop("checked", this.checked);
            });
            $("#batchRemoveBtn").click(function () {
                var length = $(".roleItembox:checked").length;
                if (length == 0) {
                    layer.msg("请至少选择一条");
                    return;
                }
                // 在全局作用域内创建roleIdArray
                window.roleIdArray = new Array();
                // 遍历$(".roleItembox:checked")
                $(".roleItembox:checked").each(function () {
                    // 通过checkbox的roleid属性获取roleId值
                    var roleId = $(this).attr("roleid");
                    // 存入数组
                    window.roleIdArray.push(roleId);
                });
                // 调用函数打开模态框
                showRemoveConfirmModal();
            });
            $("#confirmModalBtn").click(function () {
                var requestBody = JSON.stringify(window.roleIdArray);
                $.ajax({
                    "url": "role/batch/remove",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=utf-8",
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if ("SUCCESS" == result) {
                            layer.msg("操作成功");
                            //删除成功，重新执行分页
                            showPage();
                        }
                        if ("FAILID" == result) {
                            layer.msg(response.data);
                        }
                        //不论成功还是失败 模态框最终都需关闭
                        $("#confirmModal").modal("hide");
                    },
                    "error": function (response) {
                        layer.msg(response.message);
                    }
                });
            });
            // 针对.removeBtn这样动态生成的元素对象使用on()函数方式绑定单击响应函数
            // $("动态元素所依附的静态元素").on("事件类型","具体要绑定事件的动态元素的选择器", 事件响应函数);
            $("#roleTableBody").on("click", ".removeBtn", function () {
                //获取当前记录的roleId
                var roleId = $(this).attr("roleid");
                //存入到全局变量数组
                window.roleIdArray = new Array();
                window.roleIdArray.push(roleId);
                //打开模态框(后续所有操作都和批量删除一样)
                showRemoveConfirmModal();
            });
            $("#roleAddBtn").click(function () {
                $("#addModal").modal("show");
            });

            $("#addModalBtn").click(function () {
                var roleName = $.trim($("#roleNameInput").val());
                if (roleName == null || roleName == "") {
                    layer.msg("请输入有效角色名称！");
                    return;
                }
                $.ajax({
                    "url": "role/save/role",
                    "type": "post",
                    "data": {
                        "roleName": roleName
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if ("SUCCESS" == result) {
                            layer.msg("操作成功");
                            // 3.操作成功重新分页
                            // 前往最后一页
                            window.pageNum = 999999;
                            showPage();
                        }
                        if (result == "FAILED") {
                            layer.msg(response.message);
                        }
                        // 4.不管成功还是失败，关闭模态框
                        $("#addModal").modal("hide");
                        // 5.清理本次在文本框填写的数据
                        $("#roleNameInput").val("");
                    },
                    "error": function (response) {
                        layer.msg(response.message);
                    }
                });
            });

            $("#roleTableBody").on("click", ".editBtn", function () {
                // 1.获取当前按钮的roleId
                window.roleId = $(this).attr("roleId");
                // 2.获取当前按钮所在行的roleName
                var roleName = $(this).parents("tr").children("td:eq(2)").text();
                // 3.修改模态框中文本框的value值，目的是在显示roleName
                $("#roleNameInputEdit").val(roleName);
                // 4.打开模态框
                $("#editModal").modal("show");
            });
            $("#editModalBtn").click(function () {
                // 1.获取文本框值
                var roleName = $.trim($("#roleNameInputEdit").val());
                if (roleName == null || roleName == "") {
                    layer.msg("请输入有效角色名称！");
                    return;
                }
                // 2.发送请求
                $.ajax({
                    "url": "role/update/role",
                    "type": "post",
                    "data": {
                        "id": window.roleId,
                        "name": roleName
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if (result == "SUCCESS") {
                            layer.msg("操作成功！");
                            // 3.操作成功重新分页
                            showPage();
                        }
                        if (result == "FAILED") {
                            layer.msg(response.message);
                        }
                        // 4.不管成功还是失败，关闭模态框
                        $("#editModal").modal("hide");
                    }
                });
            });

        })
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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 批量删除
                    </button>
                    <button id="roleAddBtn" type="button" class="btn btn-primary" style="float:right;"
                    ><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="roleSummarybox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleTableBody">
                            <tr>
                                <td>1</td>
                                <td><input type="checkbox"></td>
                                <td>PM - 项目经理</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i
                                            class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td><input type="checkbox"></td>
                                <td>SE - 软件工程师</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i
                                            class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td><input type="checkbox"></td>
                                <td>PG - 程序员</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i
                                            class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>7</td>
                                <td><input type="checkbox"></td>
                                <td>QC - 品质控制</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i
                                            class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>8</td>
                                <td><input type="checkbox"></td>
                                <td>SA - 软件架构师</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i
                                            class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination">
                                        <!-- 这里显示分页 -->
                                    </div>
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
</div>
</div>
<%@ include file="/WEB-INF/pages/include-modal-role-confirm.jsp" %>
<%@ include file="/WEB-INF/pages/include-modal-role-add.jsp" %>
<%@ include file="/WEB-INF/pages/include-modal-role-edit.jsp" %>
</body>
</html>
