$(function(){ selectUser();liclick1();selectPublished();selectSell();selectUnPaid();selectPaid();   }); 

function change(){
	$("#accountModal").modal("show");
}

function selectUser(){
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/user/selectPerson",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	//"xgoods_id":xgoods_id,
        },
        success:function(jsonstr){
        	//alert(jsonstr); 
        	data=eval('(' + jsonstr + ')');
        	if(data.user_name!="")
        	$("#user_name").html(data.user_name);
        	if(data.user_name!="")
        	$("#xuser_name").html(data.user_name);
        	$("#phone_id").html(data.phone_id);
        	if(data.user_major!="")
        	$("#user_major").html(data.user_major);
        	if(data.user_college!="")
        	$("#user_college").html(data.user_college);
        	if(data.sex!="")
        	$("#sex").html(data.sex);
        	if(data.user_address!="")
        	$("#user_address").html(data.user_address);
        	
        	$("#iuser_name").val(data.user_name);
        	$("#iuser_major").val(data.user_major);
        	$("#iuser_college").val(data.user_college);
        	$("#isex option[value='"+data.sex+"']").attr("selected", true);
        	$("#iuser_address").val(data.user_address);
        	$("#istudent_id").val(data.student_id);
        	$("#iuser_email").val(data.user_email);
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function modifyInfo(){
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/user/modifyInfo",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"user_name":$("#iuser_name").val(),
        	"user_major":$("#iuser_major").val(),
        	"user_college":$("#iuser_college").val(),
        	"sex":$("#isex").val(),
        	"user_address":$("#iuser_address").val(),
        	"student_id":$("#istudent_id").val(),
        	"user_email":$("#iuser_email").val(),
        },
        success:function(jsonstr){
        	alert(jsonstr);        	
        },
        error : function(e) {
            alert("出错");
            //alert($("#istudent_id").val());
            alert(e.responseText);                
        },
    })
}

function selectPublished(){
	//alert("selectPublished");
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/goods/userSelect",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        success:function(jsonstr){
        	//alert(jsonstr); 
        	data=eval('(' + jsonstr + ')');
        	$("#tab2").html("");
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		//alert(actObj.goods_id);
        		$("#tab2").append('<div class="mine row"><div class="mine-img col-3"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:200px"></div><div class="mine-txt col-9"><h3>'+actObj.goods_name+'<button onclick="cancelGoods('+'\''+actObj.goods_id+'\''+')">取消发布</button></h3><p>商品信息简介</p><label">'+actObj.goods_desc+'</label><p>商品状态：</p><label>'+actObj.goods_status+'</label></div></div>');
        	});  
        },
        error : function(e) {
            alert("出错");
            //alert($("#istudent_id").val());
            alert(e.responseText);                
        },
    })	
}

function selectSell(){
	//alert('selectSell');
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/goods/sellSelect",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        success:function(jsonstr){
        	//alert(jsonstr); 
        	data=eval('(' + jsonstr + ')');
        	$("#tab3").html("");
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		//alert(actObj.goods_id);
        		$("#tab3").append('<div class="mine row"><div class="mine-img col-3"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:200px"></div><div class="mine-txt col-9"><h3>'+actObj.goods_name+'<button onclick="cancelGoods('+'\''+actObj.goods_id+'\''+')">已卖出</button></h3><p>商品信息简介</p><label">'+actObj.goods_desc+'</label><p>商品状态：</p><label>'+actObj.goods_status+'</label></div></div>');
        	});  
        },
        error : function(e) {
            alert("出错");
            //alert($("#istudent_id").val());
            alert(e.responseText);                
        },
    })	
}

function selectUnPaid(){
		$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/order/selectUnPaid",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        success:function(jsonstr){
        	//alert(jsonstr); 
        	data=eval('(' + jsonstr + ')');
        	$("#orderUn").html("");
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		var head='<table cellpadding="0" cellspacing="0" class="ta-order"><tr class="table-title"><td class="pro-tit"><div class="first-td number" style="width:200px">'+actObj.create_time+'</div></td><td colspan="2" class="pro-picture">订单编号：<em class="text">'+actObj.order_id+'</em>     </td><td class="pro-number"><div class="tol">总计<span class="blue-font number">'+actObj.total_price+' </span></div></td><td class="pro-add"><div class="blue-font pay fr">'+actObj.order_status+' </div></td></tr>';
        		var body='<tr><td><a class="first-td" href="#"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:90px;width:90px"></a></td><td><p class="long-text" style="width:400px;"><a href="#">'+actObj.goods_name+' </a></p></td><td><span class="num number">x1</span></td><td><div class="tol blue-font number">'+actObj.total_price+' </div></td><td class="clearfix"><a class="car fr" href="javascript:;"></a></td></tr>';
        		var end='<tr><td colspan="5" class="clearfix last-td"><a href="javascript:;" class="pay-btn btn fr" onclick="confirmOrder('+'\''+actObj.goods_id+'\''+')">立即支付</a><a href="javascript:;" class="look-btn btn fr" onclick="detail('+'\''+actObj.goods_id+'\''+')">查看详情</a></td></tr></table>';
        		$("#orderUn").append(head+body+end);
        	});  
        },
        error : function(e) {
            alert("出错");
            //alert($("#istudent_id").val());
            alert(e.responseText);                
        },
    })	
}

