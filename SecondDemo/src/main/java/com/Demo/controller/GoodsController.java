package com.Demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Demo.Dao.GoodsDao;
import com.Demo.Dao.UserDao;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private UserDao userDao;
	/*
	 * ������Ʒ����Ҫ������Ʒ�Ļ�����Ϣ
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public void publish(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{		
		if(session.getAttribute("phone_id")==null)
		{
			response.getWriter().write("new");
			System.out.println("����");
		}			
		else
		{
			String phone_id=(String) session.getAttribute("phone_id");
			String user_id=(String) userDao.selectPhone(phone_id).get("user_id");
			Map<String, String[]> map=request.getParameterMap();
			Map<String, Object> req = new HashMap();
			for (Map.Entry<String, String[]> entry : map.entrySet())
			{
				req.put(entry.getKey(), entry.getValue()[0]);
			}
			System.out.println(req.get("goods_name"));
			req.put("user_id", user_id);
//			System.out.println(request.getParameter("user_name"));
			goodsDao.insert(req);
			response.getWriter().write("ok");
		}
	}
	/*
	 * �ϴ�ͼƬ
	 */
	@ResponseBody
	@RequestMapping(value = "/addImg", method = RequestMethod.POST)
	public Map<String,Object> addImg(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		System.out.println("diaoyong");
		Map<String,Object> result= new HashMap<String, Object>();
		String imgURL="F:\\woke\\images";
		// ת��ΪMultipartHttpRequest��
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// ����ļ���
		/** ҳ��ؼ����ļ���* */
        MultipartFile imgFile = null;
        Map map =multipartRequest.getFileMap();
         for (Iterator i = map.keySet().iterator(); i.hasNext();) {
                Object obj = i.next();
                imgFile=(MultipartFile) map.get(obj);
               }
//		MultipartFile imgFile= multipartRequest.getFile("file");
		try{
			if(!(imgFile.getOriginalFilename() == null || "".equals(imgFile.getOriginalFilename()))){
				String imgDir = "F:\\woke\\images";        // ͼƬ�ϴ���ַ
				// ���ļ����д洢����
				byte[] bytes = imgFile.getBytes();
				String id=UUID.randomUUID().toString();
	            Path path = Paths.get(imgDir,"\\"+id);
	            Files.write(path,bytes);
	            imgURL=""+path;
	            String fileName=id;
//	            response.getWriter().write("ok");
	            result.put("msg","�ϴ��ɹ���");
	            result.put("result","ok");
	            result.put("imgURL",fileName);
			}
		}catch(IOException e){
	            result.put("msg","������");
	            result.put("result","no");
	            result.put("imgURL",imgURL);
	            e.printStackTrace();
	        }catch (Exception e1){
	            e1.printStackTrace();
	        }
		return result;
	}
	/*
	 * ���������·���еĲ���id,�ӱ��ش����ж�ȡͼƬ��picUrl�Ǵ������ļ��ж�ȡ������
	 */
	@RequestMapping(value="/showPic/{fileName:.+}")
	public void showPic(HttpServletRequest request,HttpServletResponse response,@PathVariable String fileName) throws UnsupportedEncodingException{		
		String f=java.net.URLDecoder.decode(fileName , "UTF-8");
		System.out.println(f);
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream("F:\\woke\\images"+"\\"+f);
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {			
			os.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		 
	/*
	 * ��ѯ������Ʒ��Ϣ
	 */
	@RequestMapping(value ="/select",method = RequestMethod.POST)
	public void select(HttpServletRequest request,HttpServletResponse response) throws IOException
	{		
		List<Map<String,Object>> goods=goodsDao.select();
		JSONObject res = new JSONObject();
		res.put("rows", goods);
	    System.out.println("��ѯ��Ʒ�ɹ�");
	    response.setCharacterEncoding("utf-8");
		response.getWriter().write(res.toJSONString());		
	}
	/*
	 * ��ѯ����������Ʒ��Ϣ
	 */
	@RequestMapping(value ="/userSelect",method = RequestMethod.POST)
	public void userSelect(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{	
		String phone_id=(String) session.getAttribute("phone_id");
		List<Map<String,Object>> goods=goodsDao.userSelect(phone_id);
		JSONObject res = (JSONObject) JSONObject.toJSON(goods);
	    System.out.println("��ѯ��Ʒ�ɹ�");
		response.getWriter().write(res.toJSONString());		
	}
	/*
	 * ��ѯ������Ʒ��Ϣ
	 */
	@RequestMapping(value ="/selectOne",method = RequestMethod.POST)
	public void selectOne(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{	
		String goods_id=request.getParameter("goods_id");
		Map<String,Object> goods=goodsDao.select(goods_id);
		JSONObject res = (JSONObject) JSONObject.toJSON(goods);
	    System.out.println("��ѯ��Ʒ�ɹ�");
		response.getWriter().write(res.toJSONString());		
	}
	/*
	 * ��Ʒ����
	 */
	@RequestMapping(value ="/selectType",method = RequestMethod.POST)
	public void selectType(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{	
		String goods_type=request.getParameter("goods_type");
		List<Map<String,Object>> goods=goodsDao.selectType(goods_type);
		JSONObject res = (JSONObject) JSONObject.toJSON(goods);
	    System.out.println("��ѯ��Ʒ�ɹ�");
		response.getWriter().write(res.toJSONString());		
	}
	/*
	 * ��Ʒ����
	 */
	@RequestMapping(value ="/selectOrderBy",method = RequestMethod.POST)
	public void selectOrderBy(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{	
		String goods_type=request.getParameter("goods_type");
		String oderby=request.getParameter("orderby");
		if(oderby=="like")
		{
			List<Map<String,Object>> goods=goodsDao.selectOrderByLike(goods_type);
			JSONObject res = (JSONObject) JSONObject.toJSON(goods);
		    System.out.println("��ѯ��Ʒ�ɹ�");
			response.getWriter().write(res.toJSONString());	
		}
		else if(oderby=="price")
		{
			List<Map<String,Object>> goods=goodsDao.selectOrderByPrice(goods_type);
			JSONObject res = (JSONObject) JSONObject.toJSON(goods);
		    System.out.println("��ѯ��Ʒ�ɹ�");
			response.getWriter().write(res.toJSONString());
		}
			
	}
}
