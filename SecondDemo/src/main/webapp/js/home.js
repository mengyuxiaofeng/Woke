$(function(){  select();   });  
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
                 alert(data);
                 window.location.href="http://"+window.location.host+"/SecondDemo/pages/circle.html";
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
                 alert(data);
                 window.location.href="http://"+window.location.host+"/SecondDemo/pages/news.html";
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
                    alert(data);
                    window.location.href="http://"+window.location.host+"/SecondDemo/pages/user.html";
                }                       
            },                      
            error : function(e) {
                alert("出错");
                alert(e.responseText);                
            },
        })
}
function publish(){
	alert(window.location.host);
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
	$.ajax({
		url:"http://"+window.location.host+"/SecondDemo/goods/select",//蠢办法获取当前的URL
        type:"post",  
        dataType:"text",
        success:function(jsonstr){
        	data=eval('(' + jsonstr + ')');
        	$.each(data.rows,function(i,actObj){//i表示循环的下标，actObj表示循环的对象，可自定义名字        	
        		//alert(actObj.goods_image);
        		$("#rows").append('<div class="col-sm-6 col-md-4"><div class="thumbnail"><img src="http://localhost:8080/SecondDemo/goods/showPic/'+actObj.goods_image+'" alt="通用的占位符缩略图"><div class="caption" style="padding-top: 0px;padding-bottom: 0px;"><p class="pic_p">'+actObj.goods_name+'</p><p class="pic_p"><button type="button" class="btn btn-primary" role="button"><span class="glyphicon glyphicon-camera" aria-hidden="true"></span> 详情</button><button type="button" class="btn btn-info pull-right"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> 购买</button></p></div></div></div>');
        	});
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
	})
}