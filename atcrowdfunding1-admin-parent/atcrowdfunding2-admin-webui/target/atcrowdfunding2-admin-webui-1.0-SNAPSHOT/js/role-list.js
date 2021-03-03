//在这个js文件中，会创建生成页面面的函数，方便role-list.jsp使用
//生成页面的函数调用以下函数
function generatorPage() {
    var rolePageInfo = getRoleInfoByRemote();
    fullTable(rolePageInfo);

}

//获取数据的函数
function getRoleInfoByRemote() {

    var roleList = null;
    var ajaxResult = $.ajax({
        "url": "role/get/roles.json",
        "type": "post",
        "data": {
            "keyWord": window.keyWord,
            "pageNum": window.pageNum,
            "pageSize": window.pageSize
        },
        "async": false, // 发送的请求是一个异步的请求
        "dataType": "json"//指定返回数据的类型
    });
    var result = ajaxResult.responseJSON;
    if (ajaxResult.status != 200) {
        // 使用 layer 进行弹窗
        //alert("1234")
        /*
        time属性： 指定弹窗的出现时间
        icon属性： 指定弹窗显示的图标
        shift属性： 指定弹窗的动作
         */
        layer.msg("获取数据失败，请联系管理员！", {time: 2000, icon: 2, shift: 6});
        return;
    }
    // 请求没有问题
    // 请求状态码是200不一定就有数据，可能后台代码出现异常。
    if (result.operationResult == "FAILED") {
        layer.msg(result.operationMessage, {time: 2000, icon: 2, shift: 6});
        return;
    }
    return result.queryData;
}

//填充表格函数
function fullTable(rolePageInfo) {
    $("#roleTbody").empty();
    //获取tbody对象
    var str = "";
    if (rolePageInfo == undefined) {
        return;
    }
    for (var i = 0; i < rolePageInfo.list.length; i++) {
        var role = rolePageInfo.list[i];
        var roleId = role.id;
        var roleName = role.roleName;
        str = str + "<tr>" +
            "                                <td>" + (i + 1) + "</td>" +
            "                                <td><input class='childCkbox' name='" + roleId + "'   type=\"checkbox\" alt='" + roleName + "'></td>" +
            "                                <td>" + roleName + "</td>" +
            "                                <td>" +
            "                                    <button type=\"button\" id='" + roleId + "' onclick='showAssign(this)' class=\"btn btn-success btn-xs\" ><i class=\" glyphicon glyphicon-check\"></i></button>" +
            "                                    <button type=\"button\" id='" + roleId + "' class=\"btn btn-primary btn-xs editBtn\"><i class=\" glyphicon glyphicon-pencil\"></i></button>" +
            "                                    <button type=\"button\" id='" + roleId + "' class=\"btn btn-danger btn-xs deleteOne\"><i class=\" glyphicon glyphicon-remove\"></i></button>" +
            "                                </td>" +
            "                            </tr>"
    }
    $("#roleTbody").append(str);
    //初始化导航条
    var totalPages = rolePageInfo.total;
    $("#Pagination").pagination(totalPages, {
        num_edge_entries: 2, //边缘页数
        num_display_entries: 5,//主体页数
        callback: pageSelectCallback,
        items_per_page: 2, //每页显示1项
        current_page: rolePageInfo.pageNum - 1, //当前页
        prev_text: "上一页",
        next_text: "下一页"
    });

}

/*//生成pagination函数
function generatorPagination() {

}*/

//pagination回调函数  点击分页按钮时触发的
function pageSelectCallback(pageNum, jquery) {
    //切换页码
    window.pageNum = pageNum + 1;
    generatorPage();
    // 取消按钮的默认行为
    return false;
}

//为分配权限按钮绑定函数
function showAssign(obj) {
    $("#assignShowModal").modal('show');
    window.id = obj.id;
//    加载权限的数据，将权限数据使用zTree显示到模态框中
    var result = $.ajax({
        url: "auth/do/getAll.json",
        type: "post",
        dataType: "json",
        async: false
    });
    if (result.status != 200) {
        layer.msg("获取数据失败，请联系管理员！", {time: 2000, icon: 2, shift: 6});
        return;
    }
    if (result.responseJSON.operationResult == "FAILED") {
        layer.msg("获取数据失败，请联系管理员！", {time: 2000, icon: 2, shift: 6});
        return;
    }
    var authList = result.responseJSON.queryData;
    var setting = {
        data: {
            simpleData: {
                enable: true,  //启动zTree管理数据  比如：父子节点的管理
                pIdKey: "categoryId"
            },
            key: {
                name: "title"  //这个属性表示使用节点数据中的那个属性作为树节点显示类容
            },
        },
        check: {
            enable: true,//是否在节点前面显示复选框或单选框
        }
    };
    $.fn.zTree.init($("#authTree"), setting, authList);
    //获取树对象
    var treeObj = $.fn.zTree.getZTreeObj("authTree");
    treeObj.expandAll(true);//true表示展开树 false反之
//    获取当前节点角色拥有的权限，并在树中回显（即在对应的地方打√）
//    发送ajax请求查询
    result = $.ajax({
        url: "auth/do/getAuthByRoleId.json",
        type: "post",
        data: {id: obj.id},
        dataType: "json",
        async: false
    });
    if (result.status != 200) {
        layer.msg(result.responseJSON.operationMessage, {time: 2000, icon: 2, shift: 6});
        return;
    }
    if (result.responseJSON.operationResult == "FAILED") {
        layer.msg(result.responseJSON.operationMessage, {time: 2000, icon: 2, shift: 6});
        return;
    }
    //获取节点当前数据
    var authIds = result.responseJSON.queryData;
    //遍历获取的authId
    $.each(authIds, function (index, authId) {
        //    根据authId查找树中对应的数据
        //     getNodeByParam() 参数1：查找属性名 参数2：查找属性  参数3：查找范围 null表示整棵树
        var node = treeObj.getNodeByParam("id", authId, null);
        treeObj.checkNode(node, true, false);
    })
}

/* var ajaxResult= $.ajax({
        url: "role/get/roles.json",
        type:"post",
        date: {
            "keyWord":window.keyWord,
            "pageNum":window.pageNum,
            "pageSize":window.pageSize
        },
        dateType:"json",  //指定返回数据的类型
        success:function(result){

        },
        error:function (result) {

        }
    });
   console.log(ajaxResult);
   if (ajaxResult.status!=200){
       /!*
        time属性： 指定弹窗的出现时间
        icon属性： 指定弹窗显示的图标
        shift属性： 指定弹窗的动作
         *!/
       layer.msg("获取数据失败，请联系管理员！",{time:2000, icon:2, shift:6});

   }
    return ajaxResult;*/