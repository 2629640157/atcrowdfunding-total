<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--
    base标签的作用： 在页面中所有的相对路径对相对于base标签中的路径进行定位
    --%>
    <%--<base href="http://localhost:8080/crowd/">--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <%--导入jquery库--%>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <%--发送Ajax请求--%>
    <script type="text/javascript">
        $(function () {
            $("#testAjax").click(function () {
                // 发送ajax请求
                $.ajax({
                    url: this.href,
                    data: {a: 1},
                    type: "post",
                    success: function (result) {
                        alert(result)
                    },
                    fail: function (result) {
                        alert(result)
                    }
                });
                return false;
            });

            $("#testJson1").click(function () {
                // 发送ajax请求
                $.ajax({
                    url: this.href,
                    data: {
                        empIdList: [3, 4, 7]
                    },
                    type: "post",
                    success: function (result) {
                        alert(result)
                    },
                    fail: function (result) {
                        alert(result)
                    }
                });
                return false;
            });

            // 推荐使用ajax发送json的方式
            $("#testJson2").click(function () {

                var empList = [3, 4, 7];
                var jsonData = JSON.stringify(empList);
                // alert(jsonData)

                // 发送ajax请求
                $.ajax({
                    url: this.href,
                    data: jsonData,
                    contentType: "application/json;charset=UTF-8", // 指定发送数据的内容类型，如果发送的是JSON数据，必须指定
                    type: "post",
                    success: function (result) {
                        alert(result)
                    },
                    fail: function (result) {
                        alert(result)
                    }
                });
                return false;
            });
        })
    </script>
</head>
<body>
<a href="test/test.html">aaa</a>
<br>
获取协议名称：${pageContext.request.scheme}
<br>
主机名: ${pageContext.request.serverName}
<br>
端口号： ${pageContext.request.serverPort}
<br>
项目名: ${pageContext.request.contextPath}
<br>
<a href="test/json.json" id="testAjax">Send Ajax Request</a>
<br>
<a href="test/jsonWithData1.json" id="testJson1">Send Ajax Whit Json One</a>
<br>
<a href="test/jsonWithData2.json" id="testJson2">Send Ajax Whit Json Two</a>

</body>
</html>
