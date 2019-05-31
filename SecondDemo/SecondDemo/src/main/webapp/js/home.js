$(function(){ select();   });  

function getGoods(){
    //alert(window.location.host);
    window.location.href="http://"+window.location.host+"/SecondDemo/pages/home.html";
}
function getCircle(){
    //alert(window.location.host);
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
                 //alert(data);
            	 alert("暂未开放，敬请期待！");
                 //window.location.href="http://"+window.location.host+"/SecondDemo/pages/circle.html";
             }                       
         },                      
         error : function(e) {
             alert("出错");
             alert(e.responseText);                
         },
     })
    //window.location.href="http://"+window.location.host+"/SecondDemo/pages/circle.html";
}
function getNews(){
    //alert(window.location.host);
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
            	 alert("暂未开放，敬请期待！");
                 //alert(data);
                 //window.location.href="http://"+window.location.host+"/SecondDemo/pages/news.html";
             }                       
         },                      
         error : function(e) {
             alert("出错");
             alert(e.responseText);                
         },
     })
    //window.location.href="http://"+window.location.host+"/SecondDemo/pages/news.html";
}
function getUser(){
        //alert(window.location.host);
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
                    //alert(data);
                    window.location.href="http://"+window.location.host+"/SecondDemo/pages/personal_homepage.html";
                }                       
            },                      
            error : function(e) {
                alert("出错");
                alert(e.responseText);                
            },
        })
}
function publish(){
	//alert(window.location.host);
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
                //alert(data);
            	window.location.href="http://"+window.location.host+"/SecondDemo/pages/publish.html";
            }   
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function select(){
	goods_type="全部";
	$.ajax({
		url:"http://"+window.location.host+"/SecondDemo/goods/select",//蠢办法获取当前的URL
        type:"post",  
        dataType:"text",
        success:function(jsonstr){
        	$("#rows").html("");
        	data=eval('(' + jsonstr + ')');
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		//alert(actObj.goods_image);
        		$("#rows").append('<div class="col-sm-6 col-md-3" ><div class="thumbnail"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:200px"><div class="caption" style="padding-top: 0px;padding-bottom: 0px;"><p class="goods_list_desc">商品名称：'+actObj.goods_name+'</p><p class="goods_list_desc">商品价格：'+actObj.goods_price+'</p><p class="pic_p"><button style="margin-left:10px;" type="button" class="btn btn-primary btn-color" role="button" onclick="detail('+'\''+actObj.goods_id+'\''+')"><span class="glyphicon glyphicon-camera" aria-hidden="true"></span> 详情</button><button type="button" class="btn btn-info pull-right" onclick="confirmOrder('+'\''+actObj.goods_id+'\''+')"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> 购买</button></p></div></div></div>');
        	});
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
	})
}

var xphone_id;
var xgoods_id;
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
                //alert(data);
                $("#accountModal").modal("show");
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
            $("#goods_name").text(data.goods_name);
            $("#goods_price").html(data.goods_price);
            $("#user_name").val(data.user_name);
            $("#phone_id").val(phone_id);
            $("#goods_site").val(data.goods_site);
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function insert(){
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/order/insert",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"xgoods_id":xgoods_id,
        	"xphone_id":xphone_id,
        	"order_price":$("#goods_price").html().trim(),
        	"user_name":$("#user_name").val(),
        	"phone_id":$("#phone_id").val(),
        	"order_site":$("#goods_site").val(),
        	"order_note":$("#order_note").val(),
        },
        success:function(jsonstr){
        	//alert(jsonstr);    
        	$("#accountModal").modal("hide");
        	$("#payModal").modal("show");
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function pay(){
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
        	window.location.href="http://"+window.location.host+"/SecondDemo/pages/home.html";
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

var goods_type;
function sort(type){
	//alert(type);
	goods_type=type;
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/goods/selectType",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"goods_type":type,
        },
        success:function(jsonstr){
        	$("#rows").html("");
        	//alert(jsonstr);
        	data=eval('(' + jsonstr + ')');
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		//alert(actObj.goods_image);
        		$("#rows").append('<div class="col-sm-6 col-md-3" ><div class="thumbnail"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:200px"><div class="caption" style="padding-top: 0px;padding-bottom: 0px;"><p class="pic_p">'+actObj.goods_name+'</p><p class="pic_p"><button style="margin-left:10px;" type="button" class="btn btn-primary btn-color" role="button" onclick="detail('+'\''+actObj.goods_id+'\''+')"><span class="glyphicon glyphicon-camera" aria-hidden="true"></span> 详情</button><button type="button" class="btn btn-info pull-right" onclick="confirmOrder('+'\''+actObj.goods_id+'\''+')"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> 购买</button></p></div></div></div>');
        	});
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}

function selectByOrder(){
	//alert(type);
	//alert($("#order_type").val());
	//alert(goods_type);
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/goods/selectOrderBy",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"orderby":$("#order_type").val(),
        	"goods_type":goods_type,
        },
        success:function(jsonstr){
        	$("#rows").html("");
        	//alert(jsonstr);
        	data=eval('(' + jsonstr + ')');
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		//alert(actObj.goods_image);
        		$("#rows").append('<div class="col-sm-6 col-md-3" ><div class="thumbnail"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"  style="height:200px"><div class="caption" style="padding-top: 0px;padding-bottom: 0px;"><p class="pic_p">'+actObj.goods_name+'</p><p class="pic_p"><button style="margin-left:10px;" type="button" class="btn btn-primary btn-color" role="button" onclick="detail('+'\''+actObj.goods_id+'\''+')"><span class="glyphicon glyphicon-camera" aria-hidden="true"></span> 详情</button><button type="button" class="btn btn-info pull-right" onclick="confirmOrder('+'\''+actObj.goods_id+'\''+')"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> 购买</button></p></div></div></div>');
        	});
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}
