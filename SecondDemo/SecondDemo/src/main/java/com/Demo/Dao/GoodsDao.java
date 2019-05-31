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
	 * ���루������
	 */	
	public int insert(Map<String,Object> goods)
	{
		if(goods.get("user_id")!=null)
		{
		String id=UUID.randomUUID().toString();
		//��ǰʱ�䣬��ת��Ϊʱ���
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());
		String goods_status="������";
		String SQL = "insert into goods(goods_id,user_id,goods_name,goods_type,goods_desc,goods_image,goods_price,create_time,goods_status)Values(?,?,?,?,?,?,?,?,?)";
		int re=jdbcTemplate.update(SQL,new Object[] {id, goods.get("user_id"),goods.get("goods_name"),goods.get("goods_type"),goods.get("goods_desc"), goods.get("goods_image"),goods.get("goods_price"),dateSQL,goods_status});
		return re;
		}
		return 0;
	}
	/*
	 * ��ѯ������Ϣ
	 */
	public List<Map<String,Object>> select()
	{
		String SQL = "select * from goods where goods_status='������' order by create_time DESC";
		List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL);
		//System.out.println("Dao:"+goods);
		return goods;
	}
	/*
	 * ��ѯ������Ϣ
	 */
	public Map<String,Object> select(String goods_id)
	{
		String SQL = "select * from goods where goods_id=?";
		Map<String,Object> goods=jdbcTemplate.queryForMap(SQL,new Object[] {goods_id});
		return goods;
	}
	/*
	 * ��ѯ���˷�������Ʒ��Ϣ
	 */
	public List<Map<String,Object>> userSelect(String phone_id)
	{
		String SQL = "select * from goods where user_id=? and goods_status='������'  order by create_time DESC";
		List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {phone_id});
		return goods;
	}
	/*
	 * ��ѯ������������Ʒ��Ϣ
	 */
	public List<Map<String,Object>> sellSelect(String phone_id)
	{
		String SQL = "select * from goods where user_id=? and goods_status='������'  order by create_time DESC";
		List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {phone_id});
		return goods;
	}
	/*
	 * ��Ʒɸѡ������
	 */
	public List<Map<String,Object>> selectType(String goods_type)
	{
		if(goods_type!=null&&!goods_type.equals("ȫ��"))
		{
			String SQL = "select * from goods where goods_type=? and goods_status='������'  order by create_time DESC";
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
	 * ��Ʒ�ȶ�����
	 */
	public List<Map<String,Object>> selectOrderByLike(String goods_type)
	{
		
		if(goods_type!=null&&!goods_type.equals("ȫ��"))
		{
			String SQL = "select * from goods where goods_type=? and goods_status='������'   order by goods_like DESC, create_time DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {goods_type});
			return goods;
		}
		else
		{
			String SQL = "select * from goods where goods_status='������'   order by goods_like DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL);
			return goods;
		}
	}
	/*
	 * ��Ʒ�۸�����
	 */
	public List<Map<String,Object>> selectOrderByPrice(String goods_type)
	{		
		if(goods_type!=null&&!goods_type.equals("ȫ��"))
		{
			String SQL = "select * from goods where goods_type=? and goods_status='������'   order by goods_price DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL,new Object[] {goods_type});
			return goods;
		}
		else
		{
			String SQL = "select * from goods where goods_status='������'   order by goods_price DESC";
			List<Map<String,Object>> goods=jdbcTemplate.queryForList(SQL);
			return goods;
		}
	}
	/*
	 * �޸Ļ�����Ϣ
	 */
	public int updateInfor(Map<String,Object> goods)
	{
		String SQL = "update goods set goods_name=? image=? price=? where goods_id=?";
		int re=jdbcTemplate.update(SQL, new Object[] {goods.get("goods_name"),goods.get("image"),goods.get("price"), goods.get("goods_id")});
		return re;
	}
	
	/*
	 * �޸���Ʒ״̬
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
