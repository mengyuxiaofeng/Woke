import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Demo.Dao.UserDao;
import com.Demo.service.UserService;

public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
		UserService userservice = (UserService) context.getBean("userService");
		userservice.selectUsers();
	}
}
