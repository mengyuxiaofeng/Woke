package com.Demo.controller;

import java.io.IOException;
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
import com.Demo.Dao.InventoryDao;
import com.Demo.Dao.OrderDao;
import com.Demo.Dao.UserDao;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/inventory")
public class InventoryController {
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private UserDao userDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private InventoryDao inventoryDao;
	
	@RequestMapping(value = "/selectOrder", method = RequestMethod.POST)
	public void selectOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String)request.getParameter("phone_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		List<Map<String,Object>> orders=orderDao.selectDone1(user_id);
		for(Map<String,Object> order:orders){
			String goods_id=(String) order.get("goods_id");
			Map<String,Object> goods=goodsDao.select(goods_id);
			Map<String,Object> user=userDao.selectPhone(phone_id);
			//long total_price=(Long) goods.get("goods_price");
			//String goods_image=(String) goods.get("goods_image");
			String goods_name=(String) goods.get("goods_name");
			String user_name = (String) user.get("user_name");
			//order.put("total_price", total_price);
			//order.put("goods_image", goods_image);
			order.put("goods_name", goods_name);
			order.put("user_name", user_name);
			String s;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = (Date) order.get("create_time");
			s = simpleDateFormat.format(d);
			order.put("create_time",s);
		}
		JSONObject res = new JSONObject();
		res.put("rows", orders);
	    System.out.println("查询订单成功");
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());
	}
	
	@RequestMapping(value = "/contain", method = RequestMethod.POST)
	public void contain(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String order_id=(String)request.getParameter("order_id");
		String box_id=(String)request.getParameter("box_id");
		Map<String,Object> inventory=new HashMap<String,Object>();
		inventory.put("order_id", order_id);
		inventory.put("box_id", box_id);
		orderDao.contain(order_id);
		inventoryDao.insert(inventory);
	    System.out.println("寄存成功"+box_id);
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write("寄存成功");
	}
	
	@RequestMapping(value = "/setLoaction", method = RequestMethod.POST)
	public void setLoaction(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String position=(String)request.getParameter("position");
		String box_id=(String)request.getParameter("id");
		Map<String,Object> inventory=new HashMap<String,Object>();
		inventory.put("position", position);
		inventory.put("box_id", box_id);
		inventoryDao.putIn(inventory);
	    System.out.println("更新入库成功"+inventory);
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write("更新入库成功");
	}
	@RequestMapping(value = "/selectRunning", method = RequestMethod.POST)
	public void selectRunning(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		List<Map<String,Object>> sum=inventoryDao.selectRunning();		
		for(Map<String,Object> inventory:sum){
			String order_id=(String) inventory.get("order_id");
			Map order=orderDao.selectById(order_id);
			String goods_id=(String) order.get("goods_id");
			String user_name=(String) order.get("user_name");
			String phone_id=(String) order.get("phone_id");
			String goods_name=(String)goodsDao.select(goods_id).get("goods_name");
			inventory.put("goods_name", goods_name);
			inventory.put("user_name", user_name);
			inventory.put("phone_id", phone_id);
			
			if(inventory.get("put_in_time")==null)
				inventory.put("put_in_time", "");
		}
		JSONObject res = new JSONObject();
		res.put("rows", sum);		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());		
		System.out.println(res.toJSONString());
	}
	
	@RequestMapping(value = "/putIn", method = RequestMethod.POST)
	public void putIn(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String order_id=(String)request.getParameter("order_id");
		orderDao.putIn(order_id);
	    System.out.println("入库成功");
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write("入库成功");
	}
	
	@RequestMapping(value = "/takeAway", method = RequestMethod.POST)
	public void takeAway(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String inventory_id=(String)request.getParameter("inventory_id");
		inventoryDao.takeAway(inventory_id);
	    System.out.println("取货成功");
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write("取货成功");
	}
}
