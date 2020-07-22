<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://"+
	request.getServerName() + ":" + request.getServerPort() +
	request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js "></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

	<script type="text/javascript" >
		$(function () {
			// 初始清空
			$("#userAct").html("");
			$("#userPwd").html("");
			// 初始获得焦点
            $("#userAct").focus();
            // 获取用户名和密码并进行判断是否为空,，并去除空格
			$("#submitbtn").click(function () {
				login();
			})
			// 键盘敲击登录
			$(window).keydown(function (event) {
				if (event.keyCode==13){
					// 登录操作
					login();
				}
			})
		})
        // 登录操作
		function login() {
			 var userAct =  $("#userAct").val();
			var userPwd = $("#userPwd").val();
			//alert(usrAct);
			if(userAct==""){
				$("#msg").html("用户名不能为空！");
			}
			if(userPwd==""){
				$("#msg").html("密码不能为空！");
			}
			// 如果填写了账户和密码后，就应该接下来在后台进行验证了；
			// 这里的数据名称不能写错，例如不能把data写成date
			$.ajax({
				url: "settings/user/login.do",
				data:{
					"userAct":userAct,
					"userPwd":userPwd
				},
				type:"post",
				dataType:"json",
				success:function (data) {
					// 对于这里的返回数据进行判断
					if (data.success){
						// 跳转到欢迎页面
						window.location.href="workbench/index.jsp";
					}else {
						// 输出错误信息
						//$("#msg").html(data.msg);
						// 这里现在还有问题，暂时这样
						//window.location.href="workbench/index.jsp";
					}
				}
			})
		}
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="userAct"/>
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="userPwd"/>
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
					<button type="button" id="submitbtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>