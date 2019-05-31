package com.Demo.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="newsDao")
public class NewsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * 发送消息
	 */	
	public int sendMessage(Map<String,Object> news)
	{
		if(news.get("news_publisher")!=null&&news.get("news_reciever")!=null)
		{
			String id=UUID.randomUUID().toString();
			//当前时间，并转换为时间戳
			Date d = new Date();
			java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
			String SQL = "insert into user(news_id,news_publisher,news_reciever,news_title,news_type,news_content,create_time)Values(?,?,?,?,?,?)";
			int re=jdbcTemplate.update(SQL, id, news.get("news_publisher"),news.get("news_reciever"), news.get("news_title"),news.get("news_type"),news.get("news_content"),dateSQL);
			return re;
		}
		return 0;
	}
	/*
	 * 接收消息
	 */	
	public List<Map<String,Object>> selectNews(String phone_id)
	{
		if(selectCount(phone_id)>0)
		{
			String SQL = "select * from news where phone_id=? order by create_time DESC";
			List<Map<String,Object>> news=jdbcTemplate.queryForList(SQL,phone_id);
			return news;
		}
		else
			return null;
	}
	/*
	 * 查询是否存在消息
	 */
	public int selectCount(String phone_id)
	{
		int count=0;
		String SQL = "select count(*) from news where phone_id=?";		
		count=jdbcTemplate.queryForInt(SQL,new Object[] {phone_id});						
		return count;
	}
}
