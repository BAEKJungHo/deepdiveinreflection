package reflection.study.howdoesworkspringdi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reflection.study.howdoesworkspringdi.code.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("스프링 DI 리플렉션 테스트")
@SpringBootTest
class SpringDIReflectionTest {

    @Autowired
    UserService userService;
    
    @DisplayName("DI 테스트")
    @Test
    void dependencyInjectionTest() throws Exception {
        assertNotNull(userService);
        assertNotNull(userService.userRepository);
    }
}
