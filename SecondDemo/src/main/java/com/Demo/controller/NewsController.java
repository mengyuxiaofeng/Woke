package com.Demo.controller;

import java.io.IOException;
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

import com.Demo.Dao.NewsDao;
import com.Demo.Dao.UserDao;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/news")
public class NewsController {
	@Resource
	private NewsDao newsDao;
	/*
	 * 发送消息，需传入发送者和接受者的手机号
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public void sendMessage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		if(session.getAttribute("phone_id")==null)
		{
			response.getWriter().write("new");
			System.out.println("出错");
		}	
		else
		{
			Map<String, String[]> map=request.getParameterMap();
			Map<String, Object> req = new HashMap();
			for (Map.Entry<String, String[]> entry : map.entrySet())
			{
				req.put(entry.getKey(), entry.getValue()[0]);
			}		
			String news_publisher=(String) session.getAttribute("phone_id");
			req.put("news_publisher", news_publisher);
//			System.out.println(request.getParameter("user_name"));
			if(req.get("news_publisher")!=null&&req.get("news_reciever")!=null)
			{
				System.out.println(req.get("news_publisher"));
				newsDao.sendMessage(req);
			    response.getWriter().write("send");
			}			
			else		    
				response.getWriter().write("none");
		}
				
	}
	
	/*
	 * 发送消息，需传入发送者和接受者的手机号
	 */
	@RequestMapping(value = "/selectNews", method = RequestMethod.POST)
	public void selectNews(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		if(session.getAttribute("phone_id")==null)
		{
			response.getWriter().write("new");
			System.out.println("出错");
		}	
		else
		{
			String phone_id=(String) session.getAttribute("phone_id");
//			System.out.println(request.getParameter("user_name"));
			List<Map<String,Object>> goods=newsDao.selectNews(phone_id);
			JSONObject res = (JSONObject) JSONObject.toJSON(goods);
		    System.out.println("查询商品成功");
			response.getWriter().write(res.toJSONString());	
		}				
	}
}
