package com.Demo.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="goodsDao")
public class GoodsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * 插入（发布）
	 */	
	public int insert(Map<String,Object> goods)
	{
		if(goods.get("user_id")!=null)
		{
		String id=UUID.randomUUID().toString();
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String goods_status="发布中";
		String SQL = "insert into goods(goods_id,user_id,goods_name,goods_type,goods_desc,goods_image,goods_price,create_time,goods_status)Values(?,?,?,?,?,?,?,?,?)";
		int re=jdbcTemplate.update(SQL,new Object[] {id, goods.get("user_id"),goods.get("goods_name"),goods.get("goods_type"),goods.get("goods_desc"), goods.get("goods_image"),goods.get("goods_price"),dateSQL,goods_status});
		return re;
		}
		return 0;
	}
	/*
	 * 查询所有信息
	 */
	public List<Map<String,Object>> select()
	{
		String SQL = "select * from goods where goods_status='发布中' order by create_time DESC";
		List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+goods);
		return goods;
	}
	/*
	 * 查询单个信息
	 */
	public Map<String,Object> select(String goods_id)
	{
		String SQL = "select * from goods where goods_id=?";
		Map<String,Object> goods=jdbcTemplate.queryForMap(SQL,new Object[] {goods_id});
		return goods;
	}
	/*
	 * 查询单人发布的商品信息
	 */
	public List<Map<String,Object>> userSelect(String phone_id)
	{
		String SQL = "select * from goods where user_id=? and goods_status='发布中'  order by create_time DESC";
		List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {phone_id});
		return goods;
	}
	/*
	 * 查询单人卖出的商品信息
	 */
	public List<Map<String,Object>> sellSelect(String phone_id)
	{
		String SQL = "select * from goods where user_id=? and goods_status='已卖出'  order by create_time DESC";
		List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {phone_id});
		return goods;
	}
	/*
	 * 商品筛选、分类
	 */
	public List<Map<String,Object>> selectType(String goods_type)
	{
		if(goods_type!=null&&!goods_type.equals("全部"))
		{
			String SQL = "select * from goods where goods_type=? and goods_status='发布中'  order by create_time DESC";
		    List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {goods_type});
		    return goods;
		}
		else
		{
			List<Map<String,Object>> goods=select();
			return goods;
		}
	}
	/*
	 * 商品热度排序
	 */
	public List<Map<String,Object>> selectOrderByLike(String goods_type)
	{
		
		if(goods_type!=null&&!goods_type.equals("全部"))
		{
			String SQL = "select * from goods where goods_type=? and goods_status='发布中'   order by goods_like DESC, create_time DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {goods_type});
			return goods;
		}
		else
		{
			String SQL = "select * from goods where goods_status='发布中'   order by goods_like DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL);
			return goods;
		}
	}
	/*
	 * 商品价格排序
	 */
	public List<Map<String,Object>> selectOrderByPrice(String goods_type)
	{		
		if(goods_type!=null&&!goods_type.equals("全部"))
		{
			String SQL = "select * from goods where goods_type=? and goods_status='发布中'   order by goods_price DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {goods_type});
			return goods;
		}
		else
		{
			String SQL = "select * from goods where goods_status='发布中'   order by goods_price DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL);
			return goods;
		}
	}
	/*
	 * 修改基本信息
	 */
	public int updateInfor(Map<String,Object> goods)
	{
		String SQL = "update goods set goods_name=? image=? price=? where goods_id=?";
		int re=jdbcTemplate.update(SQL, new Object[] {goods.get("goods_name"),goods.get("image"),goods.get("price"), goods.get("goods_id")});
		return re;
	}
	
	/*
	 * 修改商品状态
	 */
	public int updateStatus(String goods_id,String goods_status)
	{
		String SQL = "update goods set goods_status=?  where goods_id=?";
		int re=jdbcTemplate.update(SQL, new Object[] { goods_status, goods_id });
		return re;
	}
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