function selectPaid(){
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/order/selectPaid",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        success:function(jsonstr){
        	//alert(jsonstr); 
        	data=eval('(' + jsonstr + ')');
        	$("#orderPaid").html("");
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		var head='<table cellpadding="0" cellspacing="0" class="ta-order"><tr class="table-title"><td class="pro-tit"><div class="first-td number" style="width:200px">'+actObj.create_time+'</div></td><td colspan="2" class="pro-picture">订单编号：<em class="text">'+actObj.order_id+'</em>     </td><td class="pro-number"><div class="tol">总计<span class="blue-font number">'+actObj.total_price+' </span></div></td><td class="pro-add"><div class="blue-font pay fr">'+actObj.order_status+' </div></td></tr>';
        		var body='<tr><td><a class="first-td" href="#"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:90px;width:90px"></a></td><td><p class="long-text" style="width:400px;"><a href="#">'+actObj.goods_name+' </a></p></td><td><span class="num number">x1</span></td><td><div class="tol blue-font number">'+actObj.total_price+' </div></td><td class="clearfix"><a class="car fr" href="javascript:;"></a></td></tr>';
        		var end='<tr><td colspan="5" class="clearfix last-td"><a href="javascript:;" class="pay-btn btn fr">已支付</a><a href="javascript:;" class="look-btn btn fr" onclick="detail('+'\''+actObj.goods_id+'\''+')">查看详情</a></td></tr></table>';
        		$("#orderPaid").append(head+body+end);
        	});  
        },
        error : function(e) {
            alert("出错");
            //alert($("#istudent_id").val());
            alert(e.responseText);                
        },
    })	
}

function cancelGoods(goods_id){
	//alert("cancelGoods");
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/goods/cancelGoods",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"goods_id":goods_id,
        },
        success:function(jsonstr){
        	alert(jsonstr);   
        	//window.location.href="http://"+window.location.host+"/SecondDemo/pages/personal_homepage.html";
        	selectPublished();
        	selectPaid();
        	selectUnPaid();
        	selectSell();
        },
        error : function(e) {
            alert("出错");
            //alert($("#istudent_id").val());
            alert(e.responseText);                
        },
    })	
}

function confirmOrder(goods_id){
	//alert("dsi");
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/user/getSession",//蠢办法获取当前的URL
        type:"GET",  
        dataType:"text",        
        success:function(data){
            if(data=="new")
                window.location.href='http://'+window.location.host+'/SecondDemo/pages/login.html';
            else
            {
                alert(data);
                $("#orderModal").modal("show");
                confirm(goods_id,data);
                xgoods_id=goods_id;
                xuser_id=data;
            }   
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function confirm(goods_id,phone_id){
	xphone_id=phone_id;
	xgoods_id=goods_id;
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/order/confirm",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"goods_id":goods_id,
        	"phone_id":phone_id,
        },
        success:function(jsonstr){
        	//alert(jsonstr);
        	data=eval('(' + jsonstr + ')');
            $("#cgoods_name").text(data.goods_name);
            $("#cgoods_price").html(data.goods_price);
            $("#cuser_name").val(data.user_name);
            $("#cphone_id").val(phone_id);
            $("#cgoods_site").val(data.goods_site);
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function insertOrder(){
	$("#accountModal").modal("hide");
	$("#payModal").modal("show");	
}

function payOrder(){
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/order/pay",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"xgoods_id":xgoods_id,
        },
        success:function(jsonstr){
        	alert(jsonstr);    
        	//window.location.href="http://"+window.location.host+"/SecondDemo/pages/personal_home.html";
        	selectUnPaid();
        	selectPublished();
        	selectPaid();
        	selectSell();
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function detail(goods_id){
	window.open("http://"+window.location.host+"/SecondDemo/pages/goods_detail.html?goods_id="+goods_id);
}