package com.Demo.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="orderDao")
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * 插入（发布）
	 */	
	public int insert(Map<String,Object> orders)
	{
		if(orders.get("user_id")!=null&&orders.get("goods_id")!=null)
		{
		String id=UUID.randomUUID().toString();
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String order_status="待支付";
		String SQL = "insert into orders (order_id,user_id,goods_id,order_price,order_status,order_site,order_note,create_time,user_name,phone_id)Values(?,?,?,?,?,?,?,?,?,?)";
		int re=jdbcTemplate.update(SQL,new Object[] {id, orders.get("user_id"),orders.get("goods_id"),orders.get("order_price"),order_status,orders.get("order_site"), orders.get("order_note"),dateSQL,orders.get("user_name"),orders.get("phone_id") });
		return re;
		}
		return 0;
	}
	
	/*
	 * 修改（未完成）
	 */	
	public int update(Map<String,Object> orders)
	{
		if(orders.get("user_id")!=null&&orders.get("goods_id")!=null)
		{
		String id=UUID.randomUUID().toString();
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String order_status="待支付";
		String SQL = "insert into orders (order_id,user_id,goods_id,order_price,order_status,order_site,order_note,create_time,user_name,phone_id)Values(?,?,?,?,?,?,?,?,?,?)";
		int re=jdbcTemplate.update(SQL,new Object[] {id, orders.get("user_id"),orders.get("goods_id"),orders.get("order_price"),order_status,orders.get("order_site"), orders.get("order_note"),dateSQL,orders.get("user_name"),orders.get("phone_id") });
		return re;
		}
		return 0;
	}
	
	/*
	 * 查询已完成订单
	 */
	public List<Map<String,Object>> selectDone(String user_id)
	{
		String SQL = "select * from orders where user_id=? and (order_status='已完成' or order_status='待入库' or order_status='已入库' or order_status='已取出')  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL,new Object[] { user_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	public List<Map<String,Object>> selectDone1(String user_id)
	{
		String SQL = "select * from orders where user_id=? and order_status='已完成'  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL,new Object[] { user_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	public List<Map<String,Object>> selectDone()
	{
		String SQL = "select * from orders where (order_status='已完成' or order_status='待入库' or order_status='已入库' or order_status='已取出')  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return order;
	}
	
	public List<Map<String,Object>> selectDone1()
	{
		String SQL = "select * from orders where (order_status='待入库' or order_status='已入库' or order_status='已取出')  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 查询未完成订单
	 */
	public List<Map<String,Object>> selectNot(String user_id)
	{
		String SQL = "select * from orders where user_id=? and order_status='待支付'  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL,new Object[] { user_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 查询待放入订单
	 */
	public List<Map<String,Object>> selectNotPut(String user_id)
	{
		String SQL = "select * from orders where order_status='待入库'  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL,new Object[] { user_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 查询已放入订单
	 */
	public List<Map<String,Object>> selectPut(String user_id)
	{
		String SQL = "select * from orders where and order_status='已入库'  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL,new Object[] { user_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 查询已取出订单
	 */
	public List<Map<String,Object>> selectPicked(String user_id)
	{
		String SQL = "select * from orders where order_status='已取出'  order by create_time DESC";
		List<Map<String,Object>> order=jdbcTemplate.queryForList(SQL,new Object[] { user_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 查询已订单ID
	 */
	public Map<String,Object> selectById(String order_id)
	{
		String SQL = "select * from orders where order_id=?";
		Map<String,Object> order=jdbcTemplate.queryForMap(SQL,new Object[] { order_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 确认订单,支付
	 */
	public int pay(String user_id,String goods_id)
	{
		String SQL = "update orders set order_status='已完成'  where user_id=? and goods_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { user_id ,goods_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 确认寄存
	 */
	public int contain(String user_id,String goods_id)
	{
		String SQL = "update orders set order_status='待入库'  where user_id=? and goods_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { user_id ,goods_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	public int contain(String order_id)
	{
		String SQL = "update orders set order_status='待入库'  where order_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { order_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 确认入库
	 */
	public int putIn(String user_id,String goods_id)
	{
		String SQL = "update orders set order_status='已入库'  where user_id=? and goods_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { user_id ,goods_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	public int putIn(String order_id)
	{
		String SQL = "update orders set order_status='已入库'  where order_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { order_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	/*
	 * 确认取出
	 */
	public int takeAway(String user_id,String goods_id)
	{
		String SQL = "update orders set order_status='已取出'  where user_id=? and goods_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { user_id ,goods_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	public int takeAway(String order_id)
	{
		String SQL = "update orders set order_status='已取出'  where order_id=?";
		int order=jdbcTemplate.update(SQL,new Object[] { order_id });
		//System.out.println("Dao:"+order);
		return order;
	}
	
	public List<Map<String,Object>> countSalesEveryDay()
	{
		String SQL = "select DATE_FORMAT(orders.create_time, '%Y-%m-%d') sale_time, count(*) sale_count from orders where (order_status='已完成' or order_status='待入库' or order_status='已入库' or order_status='已取出') group by sale_time order by sale_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	public List<Map<String,Object>> sumSalesEveryDay()
	{
		String SQL = "select DATE_FORMAT(orders.create_time, '%Y-%m-%d') sale_time, sum(goods_price) prices from orders left join goods on orders.goods_id=goods.goods_id where (order_status='已完成' or order_status='待入库' or order_status='已入库' or order_status='已取出') group by sale_time order by sale_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);		
		//System.out.println("Dao:"+order);
		return inventory;
	}
}
