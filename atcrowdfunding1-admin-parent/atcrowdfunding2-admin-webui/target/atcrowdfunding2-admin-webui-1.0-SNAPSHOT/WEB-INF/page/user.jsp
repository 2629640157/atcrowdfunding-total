<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/comment/head.jsp"/>
    <link rel="stylesheet" href="pagination/pagination.css"/>
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
    <script type="text/javascript">
        $(function () {
            // 初始化pagination插件
            initPagination();
        })

        function initPagination() {
            var totalPages =${requestScope.adminPageInfo.total};
            $("#Pagination").pagination(totalPages, {
                num_edge_entries: 2, //边缘页数
                num_display_entries: 5,//主体页数
                callback: pageSelectCallback,
                items_per_page: 10, //每页显示1项
                current_page: ${requestScope.adminPageInfo.pageNum}-1, //当前页
                prev_text: "上一页",
                next_text: "下一页"
            })
        }

        function pageSelectCallback(pageNum) {
            <%--this.href="admin/to/user.html?pageNum=${requestScope.adminPageInfo.pageNum}&keyWord=${param.keyWord}";--%>
            // pageNum表示点击的页码数  注意： pageNum 从0开始的
            window.location.href = "admin/to/user.html?pageNum=" + (pageNum + 1) + "&keyWord=${param.keyWord}"
            // 取消按钮的默认行为
            return false;
        }
    </script>
</head>

<body>
<jsp:include page="/comment/navber.jsp"/>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="/comment/sidebar.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
            </div>
            <div class="panel-body">
                <form class="form-inline" role="form" style="float:left;" method="get" action="">
                    <div class="form-group has-feedback">
                        <div class="input-group">
                            <div class="input-group-addon">查询条件</div>
                            <input class="form-control has-success" name="keyWord" value="${param.keyWord}" type="text"
                                   placeholder="请输入查询条件">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                </form>
                <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                        class=" glyphicon glyphicon-remove"></i> 删除
                </button>
                <a type="button" href="admin/to/add.html" class="btn btn-primary" style="float:right;"
                   onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                </a>
                <br>
                <hr style="clear:both;">
                <div class="table-responsive">
                    <c:if test="${empty requestScope.adminPageInfo.list}">
                        抱歉！没有查询到相关数据！
                    </c:if>
                    <c:if test="${!empty requestScope.adminPageInfo.list}">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.adminPageInfo.list}" var="admin" varStatus="status">
                                <tr>
                                    <td>${status.index+1}</td>
                                    <td><input type="checkbox"></td>
                                    <td>${admin.loginAcct}</td>
                                    <td>${admin.userName}</td>
                                    <td>${admin.email}</td>
                                    <td>
                                        <a type="button"
                                           href="admin/to/assign.html?id=${admin.id}&pageNum=${requestScope.adminPageInfo.pageNum}&keyWord=${param.keyWord}"
                                           class="btn btn-success btn-xs"><i
                                                class=" glyphicon glyphicon-check"></i></a>
                                        <a type="button"
                                           href="admin/to/update.html?id=${admin.id}&pageNum=${requestScope.adminPageInfo.pageNum}&keyWord=${param.keyWord}"
                                           class="btn btn-primary btn-xs"><i
                                                class=" glyphicon glyphicon-pencil"></i></a>
                                        <a type="button"
                                           href="admin/do/remove.html?id=${admin.id}&pageNum=${requestScope.adminPageInfo.pageNum}&keyWord=${param.keyWord}"
                                           class="btn btn-danger btn-xs"><i
                                                class=" glyphicon glyphicon-remove"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                        <%--<ul class="pagination">
                                            <c:if test="${requestScope.adminPageInfo.hasPreviousPage}">
                                                <li ><a href="admin/to/user.html?pageNum=${requestScope.adminPageInfo.prePage}&keyWord=${param.keyWord}">上一页</a></li>
                                            </c:if>
                                            <c:if test="${!requestScope.adminPageInfo.hasPreviousPage}">
                                                <li class="disabled"><a href="javaScript:return false">上一页</a></li>
                                            </c:if>
                                            <c:forEach var="num" items="${requestScope.adminPageInfo.navigatepageNums}">
                                                <c:if test="${num != requestScope.adminPageInfo.pageNum}">
                                                    <li ><a href="admin/to/user.html?pageNum=${num}&keyWord=${param.keyWord}">${num}</a></li>
                                                </c:if>
                                                <c:if test="${num == requestScope.adminPageInfo.pageNum}">
                                                    <li class="active"><a href="admin/to/user.html?pageNum=${num}&keyWord=${param.keyWord}">${num} <span class="sr-only">(current)</span></a></li>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${requestScope.adminPageInfo.hasNextPage}">
                                                <li ><a href="admin/to/user.html?pageNum=${requestScope.adminPageInfo.nextPage}&keyWord=${param.keyWord}">下一页</a></li>
                                            </c:if>
                                            <c:if test="${!requestScope.adminPageInfo.hasNextPage}">
                                                <li class="disabled"><a href="javaScript:return false">下一页</a></li>
                                            </c:if>
                                        </ul>--%>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script type="text/javascript" src="pagination/jquery.pagination.js"></script>
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

