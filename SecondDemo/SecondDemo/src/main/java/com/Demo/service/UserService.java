package com.Demo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Demo.Dao.UserDao;
import com.Demo.Pojo.User;

@Service(value="userService")
public class UserService {
	@Autowired
    private UserDao userDao;
	
	public List selectUsers()
	{
		List<User> users;
		List<Map<String,Object>> list=userDao.select();
		System.out.println("Service:"+list);
		return list;
	}
}
