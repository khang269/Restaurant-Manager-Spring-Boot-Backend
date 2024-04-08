package critisys.res.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * guide
 * basic spring boot guide:
 * 	https://spring.academy/courses/spring-boot
 * 	https://www.tutorialspoint.com/spring_boot/index.htm
 * 	https://kungfutech.edu.vn/bai-viet/spring-boot
 * rest api:
 * 	https://www.baeldung.com/sprint-boot-multipart-requests
 * role-base access : 
 * 	https://howtodoinjava.com/spring-security/spring-boot-role-based-authorization/
 * 	https://blog.tericcabrel.com/role-base-access-control-spring-boot/
 * 	https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 * 	https://dev.to/alphaaman/building-a-role-based-access-control-system-with-jwt-in-spring-boot-a7l	
 * 	https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter	
 */

@SpringBootApplication
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

}
