function generatorMenu() {
    var setting = {
        data: {
            key: {
                url: "xxxL"
            }
        },
        view: {
            //指定是否可以指定多个菜单
            selectedMulti: false,
            /*
            treeNode： 代表当前节点的json数据对象
             */
            addDiyDom: function (treeId, treeNode) {
                var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                if (treeNode.ico) {
                    icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.ico).css("background", "");
                }
            },

            addHoverDom: function (treeId, treeNode) {
                var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) return;
                var s = '<span id="btnGroup' + treeNode.tId + '">';
                if (treeNode.level == 0) {// 如果是根节点 如何添加控件
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="' + treeNode.id + '" onclick="toAddMidal(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if (treeNode.level == 1) {// 如果是分支节点 如何添加控件
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" id="' + treeNode.id + '" onclick="toUpdateMidal(this)"  href="javaScript:;" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    if (treeNode.children.length == 0) {// 判断是否有子节点，如果没有子节点，则该节点可以删除
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" id="' + treeNode.id + '" onclick="toDeleteMidal(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="' + treeNode.id + '"  onclick="toAddMidal(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if (treeNode.level == 2) { // 如果是叶子节点，如何添加控件
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" id="' + treeNode.id + '" onclick="toUpdateMidal(this)" href="javaScript:;" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="' + treeNode.id + '" onclick="toDeleteMidal(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                }

                s += '</span>';
                aObj.after(s);
            },
            //当鼠标移除之后执行的函数
            removeHoverDom: function (treeId, treeNode) {
                // 将加入的控件移除
                $("#btnGroup" + treeNode.tId).remove();
            }
        },
    };
    $.ajax({
        url: "menu/getAllMenus.json",
        type: "get",
        dataType: "json",
        success: function (response) {
            if (response.operationResult == "FAILED") {
                layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                return;
            }
            $.fn.zTree.init($("#treeDemo"), setting, response.queryData);
        },
        error: function (response) {
            if (response.operationResult == "FAILED") {
                layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                return;
            }
        }
    })
}

//给添加按钮绑定函数
function toAddMidal(obj) {
    $("#menuAddModal").modal('show');
    window.id = obj.id;
}

//给删除按钮绑定函数
function toDeleteMidal(obj) {
    $("#menuConfirmModal").modal('show');
    window.id = obj.id;
}

//给修改按钮绑定函数
function toUpdateMidal(obj) {
    $("#menuEditModal").modal('show');
    window.id = obj.id;
//    回显数据
//    获取数据
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var node = treeObj.getNodeByParam("id", window.id, null);
    //为模态框赋值
    $("#menuEditModal [name=name]").val(node.name);
    $("#menuEditModal [name=url]").val(node.url);
    //为单选按钮赋值，注意：使用val()为单选按钮赋值或复选框赋值时需要传入一个数组对象
    $("#menuEditModal [name=icon]").val([node.ico]);
    // alert([node.icon])
}