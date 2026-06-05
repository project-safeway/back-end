package com.safeway.tech;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class SafewayApplicationTests {

	@MockitoBean
	private RabbitTemplate rabbitTemplate;

	@MockitoBean
	private RedisConnectionFactory redisConnectionFactory;

	@Test
	void contextLoads() {
	}

}
