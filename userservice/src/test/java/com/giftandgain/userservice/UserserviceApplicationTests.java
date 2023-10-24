package com.giftandgain.userservice;

import com.giftandgain.userservice.models.User;
import com.giftandgain.userservice.repository.UserRepo;
import com.giftandgain.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SpringBootTest
class UserserviceApplicationTests {
	private static final org.slf4j.Logger log
			= org.slf4j.LoggerFactory.getLogger(UserserviceApplicationTests.class);

	@Mock
	private UserRepo userRepo;
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	private com.giftandgain.userservice.models.User userTest;

	@BeforeEach
	public void setup() {
		userTest = new User();
		userTest.setName("arnold");
		userTest.setEmail("arnold@gmail.com");
		ArrayList<String> authorities = new ArrayList<>();
		authorities.add("ROLE_MANAGER");
		userTest.setAuthorities(authorities);

	}

	@Test
	void contextLoads() {
	}

	@Test
	void sampleTest_1() {
		UserDetails user1 = userServiceImpl.loadUserByUsername("arnold");
		UserDetails user2 = userServiceImpl.loadUserByUsername("arnold");
		log.info("user1 is: {}", user1);
		assertEquals(user1, user2);
	}

}
