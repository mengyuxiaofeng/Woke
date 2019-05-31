function userLogin(){
//	alert("login");
	$.ajax({
		type:"post",
		url:"../user/login",	
		dataType:"text",
		data:{
			"phone_id":$("#username").val(),
			"password":$("#password").val(),
		},
		success:function(data){
//			alert(data);
			if(data=="right")
				window.location.href="../pages/home.html";
			else if(data=="error")
				alert("密码错误");
			else if(data=="none")
				alert("没有该用户信息");
		},
		error : function() {
            alert("出错");
        },
	})
}

function userRegister(){
//	alert("register");
	$.ajax({
		url:"../user/register",
		type:"post",
		dataType:"text",
		data:{
			"phone_id":$("#username").val(),
			"password":$("#password").val(),
		},
		success:function(data){
			if(data=="insert")
				alert("注册成功");
			else if(data=="repeat")
				alert("已存在该手机号");
		},
		error : function() {
            alert("出错");
        },
	})
}