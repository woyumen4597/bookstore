<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>注册表单</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js">

</script>
<script type="text/javascript">
	function checkpass() {
		var userPass = $("#password").val();
		var pts = document.getElementById("passts");

		if (userPass.length<6 || userPass.length >15) {
			pts.innerHTML = "密码长度须在6-15之间!";
			pts.style.color = "red";
			return false;
		}
		pts.innerHTML = "密码可以使用!";
		pts.style.color = "green";
		return true;
	}

	function checkname() {
		var name = document.getElementById("username").value;
		var ts = document.getElementById("namets");
		if (name.length<3|| name.length>15) {
			ts.innerHTML = "用户名长度须在3-15之间!";
			ts.style.color = "red";
			return false;
		}
		$.post("${pageContext.request.contextPath}/client/CheckNameServlet", {
			"username" : name
		}, function(data) {
			var d = $.parseJSON(data);
			if (d.success == true) {
				ts.innerHTML = '用户名已存在!';
				ts.style.color = 'red';
				return false;
			}
		});
		ts.innerHTML = '用户名可以使用！';
		ts.style.color = 'green';
		return true;
	}
	function checkconfirm() {
		var password = $("#password").val();
		var confirm = $("#confirm").val();
		var confirmts = document.getElementById("confirmts");
		if (password != confirm) {
			confirmts.innerHTML = "两次密码输入不一致!";
			confirmts.style.color = "red";
			return false;
		}
		confirmts.innerHTML = "输入一致!";
		confirmts.style.color = "green";
		return true;
	}
	function checkemail() {
		var email = $("#email").val();
		var ets = document.getElementById("emailts");
		if (!isEmail(email)) {
			ets.innerHTML = "邮箱格式不正确!";
			ets.style.color = "red";
			return false;
		}
		ets.innerHTML = "邮箱可以使用!";
		ets.style.color = "green";
		return true;
	}
	function isEmail(str) {
		var reg = /[a-z0-9-]{1,30}@[a-z0-9-]{1,65}.[a-z]{3}/;
		return reg.test(str);
	}
	function checkNull(id) {
		var val = $("#" + id).val();
		var ts = document.getElementById(id + "ts");
		if (val == null || val == "") {
			ts.innerHTML = id + "不能为空";
			ts.style.color = "red";
		} else {
			ts.innerHTML = "校验通过";
			ts.style.color = "green";
		}
	}
	function register() {
		if (!checkname()) {
			return false;
		} else if (!checkpass()) {
			return false;
		} else if (!checkemail()) {
			return false;
		} else if (!codeConfirm()) {
			return false;
		}
		return true;
	}

	function emailConfirm() {
		var email = $("#email").val();
		var code = document.getElementById("code");
		if (email == null || email == "") {
			alert("请填写邮箱地址");
		} else {
			$.post("${pageContext.request.contextPath}/client/CodeServlet", {
				"email" : email
			}, function(data) {
				var d = $.parseJSON(data);
				if (d.success == true) {
					code.innerHTML = d.code;
					return true;
				}
			});
		}
	}
	function codeConfirm() {
		var emailCode = $("#emailCode").val();
		var code = document.getElementById("code");
		var codets = document.getElementById("codets");
		if (emailCode != code.innerHTML) {
			codets.innerHTML = "验证码输入错误";
			codets.style.color = "red";
			return false;
		}else if(emailCode==null||emailCode==""||code==null||code==""){
			codets.innerHTML = "请输入验证码";
			codets.style.color = "red";
			return false;
		}
		else {
			codets.innerHTML = "验证码正确";
			codets.style.color = "green";
			return true;
		}
	}
</script>
</head>

<body style="text-align: center;">
	<form
		action="${pageContext.request.contextPath }/client/RegisterServlet"
		method="post"
		onsubmit="return register();">
		<table style="margin: 0 30%;">
			<tr>
				<td>用户名：<input type="text" name="username" id="username"
					onblur="return checkname();">&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="namets" style="color: red;"></span>
				</td>
			</tr>
			<tr>
				<td>密码：<input type="password" name="password" id="password"
					onblur="return checkpass();">&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="passts" style="color: red;"></span>
				</td>
			</tr>
			<tr>
				<td>确认密码:<input type="password" id="confirm"
					onblur="return checkconfirm();">&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="confirmts" style="color: red;"></span>
				</td>
			</tr>
			<tr>
				<td>电话：<input type="text" name="phone" id="phone"
					onblur="return checkNull('phone');">&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="phonets" style="color: red;"></span>
				</td>
			</tr>
			<tr>
				<td>手机：<input type="text" name="cellphone" id="cellphone"
					onblur="return checkNull('cellphone');">&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="cellphonets" style="color: red;"></span>
				</td>
			</tr>
			<tr>
				<td>邮箱：<input type="text" name="email" id="email"
					onblur="return checkemail();">&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="emailts" style="color: red;"></span>
				</td>
				<td><button onclick="return emailConfirm()">发送验证码</button>
					</td>
			</tr>
			<tr>
				<td>邮箱验证码:<input type="text" id="emailCode"
					onblur="return codeConfirm();" />&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="codets" style="color: red;"></span>
				</td>
				<td>
				<p id="code" hidden></p>
				</td>
			</tr>
			<tr>
				<td>住址：<input type="text" name="address" id="address"
					onblur="return checkNull('address');" />&nbsp;&nbsp;&nbsp;&nbsp;<span
					id="addressts" style="color: red;"></span>
					<p style="color: red;">多地址用逗号分隔</p>

				</td>
			</tr>
			<tr>
				<td><input type="submit" value="注册"></td>
			</tr>
		</table>
	</form>

</body>
</html>
