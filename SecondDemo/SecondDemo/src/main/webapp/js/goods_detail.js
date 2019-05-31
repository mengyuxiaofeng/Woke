$(function(){  getGoodsDetail();   });  

function GetUrlPara(){
                var url = document.location.toString();//获取当前URL
                var arrUrl = url.split("?");//分割？
                var para = arrUrl[1];//获取参数部分
                var arr = para.split("=");//分割=
                var res = arr[1];//获取参数的值
                return res;
}

function getGoodsDetail(){
	var goods_id=GetUrlPara();
	//alert("de");
	$.ajax({
        //              url:"http://localhost:8080/SecondDemo/user/getSession",
        url:"http://"+window.location.host+"/SecondDemo/goods/selectOne",//蠢办法获取当前的URL
        type:"POST",  
        dataType:"text",
        data:{
        	"goods_id":goods_id,
        },
        success:function(jsonstr){
        	//alert(jsonstr);   
        	data=eval('(' + jsonstr + ')');        	
        	$("#goods_name").append(data.goods_name);
        	//动态修改图片内容
        	$("#goods_image").attr("src","http://localhost:8080/SecondDemo/goods/showPic/"+data.goods_image);
        	$("#goods_price").append(data.goods_price);
        	$("#goods_type").append(data.goods_type);
        	$("#goods_address").append(data.goods_address);
        	$("#user_name").append('<a class="" href="">'+data.user_name+'</a>');
        	$("#create_time").append(data.create_time);
        	$("#goods_desc").append(data.goods_desc);
        },
        error : function(e) {
            alert("出错");
            alert(e.responseText);                
        },
    })
}