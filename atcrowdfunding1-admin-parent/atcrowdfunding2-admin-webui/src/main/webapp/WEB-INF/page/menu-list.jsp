<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/comment/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="ztree/zTreeStyle.css">
    <script type="text/javascript">

        //zTree的一些节点信息
        /*var zNodes = [
            {
                name: "父节点1 - 展开", open: true,
                children: [
                    {
                        name: "父节点11 - 折叠",
                        children: [
                            {name: "叶子节点111"},
                            {name: "叶子节点112"},
                            {name: "叶子节点113"},
                            {name: "叶子节点114"}
                        ]
                    },
                    {
                        name: "父节点12 - 折叠",
                        children: [
                            {name: "叶子节点121"},
                            {name: "叶子节点122"},
                            {name: "叶子节点123"},
                            {name: "叶子节点124"}
                        ]
                    },
                    {name: "父节点13 - 没有子节点", isParent: true}
                ]
            },
            {name: "父节点3 - 没有子节点", isParent: true}

        ];*/
        //
        $(document).ready(function () {
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
            generatorMenu();
            //给保存按钮绑定函数
            $("#menuSaveBtn").click(function () {
                //    获取pid  roleName url ico
                var pid = window.id;
                var name = $("#menuAddModal [name=name]").val();
                var url = $("#menuAddModal [name=url]").val();
                var ico = $("#menuAddModal [name=icon]:checked").val();
                //发送ajax请求执行保存操作
                $.ajax({
                    url: "menu/insertMenu.json",
                    type: "post",
                    data: {
                        pId: pid,
                        name: name,
                        url: url,
                        ico: ico
                    },
                    success: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        //    清理用户输入的数据(调用重置按钮)
                        layer.msg("添加成功！", {time: 2000, icon: 1, shift: 4});
                        $("#menuResetBtn").click();
                        /*  $("#menuSaveBtn [name=name]").empty();
                          $("#menuSaveBtn [name=url]").empty();
                          $("#menuSaveBtn [name=icon]:checked").empty();*/
                        //    隐藏模态框
                        $("#menuAddModal").modal('hide');
                        //    重新加载整棵树
                        generatorMenu();
                    },
                    error: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                    }
                });

            });
            //给OK按钮绑定函数
            $("#confirmBtn").click(function () {
                $.ajax({
                    url: "menu/deleteMenu.json",
                    type: "post",
                    data: {"id": window.id},
                    success: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        //    清理用户输入的数据(调用重置按钮)
                        layer.msg("删除成功！", {time: 2000, icon: 1, shift: 4});
                        //    隐藏模态框
                        $("#menuConfirmModal").modal('hide');
                        //    重新加载整棵树
                        generatorMenu();
                    },
                    error: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                    }
                })
            });
            //为更新按钮绑定函数
            $("#menuEditBtn").click(function () {
                //获取用户填写的数据
                var name = $("#menuEditModal [name=name]").val();
                var url = $("#menuEditModal [name=url]").val();
                var ico = $("#menuEditModal [name=icon]:checked").val();
                $.ajax({
                    url: "menu/updateMenu.json",
                    type: "post",
                    data: {
                        "id": window.id,
                        "name": name,
                        "url": url,
                        "ico": ico
                    },
                    dataType: "json",
                    success: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }

                        //    隐藏模态框
                        $("#menuEditModal").modal('hide');
                        layer.msg("修改成功！", {time: 2000, icon: 1, shift: 4});
                        //    重新加载整棵树
                        generatorMenu();
                    },
                    error: function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                    }
                })
            });
        });

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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <%--使用ztree生成树的位置--%>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="modal-menu-add.jsp"></jsp:include>
<jsp:include page="modal-menu-confirm.jsp"></jsp:include>
<jsp:include page="modal-menu-edit.jsp"></jsp:include>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script src="js/menu-list.js"></script>
<script src="layer/layer.js"></script>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"/>
<script type="text/javascript">
    /* $(function () {

     });*/
</script>
</body>
</html>

