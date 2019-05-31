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
	 * ����
	 */	
	public int insert(Map<String,Object> inventory)
	{		
		String id=UUID.randomUUID().toString();
		String position="�����";
		String box_status="������";
		//��ǰʱ�䣬��ת��Ϊʱ���
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String SQL = "insert into inventory (inventory_id,order_id,box_id,position,create_time,box_status)Values(?,?,?,?,?,?)";
		int re=jdbcTemplate.update(SQL,new Object[] {id, inventory.get("order_id") ,inventory.get("box_id") ,position ,dateSQL,box_status });
		return re;		
	}
	
	/*
	 * ���
	 */	
	public int putIn(String inventoryId)
	{		
		//��ǰʱ�䣬��ת��Ϊʱ���
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String position="�ֿ�";
		String SQL = "update inventory set position=? , put_in_time=? where inventory_id=?";
		int re=jdbcTemplate.update(SQL,new Object[] {position,  dateSQL , inventoryId});
		return re;		
	}
	public int putIn(Map inventory)
	{		
		//��ǰʱ�䣬��ת��Ϊʱ���
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String position=(String) inventory.get("position");
		String box_id=(String) inventory.get("box_id");
		String SQL = "update inventory set position=? , put_in_time=?  where box_id=? and box_status='������' ";
		int re=jdbcTemplate.update(SQL,new Object[] {position,  dateSQL , box_id});
		return re;		
	}
	
	/*
	 * ȡ��
	 */
	public int takeAway(String inventoryId)
	{
		//��ǰʱ�䣬��ת��Ϊʱ���
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String position="ȡ��";
		String box_status="����";
		String SQL = "update inventory set position=? , take_away_time=? ,box_status=? where inventory_id=?";
		int re=jdbcTemplate.update(SQL,new Object[] {position,  dateSQL ,box_status, inventoryId});
		return re;	
	}
	
	/*
	 * ��ѯ���
	 */
	public List<Map<String,Object>> selectPutIn()
	{
		String SQL = "select * from inventory where position='�ֿ�' order by put_in_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	public List<Map<String,Object>> selectRunning()
	{
		String SQL = "select * from inventory where box_status='������' order by create_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	public long countPutIn()
	{
		String SQL = "select count(*) inventory_count from inventory where position='�ֿ�' ";
		Map<String,Object> inventory=jdbcTemplate.queryForMap(SQL);
		long count=(long) inventory.get("inventory_count");
		//System.out.println("Dao:"+order);
		return count;
	}
	
	public List<Map<String,Object>> sumPutIn()
	{
		String SQL = "select sum() from inventory where position='�ֿ�'";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	
	/*
	 * ��ѯδ���
	 */
	public List<Map<String,Object>> selectNone()
	{
		String SQL = "select * from inventory where position='�����' order by create_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	
	/*
	 * ��ѯ��ȡ��
	 */
	public List<Map<String,Object>> selectTaken()
	{
		String SQL = "select * from inventory where position='ȡ��' order by create_time DESC";
		List<Map<String,Object>> inventory=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+order);
		return inventory;
	}
	
	public long countPutInEveryDay()
	{
		String SQL = "select DATE_FORMAT(put_in_time, '%Y-%m-%d') putInTime, count(*) inventory_count from inventory where position='�ֿ�' group by putInTime order by putInTime DESC";
		Map<String,Object> inventory=jdbcTemplate.queryForMap(SQL);
		long count=(long) inventory.get("inventory_count");
		//System.out.println("Dao:"+order);
		return count;
	}
	
	
}
