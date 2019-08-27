<%@ page contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/pages/include-head.jsp" %>
</head>
<body>

<%@ include file="/WEB-INF/pages/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/pages/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="admin/page/query">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">
                    表单数据
                </div>
                <div class="panel-body">
                    <form action="admin/save" method="post" role="form">
                        <div class="form-group">
                            <label for="loginAcct1">登录账号</label>
                            <input type="text" name="loginAcct" class="form-control" id="loginAcct1"
                                   placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="username1">用户昵称</label>
                            <input type="text" name="userName" class="form-control" id="username1"
                                   placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="password1">用户密码</label>
                            <input type="text" name="userPswd" class="form-control" id="password1"
                                   placeholder="请输入用户密码">
                        </div>
                        <div class="form-group">
                            <label for="email1">邮箱地址</label>
                            <input type="email" name="email" class="form-control" id="email1" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增
                        </button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

</body>
</html>
