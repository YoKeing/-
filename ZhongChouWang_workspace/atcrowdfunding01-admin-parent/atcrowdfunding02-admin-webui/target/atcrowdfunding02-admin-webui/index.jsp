<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>index</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">

    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function (){



            $("#btn1").click(function (){
                $.ajax({
                    "url": "send/array/one.html",
                    "type": "post",
                    "data": {
                        "array": [5, 8, 12]
                    },
                    "dataType": "text",
                    "success": function (response){
                        alert(response);
                    },
                    "error": function (response){
                        alert(response);
                    }
                });
            });

            $("#btn2").click(function (){
                $.ajax({
                    "url": "send/array/two.html",
                    "type": "post",
                    "data": {
                        "array": [5, 8, 12]
                    },
                    "dataType": "text",
                    "success": function (response){
                        alert(response);
                    },
                    "error": function (response){
                        alert(response);
                    }
                });
            });

            $("#btn3").click(function (){
                var array = [5, 8, 12];
                var requestBody = JSON.stringify(array);
                $.ajax({
                    "url": "send/array/three.html",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "text",
                    "success": function (response){
                        alert(response);
                    },
                    "error": function (response){
                        alert(response);
                    }
                });
            });

            $("#btn4").click(function (){
                layer.msg("layer的弹窗！");
            });

        });
    </script>

</head>
<body>
    <h2>Hello World!</h2>

    <a href="test/ssm.html">测试SSM整合环境</a>
    <br>

    <button id="btn1">Send [5, 8, 12] One</button>
    <br>
    <br>

    <button id="btn2">Send [5, 8, 12] Two</button>
    <br>
    <br>

    <button id="btn3">Send [5, 8, 12] Three</button>

    <br>
    <br>

    <button id="btn5">Send Compose Object</button>

    <br>
    <br>

    <button id="btn4">layer 弹窗</button>

</body>
</html>
