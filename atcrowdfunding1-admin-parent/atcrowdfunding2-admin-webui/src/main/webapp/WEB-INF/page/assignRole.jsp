<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/comment/head.jsp"/>
    <script>
        $(function () {
            $("#toAssign").click(function () {
                //appendTo:把一选定元素添加到指定对象（往后面加）
                //prependTo:把一选定元素添加到指定对象（往前面面加）
                $("select:eq(0) option:selected").appendTo($("select:eq(1)"));
            });
            $("#toUnAssign").click(function () {
                $("select:eq(1) option:selected").appendTo($("select:eq(0)"));
            });
            //给设置按钮绑定函数
            $("#finishAssign").click(function () {
                // 在提交表单时，需要将所有分配的option选中，然后提交
                $("select:eq(1) option").prop("selected", true);
            });
        })
    </script>
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        .tree-closed {
            height: 40px;
        }

        .tree-expanded {
            height: auto;
        }
    </style>
</head>

<body>
<jsp:include page="/comment/navber.jsp"/>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="/comment/sidebar.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline" method="post" action="admin/do/assign.html">
                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:200px;overflow-y:auto;">
                                <c:forEach items="${requestScope.UnAssignRoles}" var="role">
                                    <option value="${role.id}">${role.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li class="btn btn-default glyphicon glyphicon-chevron-right" id="toAssign"></li>
                                <br>
                                <li class="btn btn-default glyphicon glyphicon-chevron-left" id="toUnAssign"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <input type="hidden" name="pageNum" value="${requestScope.pageNum}">
                        <input type="hidden" name="keyWord" value="${requestScope.keyWord}">
                        <input type="hidden" name="id" value="${requestScope.id}">
                        <div class="form-group" style="margin-left:40px;">
                            <label>已分配角色列表</label><br>
                            <select name="assignRoleId" class="form-control" multiple="" size="10"
                                    style="width:200px;overflow-y:auto;">
                                <c:forEach items="${requestScope.AssignRoles}" var="role">
                                    <option value="${role.id}">${role.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success" id="finishAssign">设置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
</script>
</body>
</html>

