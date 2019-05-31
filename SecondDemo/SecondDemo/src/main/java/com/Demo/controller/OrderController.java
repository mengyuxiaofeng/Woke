package com.Demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Demo.Dao.GoodsDao;
import com.Demo.Dao.OrderDao;
import com.Demo.Dao.UserDao;
import com.Demo.service.UserService;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
	@Resource
    private GoodsDao goodsDao;
	@Resource
	private OrderDao orderDao;
	@Resource
    private UserDao userDao;
	
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public void confirm(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=request.getParameter("phone_id");
		String goods_id=request.getParameter("goods_id");
		Map<String,Object> goods=goodsDao.select(goods_id);
		Map<String,Object> user=userDao.selectPhone(phone_id);
		Map<String,Object> res=new HashMap();
		res.put("goods_price", goods.get("goods_price"));
		res.put("goods_name", goods.get("goods_name"));
		res.put("user_name", user.get("user_name"));
		res.put("goods_site", user.get("user_address"));
		JSONObject r = (JSONObject) JSONObject.toJSON(res);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(r.toJSONString());		
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=request.getParameter("xphone_id");
		String goods_id=request.getParameter("xgoods_id");
		Map<String,Object> goods=goodsDao.select(goods_id);
		Map<String,Object> user=userDao.selectPhone(phone_id);
		Map<String,Object> order=new HashMap();
		order.put("goods_id", goods_id);
		order.put("user_id", user.get("user_id"));
		BigDecimal order_price = new BigDecimal(request.getParameter("order_price"));
		order.put("order_price", order_price);
		order.put("order_site", request.getParameter("order_site"));
		order.put("order_note", request.getParameter("order_note"));
		order.put("phone_id", request.getParameter("phone_id"));
		order.put("user_name", request.getParameter("user_name"));
		orderDao.insert(order);
		response.getWriter().write("insert");		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=request.getParameter("xphone_id");
		String goods_id=request.getParameter("xgoods_id");
		Map<String,Object> order=new HashMap();
		BigDecimal order_price = new BigDecimal(request.getParameter("order_price"));
		order.put("order_price", order_price);
		order.put("order_site", request.getParameter("order_site"));
		order.put("order_note", request.getParameter("order_note"));
		order.put("phone_id", request.getParameter("phone_id"));
		order.put("user_name", request.getParameter("user_name"));
		orderDao.update(order);
		response.getWriter().write("insert");		
	}
	
	@RequestMapping(value = "/selectUnPaid", method = RequestMethod.POST)
	public void selectDone(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String) session.getAttribute("phone_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		List<Map<String,Object>> orders=orderDao.selectNot(user_id);
		for(Map<String,Object> order:orders){
			String goods_id=(String) order.get("goods_id");
			Map<String,Object> goods=goodsDao.select(goods_id);
			long total_price=(Long) goods.get("goods_price");
			String goods_image=(String) goods.get("goods_image");
			String goods_name=(String) goods.get("goods_name");
			order.put("total_price", total_price);
			order.put("goods_image", goods_image);
			order.put("goods_name", goods_name);
			String s;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = (Date) order.get("create_time");
			s = simpleDateFormat.format(d);
			order.put("create_time",s);
		}
		
		JSONObject res = new JSONObject();
		res.put("rows", orders);
	    //System.out.println("查询商品成功");
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());	
	}
	
	@RequestMapping(value = "/selectPaid", method = RequestMethod.POST)
	public void selectNot(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String) session.getAttribute("phone_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		List<Map<String,Object>> orders=orderDao.selectDone(user_id);
		for(Map<String,Object> order:orders){
			String goods_id=(String) order.get("goods_id");
			Map<String,Object> goods=goodsDao.select(goods_id);
			long total_price=(Long) goods.get("goods_price");
			String goods_image=(String) goods.get("goods_image");
			String goods_name=(String) goods.get("goods_name");
			order.put("total_price", total_price);
			order.put("goods_image", goods_image);
			order.put("goods_name", goods_name);
			String s;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = (Date) order.get("create_time");
			s = simpleDateFormat.format(d);
			order.put("create_time",s);
		}
		
		JSONObject res = new JSONObject();
		res.put("rows", orders);
	    //System.out.println("查询商品成功");
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());	
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public void pay(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String) session.getAttribute("phone_id");
		String goods_id=request.getParameter("xgoods_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		goodsDao.updateStatus(goods_id,"已卖出");
		orderDao.pay(user_id, goods_id);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("支付成功");		
	}
	
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public void cancelOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String) session.getAttribute("phone_id");
		String goods_id=request.getParameter("xgoods_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		goodsDao.updateStatus(goods_id,"已卖出");
		orderDao.pay(user_id, goods_id);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("支付成功");		
	}
	@RequestMapping(value = "/sumSale", method = RequestMethod.POST)
	public void sumSale(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		List<Map<String,Object>> orders=orderDao.selectDone();
		List<Map<String,Object>> users=userDao.select();
		List<Map<String,Object>> orders1=orderDao.selectDone1();
		int countUser=users.size();
		int count1=orders1.size();
		long sales=0;
		int count=0;
		for(Map<String,Object> order:orders){
			Map<String,Object> goods=goodsDao.select((String)order.get("goods_id"));			
			long price = (long)goods.get("goods_price");
			sales+=price;
			count++;
		}
		DecimalFormat df = new DecimalFormat( "0.00"); //设置double类型小数点后位数格式 
		double profit=sales*0.07;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sales", sales);
		map.put("count", count);
		map.put("count1", count1);
		map.put("countUser", countUser);
		map.put("profit", df.format(profit));
		JSONObject res = new JSONObject();
		res.put("rows", map);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());		
	}
	
	@RequestMapping(value = "/everyDaySale", method = RequestMethod.POST)
	public void everyDaySale(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		List<Map<String,Object>> sum=orderDao.sumSalesEveryDay();		
		List<Map<String,Object>> count=orderDao.countSalesEveryDay();	
		JSONObject res = new JSONObject();
		res.put("sum", sum);
		res.put("count", count);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());		
		System.out.println(res.toJSONString());
	}
	
}
