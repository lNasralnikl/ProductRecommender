package com.recom.bot;

import com.recom.bot.config.TestBotConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestBotConfig.class)
class BotApplicationTests {

	@Test
	void contextLoads() {
	}

}
