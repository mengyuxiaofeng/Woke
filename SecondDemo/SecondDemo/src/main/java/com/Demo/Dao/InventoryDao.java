package com.Demo.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="inventoryDao")
public class InventoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * 插入
	 */	
	public int insert(Map<String,Object> inventory)
	{		
		String id=UUID.randomUUID().toString();
		String position="待入库";
		String box_status="运行中";
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String SQL = "insert into inventory (inventory_id,order_id,box_id,position,create_time,box_status)Values(?,?,?,?,?,?)";
		int re=jdbcTemplate.update(SQL,new Object[] {id, inventory.get("order_id") ,inventory.get("box_id") ,position ,dateSQL,box_status });
		return re;		
	}
	
	/*
	 * 入库
	 */	
	public int putIn(String inventoryId)
	{		
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String position="仓库";
		String SQL = "update inventory set position=? , put_in_time=? where inventory_id=?";
		int re=jdbcTemplate.update(SQL,new Object[] {position,  dateSQL , inventoryId});
		return re;		
	}
	public int putIn(Map inventory)
	{		
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String position=(String) inventory.get("position");
		String box_id=(String) inventory.get("box_id");
		String SQL = "update inventory set position=? , put_in_time=?  where box_id=? and box_status='运行中' ";
		int re=jdbcTemplate.update(SQL,new Object[] {position,  dateSQL , box_id});
		return re;		
	}
	
	/*
	 * 取货
	 */
	public int takeAway(String inventoryId)
	{
		//当前时间，并转换为时间戳
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String position="取出";
		String box_status="结束";
		String SQL = "update inventory set position=? , take_away_time=? ,box_status=? where inventory_id=?";
		int re=jdbcTemplate.update(SQL,new Object[] {position,  dateSQL ,box_status, inventoryId});
		return re;	
	}
	
	/*
	 * 查询库存
	 */
	public List<Map<String,Object>> selectPutIn()
	{
		String SQL = "select * from inventory where position='仓库' order by put_in_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	public List<Map<String,Object>> selectRunning()
	{
		String SQL = "select * from inventory where box_status='运行中' order by create_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	public long countPutIn()
	{
		String SQL = "select count(*) inventory_count from inventory where position='仓库' ";
		Map<String,Object> inventory=jdbcTemplate.queryForMap(SQL);
		long count=(long) inventory.get("inventory_count");
		//System.out.println("Dao:"+order);
		return count;
	}
	
	public List<Map<String,Object>> sumPutIn()
	{
		String SQL = "select sum() from inventory where position='仓库'";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	
	/*
	 * 查询未入库
	 */
	public List<Map<String,Object>> selectNone()
	{
		String SQL = "select * from inventory where position='待入库' order by create_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	
	/*
	 * 查询已取货
	 */
	public List<Map<String,Object>> selectTaken()
	{
		String SQL = "select * from inventory where position='取出' order by create_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	
	public long countPutInEveryDay()
	{
		String SQL = "select DATE_FORMAT(put_in_time, '%Y-%m-%d') putInTime, count(*) inventory_count from inventory where position='仓库' group by putInTime order by putInTime DESC";
		Map<String,Object> inventory=jdbcTemplate.queryForMap(SQL);
		long count=(long) inventory.get("inventory_count");
		//System.out.println("Dao:"+order);
		return count;
	}
	
	
}
