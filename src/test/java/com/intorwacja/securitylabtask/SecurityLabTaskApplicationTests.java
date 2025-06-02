package com.intorwacja.securitylabtask;

import com.intorwacja.securitylabtask.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestSecurityConfig.class)
class SecurityLabTaskApplicationTests {

    @Test
    void contextLoads() {
    }

}
