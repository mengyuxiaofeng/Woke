package com.Demo.Dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="userDao")
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * ���루ע�ᣩ
	 */	
	public int insert(Map<String,Object> user)
	{
		if(user.get("phone_id")!=null&&user.get("password")!=null)
		{
		String id=UUID.randomUUID().toString();
		//��ǰʱ�䣬��ת��Ϊʱ���
		Date d = new Date();
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(d.getTime());		
		String SQL = "insert into user(user_id,phone_id,password,create_time)Values(?,?,?,?)";
		int re=jdbcTemplate.update(SQL, id, user.get("phone_id"), user.get("password"),dateSQL);
		System.out.println("insert");
		return re;
		}
		return 0;
	}
	/*
	 * ��ѯ������Ϣ
	 */
	public List<Map<String,Object>> select()
	{
		String SQL = "select * from user";
		List<Map<String,Object>> users=jdbcTemplate.queryForList(SQL);
		System.out.println("Dao:"+users);
		return users;
	}
	/*
	 * ��ѯ������Ϣ
	 */
	public Map<String,Object> selectId(String user_id)
	{
		String SQL = "select * from user where user_id=?";
		Map<String,Object> user=jdbcTemplate.queryForMap(SQL,new Object[] {user_id});
		return user;
	}
	public Map<String,Object> selectPhone(String phone_id)
	{
		Map<String,Object> user=new HashMap();
		String SQL = "select * from user where phone_id=?";
		if(selectCount(phone_id)>=1)
	    {		
			user=jdbcTemplate.queryForMap(SQL,phone_id);			
		}
		//System.out.println("Dao:"+user+selectCount(phone_id));
		return user;
	}
	
	public Map<String,Object> selectEmail(String user_email)
	{
		String SQL = "select * from user where user_email=?";
		Map<String,Object> user=jdbcTemplate.queryForMap(SQL,new Object[] {user_email});
		return user;
	}
	
	/*
	 * ��ѯ�Ƿ�����ֻ���
	 */
	public int selectCount(String phone_id)
	{
		int count=0;
		String SQL = "select count(*) from user where phone_id=?";		
		count=jdbcTemplate.queryForInt(SQL,new Object[] {phone_id});						
		return count;
	}
	/*
	 * �޸�����
	 */
	public int updatePassword(String password,String user_id)
	{
		String SQL = "update user set password=? where user_id=?";
		int re=jdbcTemplate.update(SQL,new Object[] { password,user_id});
		return re;
	}
	/*
	 * �޸Ļ�����Ϣ
	 */
	public int updateInfor(Map<String,Object> user)
	{
		String SQL = "update user set user_name=? , sex=? , user_college=? , user_major=? , student_id=? , user_email=? , user_address=? where user_id=?;";
		int re=jdbcTemplate.update(SQL, new Object[] {user.get("user_name"),user.get("sex"),user.get("user_college"),user.get("user_major"),user.get("student_id"),user.get("user_email"),user.get("user_address"), user.get("user_id")});
		return re;
	}
	/*
	 * �޸�ѧ����Ϣ
	 */
	public int updateStu(Map<String,Object> user)
	{
		String SQL = "update user set user_college=? user_major=? student_id=? where user_id=?";
		int re=jdbcTemplate.update(SQL, new Object[] {user.get("user_college"),user.get("user_major"),user.get("student_id"), user.get("user_id")});
		return re;
	}
	
	
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
