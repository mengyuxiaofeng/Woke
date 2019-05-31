/*
 *Description : 我的购买记录;
 *Author : Yao Yue;
 *Date : 2016/03/24;
 **/
var purchase = {
    initFun:function(){
        this.scrollFun();//山姆头条；
        this.TabFun();//tab选项卡切换；
        this.SelectFun();//模拟select下拉列表
        this.deleteFun();//删除单个订单
        this.returnTopFun();//返回顶部
        this.moreFun();//点击查看更多按钮时出现一个订单模块
        this.menuFun();//左侧菜单栏
    },

    //山姆头条；
    scrollFun:function(){
            var $this = $(".slide-box");
            var scrollTimer;
            $this.hover(function(){
                clearInterval(scrollTimer);
            },function(){
                scrollTimer = setInterval(function(){
                    scrollNews( $this );
                }, 3000 );
            }).trigger("mouseleave");
        function scrollNews(e){
            var $self = e.find("ul");
            var lineHeight = $self.find("li:first").height(); //获取行高
            $self.animate({ "margin-top" : -lineHeight +"px" }, 600 , function(){
                $self.css({"margin-top":0}).find("li:first").appendTo($self);
            })
        }
    },

    //tab选项卡切换；
    TabFun:function(){
        var $tab_li = $(".tab-tit li");
        $tab_li.click(function(){
            $(this).addClass("curr").siblings().removeClass("curr");
            var Index = $tab_li.index(this);
            $(".tab-cont>div").eq(Index).show().siblings().hide();
        })
    },


    //模拟select下拉列表
    SelectFun:function(){
        var $list_li = $(".list-cont ul li");
        var $pul_list = $(".pull-down-list");
        var $List_cont = $(".list-cont");
        var $list_tit = $(".list-tit");
        $pul_list.mouseover(function() {
            $List_cont.show();
        });//select出现
        $pul_list.mouseout(function(){
            $List_cont.hide();
        });//select消失
        $list_li.click(function(){
            $list_tit.text($(this).children().text());
            $List_cont.hide();

        })
    },

    //删除单个订单
    deleteFun:function(){
        var $delete = $("table .delete");
        $delete.click(function(){
          $(this).parents(".ta-order").remove();

        })
    },

    //返回顶部
    returnTopFun:function(){
        var $return = $(".return-top");
       $(window).scroll(function(){
          var $num = $(window).scrollTop();
          if($num>0){
              $return.show();
          }else{
              $return.hide();
          }
       })
    },


    //点击查看更多按钮时出现一个订单模块
    moreFun:function(){
        var $more_btn = $(".more-btn");
        $more_btn.click(function(){
            $(".main-cont table:hidden").first().show();
        })
    },


    //左侧菜单栏
    menuFun:function(){
        var items = [
            {'dt':'订单管理',
                'dd':[
                    {'dtit':'我的购买记录','dlink':'index.html'},
                    {'dtit':'返修/退换货申请','dlink':'index.html'},
                    {'dtit':'返修/退换货查询','dlink':'index.html'}
                ]
            },
            {'dt':'个人信息管理',
                'dd':[
                    {'dtit':'编辑个人信息','dlink':'index.html'},
                    {'dtit':'会籍管理','dlink':'index.html'},
                    {'dtit':'修改密码','dlink':'index.html'},
                    {'dtit':'返修/地址管理','dlink':'index.html'}
                ]
            },
            {'dt':'我的专区',
                'dd':[
                    {'dtit':'我的优惠券','dlink':'index.html'},
                    {'dtit':'常购清单','dlink':'index.html'},
                    {'dtit':'商品点评','dlink':'index.html'},
                    {'dtit':'购物咨询','dlink':'index.html'}
                ]
            },
            {'dt':'服务中心',
                'dd':[
                    {'dtit':'到货通知','dlink':'index.html'},
                    {'dtit':'降价通知','dlink':'index.html'},
                    {'dtit':'推广订阅','dlink':'index.html'},
                    {'dtit':'我的购物卡','dlink':'index.html'},
                    {'dtit':'好友推荐','dlink':'index.html'},
                    {'dtit':'山姆实体店','dlink':'index.html'}
                ]
            }
        ];
        var path = '../';
        for(var i= 0,len=items.length;i<len;i++){
            var list = items[i];
            var listHtml = ''+
                '<dl>'+
                ' <dt><i></i>'+list.dt+'</dt>';
            for(var j= 0,l=list.dd.length;j<l;j++){
                listHtml+='<dd><a href="'+path+''+''+list.dd[j].dlink+'">'+list.dd[j].dtit+'</a></dd>'
            }
            listHtml+='</dl>';
            $(".sidenav").append(listHtml);
        }
        $('.sidenav dl:first dd:first').addClass("curr");
    }

};
purchase.initFun();