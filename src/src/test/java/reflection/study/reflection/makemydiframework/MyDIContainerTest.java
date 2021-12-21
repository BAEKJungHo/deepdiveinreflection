package reflection.study.reflection.makemydiframework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reflection.study.reflection.makemydiframework.code.ContainerService;
import reflection.study.reflection.makemydiframework.code.ContainerService2;
import reflection.study.reflection.makemydiframework.code.UserRepository;
import reflection.study.reflection.makemydiframework.code.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("나만의 DI 프레임워크 만들기")
class MyDIContainerTest {

    @DisplayName("ContainerService 에서 UserRepository Object 가져오기 테스트 : 성공")
    @Test
    void getUserRepositoryObject_Success() throws Exception {
        UserRepository userRepository = ContainerService.getObject(UserRepository.class);
        assertNotNull(userRepository);
    }

    @DisplayName("ContainerService 에서 UserService Object 가져오기 테스트 : 실패")
    @Test
    void getUserServiceObject_Fail() throws Exception {
        UserService userService = ContainerService.getObject(UserService.class);
        assertNotNull(userService);
        assertNotNull(userService.userRepository); // 여기서 NPE 로 실패
    }

    @DisplayName("ContainerService 에서 UserService Object 가져오기 테스트 : 성공")
    @Test
    void getUserServiceObject_Success() throws Exception {
        UserService userService = ContainerService2.getObject(UserService.class);
        assertNotNull(userService);
        assertNotNull(userService.userRepository); // 여기서 NPE 로 실패
    }
}
