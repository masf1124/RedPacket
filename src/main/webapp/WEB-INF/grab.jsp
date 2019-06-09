<%--
  Created by IntelliJ IDEA.
  User: masf
  Date: 2019-06-05
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transactional//EN"
            "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>参数</title>
    <!--加载Query文件-->

    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            //模拟30000个异步请求，进行并发
            var max = 30000;
            for(var i=0;i<max;i++){
                //jQuery的post请求，请注意是异步请求
                $.post({
                    //请求抢ID 为1的红包
                    url:"http://localhost:9100/userRedPacket/grabRedPacketForVersion.do?redPacketId=5&userId="+i,
                    success:function(result){}
                });
            }
        });


    </script>
</head>
<body>

</body>
</html>
