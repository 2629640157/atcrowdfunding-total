<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/comment/head.jsp"/>
    <link rel="stylesheet" href="pagination/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="ztree/zTreeStyle.css">
    <script type="text/javascript" src="js/role-list.js"></script>
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
        $(function () {

            //准备数据 分页要的数据：1.页面 2.页面大小 3.keyWord
            window.pageNum = 1;
            window.pageSize = 2;
            window.keyWord = "";
            //调用生成页面信息函数
            generatorPage();
            //为queryBtn绑定单击响应函数
            $("#queryBtn").click(function () {
                //    获取queryInput的value值
                var keyWord = $("#queryInput").val();
                window.keyWord = keyWord;
                generatorPage();
            })
            //    为insertBtn(添加按钮)绑定响应函数
            $("#insertBtn").click(function () {
                //    显示模态框
                $("#roleInputModal").modal('show');
            })
            //    为saveBtn（保存按钮）绑定响应函数
            $("#saveBtn").click(function () {
                //    获取用户输入的角色信息
                var roleName = $("#inputSuccess4").val();
                //  发送ajax请求
                $.ajax({
                    url: "role/input.json",
                    type: "post",
                    data: {"roleName": roleName},
                    dataType: "json",
                    success: function (response) {
                        console.log(response);
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        // 如果成功
                        //1.需要将模态框隐藏
                        $("#roleInputModal").modal('hide');
                        //2.将模态框表单中用户输入的内容清空
                        $("#inputSuccess4").val('');
                        //3.调用generatorPage函数重新加载页面内容
                        window.pageNum = 4000;
                        generatorPage();
                        //4.需要将用户插入数据显示给用户
                        //将页面切换到最后一页
                    },
                    error: function (response) {
                        layer.msg("添加失败，请联系管理员！", {time: 2000, icon: 2, shift: 6});
                    }
                })
            })
            //    为editBtn（修改按钮）绑定函数
            /* $(".editBtn").click(function () {
                 $("#roleUpdateModal").modal('show');
                 //    回显模态框表单中的roleName
                 var roleName = $(this).parent().prev().text();
                 $("#updateInput").val(roleName);
                 //    获取角色id
                 window.roleId=this.id;
             })*/
            //以上方式进行响应时间的绑定对于使用ajax请求重新插入元素而言不好用
            //可以使用.on()为对象绑定函数
            //为插入元素的容器元素绑定on函数
            $("#roleTbody").on("click", ".editBtn", function () {
                $("#roleUpdateModal").modal('show');
                //    回显模态框表单中的roleName
                var roleName = $(this).parent().prev().text();
                $("#updateInput").val(roleName);
                //    获取角色id
                window.roleId = this.id;

            })
            //    为修改按钮绑定函数
            $("#updateBtn").click(function () {
                //    获取用户输入的角色信息
                var roleName = $("#updateInput").val();
                //  发送ajax请求
                $.ajax({
                    url: "role/update.json",
                    type: "post",
                    data: {"id": window.roleId, "roleName": roleName},
                    dataType: "json",
                    success: function (response) {
                        console.log(response);
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        // 如果成功
                        //1.需要将模态框隐藏
                        $("#roleUpdateModal").modal('hide');
                        //2.将模态框表单中用户输入的内容清空
                        $("#updateInput").val('');
                        //3.调用generatorPage函数重新加载页面内容
                        generatorPage();

                    },
                    error: function (response) {
                    }
                })
            })

            //    为parentCkbox绑定响应函数
            $("#parentCkbox").click(function () {
                //    判断当前这个checkbox的选中状态
                var flag = this.checked
                var boxList = $(".childCkbox");
                $.each(boxList, function (index, obj) {
                    obj.checked = flag
                })
            })
            //    当点击每一个checkbox时需要判断是否每一个都被选中了，如果都选中了让parentCkbox选中。
            $("#roleTbody").on("click", ".childCkbox", function () {
                //   当点击每一个checkbox时需要判断是否每一个都被选中了，如果都选中了让parentCkbox选中。
                var boxList = $(".childCkbox");
                //遍历前，将parentCkbox的选中状态设为true
                $("#parentCkbox")[0].checked = true;
                $.each(boxList, function (index, obj) {
                    if (!obj.checked) {
                        $("#parentCkbox")[0].checked = false;
                    }
                })
            })
            //    给deleteMoreBtn （删除按钮）绑定响应函数
            $("#deleteMoreBtn").click(function () {
                //优化：1.点击批量删除按钮，给用户一个提示，是否继续删除
                //2.判断用户是否被选中

                //创建一个数组用于保存选中的roleId
                var roleIdList = [];
                var roleNameList = [];
                var boxList = $(".childCkbox");
                $.each(boxList, function (index, obj) {
                    if (obj.checked) {
                        //    如果选中了 将checkbox的name属性获取，添加到roleIdList
                        roleIdList.push(obj.name);
                        roleNameList.push(obj.alt);
                    }
                });
                //判断用户是否被选中
                if (roleIdList.length <= 0) {
                    layer.msg("请选中需要删除的角色！", {time: 2000, icon: 0, shift: 6});
                    return;
                }
                //给用户一个模态框，用户显示需要删除角色的roleName
                var str = "";
                for (var i = 0; i < roleNameList.length; i++) {
                    str = str + "<span>" + roleNameList[i] + "</span> &nbsp; &nbsp;&nbsp; &nbsp;"
                }
                $("#deleteRoleNames").append(str);
                //显示模态框
                $("#roleDeleteModal").modal('show');
                console.log(roleIdList);
                // deleteMoreRoles(roleIdList)
            });
            //给×按钮绑定函数
            $("#deleteClose").click(function () {
                //取消的时候也清空
                $("#deleteRoleNames").empty();
            });
            //给确认按钮绑定函数
            $("#deleteAffirm").click(function () {
                var roleIdList = [];
                var boxList = $(".childCkbox");
                $.each(boxList, function (index, obj) {
                    if (obj.checked) {
                        //    如果选中了 将checkbox的name属性获取，添加到roleIdList
                        roleIdList.push(obj.name);
                    }
                });
                deleteMoreRoles(roleIdList);
                //关闭模态框
                $("#roleDeleteModal").modal('hide');
                //    清空数据
                $("#deleteRoleNames").empty();
                //调用生成页面信息函数
                generatorPage();
            });
            //给单个删除按钮绑定函数
            $("#roleTbody").on('click', '.deleteOne', function () {
                var roleName = $(this).parent().prev().text();
                //获取id的值
                var id = this.id;
                layer.confirm("您是否要删除" + roleName + "角色?", {icon: 3, title: '提示'}, function (cindex) {
                    // 将id封装成一个数组
                    var roleIdList = [id];
                    deleteMoreRoles(roleIdList);
                    //调用生成页面信息函数
                    generatorPage();
                    layer.close(cindex);
                }, function (cindex) {
                    layer.close(cindex);
                });

            });
            //给分配按钮绑定函数
            $("#saveAssign").click(function () {

                //    获取用户选定权限id
                var treeObj = $.fn.zTree.getZTreeObj("authTree");
                var nodes = treeObj.getCheckedNodes(true);
                //    插入到数据库的数据只有权限id
                var authIds = [];
                for (var i = 0; i < nodes.length; i++) {
                    authIds.push(nodes[i].id);
                }
                var dataObj = {
                    "authIds": authIds,
                    "id": [window.id]
                };

                var dataStr = JSON.stringify(dataObj);

                //    发送ajax请求

                $.ajax({
                    url: "auth/do/updateAuth.json",
                    type: "post",
                    data: dataStr,
                    contentType: "application/json;charset=UTF-8",
                    success: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        //    隐藏模态框
                        $("#assignShowModal").modal('hide');
                        layer.msg("分配成功！", {time: 2000, icon: 1, shift: 1});
                        //    重新加载页面信息
                        generatorPage();
                    },
                    error: function (response) {
                        layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                    }
                })

            });

            function deleteMoreRoles(roleIdList) {
                var jsonStr = JSON.stringify(roleIdList);
                $.ajax({
                    url: "role/delete.json",
                    type: "post",
                    data: jsonStr,
                    contentType: "application/json",
                    dateType: "json",
                    success: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        layer.msg("删除成功！", {time: 2000, icon: 1, shift: 1});
                        //调用generatorPage函数重新加载页面内容
                        generatorPage();
                    },
                    error: function (response) {
                        layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});

                    }
                })

            }
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" id="queryInput" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="queryBtn" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="deleteMoreBtn" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除

                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            id="insertBtn"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="parentCkbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleTbody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination">
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
<jsp:include page="role-modal-input.jsp"/>
<jsp:include page="role-modal-update.jsp"/>
<jsp:include page="role-modal-delete.jsp"/>
<jsp:include page="modal-role-assign.jsp"/>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script src="layer/layer.js"></script>
<script type="text/javascript" src="pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"/>
<script type="text/javascript">
</script>
</body>
</html>

