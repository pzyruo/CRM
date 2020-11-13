
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
            $.ajax({
                url:"settings/user/login.do",
                data:{
                "loginAct":loginAct,
                "loginPwd":loginPwd
            },
            type:"post",
            dataType:"json",
            success:function (data){
</body>
</html>
