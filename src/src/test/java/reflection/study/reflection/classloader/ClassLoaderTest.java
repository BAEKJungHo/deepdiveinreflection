package reflection.study.reflection.classloader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reflection.study.reflection.classloader.code.User;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("클래스 로더 테스트")
public class ClassLoaderTest {

    @DisplayName("클래스 로더를 사용하여 객체 동적 적재")
    @Test
    void dynamicLoadingUsingAClassLoader() throws Exception {
        final String binaryName = "reflection.study.reflection.classloader.code.User";
        Class<?> userClass = ClassLoader.getSystemClassLoader().loadClass(binaryName);
        Constructor<?> constructor = userClass.getConstructor(String.class);
        User user = (User) constructor.newInstance("JungHo");
        assertNotNull(user);
    }
}
