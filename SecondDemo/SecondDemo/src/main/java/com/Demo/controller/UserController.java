package com.Demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Demo.Dao.UserDao;
import com.Demo.service.UserService;
import com.alibaba.fastjson.JSONObject;



@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Resource
    private UserService userService;
	@Resource
	private UserDao userDao;
	
	/*
	 * 注册
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		Map<String, String[]> map=request.getParameterMap();
		Map<String, Object> req = new HashMap();
		for (Map.Entry<String, String[]> entry : map.entrySet())
		{
			req.put(entry.getKey(), entry.getValue()[0]);
		}
		System.out.println(req.get("phone_id"));
//		System.out.println(request.getParameter("user_name"));
		if(userDao.selectCount((String)req.get("phone_id"))==0)
		{
			userDao.insert(req);
			session.setAttribute("phone_id",req.get("phone_id"));			
		    response.getWriter().write("insert");
		}			
		else		    
			response.getWriter().write("repeat");		
	}
	/*
	 * 根据手机号登录
	 */
	@RequestMapping(value ="/login",method = RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=request.getParameter("phone_id");
		if(userDao.selectPhone(phone_id).isEmpty())
			response.getWriter().write("none");
		else
		{
		String password=(String) userDao.selectPhone(phone_id).get("password");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		if(password.equals(request.getParameter("password")))
		{
			response.getWriter().write("right");
			session.setAttribute("phone_id",phone_id);
			session.setAttribute("user_id",user_id);
			System.out.println("密码正确");
		}
		else
		{
			response.getWriter().write("error");
			System.out.println("密码错误！！");
		}
		}
	}
	/*
	 * 通过session获取用户信息
	 */
	@RequestMapping(value ="/getSession",method = RequestMethod.GET)
	public void getSession(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		if(session.getAttribute("phone_id")==null)
		{
			response.getWriter().write("new");
			System.out.println("出错");
		}			
		else
		{		
			String phone_id=(String) session.getAttribute("phone_id");
			System.out.println(phone_id);
			response.getWriter().write(phone_id);
		}
	}
	/*
	 * 查询个人信息
	 */
	@RequestMapping(value ="/selectPerson",method = RequestMethod.POST)
	public void selectPerson(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String) session.getAttribute("phone_id");
		Map<String,Object> user=userDao.selectPhone(phone_id);
		JSONObject res = (JSONObject) JSONObject.toJSON(user);
	    System.out.println("查询用户详情"+phone_id);
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());		
	}
	/*
	 * 修改信息
	 */
	@RequestMapping(value ="/modifyInfo",method = RequestMethod.POST)
	public void modifyInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		String phone_id=(String) session.getAttribute("phone_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		Map<String, String[]> map=request.getParameterMap();
		Map<String, Object> req = new HashMap();
		for (Map.Entry<String, String[]> entry : map.entrySet())
		{
			req.put(entry.getKey(), entry.getValue()[0]);
		}
		req.put("user_id", user_id);
		System.out.println(req);
		userDao.updateInfor(req);		
		response.getWriter().write("ok");
	}
	/*
	 * 修改密码
	 */
	@RequestMapping(value ="/modifyPassword",method = RequestMethod.POST)
	public void modifyPassword(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String phone_id=request.getParameter("phone_id");
		String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
		System.out.println(request.getParameter("password"));
		userDao.updatePassword(user_id,request.getParameter("password"));
		response.getWriter().write("ok");
	}
}
