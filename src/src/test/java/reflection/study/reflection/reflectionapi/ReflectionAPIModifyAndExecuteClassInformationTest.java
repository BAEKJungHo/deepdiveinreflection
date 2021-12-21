package reflection.study.reflection.reflectionapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reflection.study.reflection.reflectionapi.code.Team;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("리플렉션 API : 클래스 정보 수정 및 실행 테스트")
public class ReflectionAPIModifyAndExecuteClassInformationTest {

    @DisplayName("인스턴스 만들기 테스트")
    @Test
    void createInstance() throws Exception {
        Class<?> teamClass = Class.forName("reflection.study.reflection.reflectionapi.code.Team");
        // Deprecated Class.newInstance()
        // 따라서, 생성자를 통해서 만들어야 한다.
        Constructor<?> constructor = teamClass.getConstructor(null);
        Team team = (Team) constructor.newInstance();
        assertNotNull(team);
    }

    @DisplayName("클래스의 public 필드에서 값을 가져오고, 값 넣기 테스트")
    @Test
    void findPublicFieldsAndInjectValueInFields() throws Exception {
        Class<?> teamClass = Class.forName("reflection.study.reflection.reflectionapi.code.Team");
        Field field = teamClass.getDeclaredField("name");
        log.info("{}", field.get(null)); // public 이기 때문에 파라미터에 클래스를 지정안해도 된다.
        assertNotNull(field.get(null));
        field.set(null, "ABC");
        assertEquals(field.get(null), "ABC");
    }

    @DisplayName("클래스의 public 을 제외한 필드에서 값을 가져오고, 값 넣기 테스트")
    @Test
    void findOtherFieldsAndInjectValueInFields() throws Exception {
        Class<?> teamClass = Class.forName("reflection.study.reflection.reflectionapi.code.Team");
        Constructor<?> constructor = teamClass.getConstructor(null);
        Team team = (Team) constructor.newInstance();

        Field field = teamClass.getDeclaredField("leader");
        field.setAccessible(true);
        assertNotNull(field.get(team));
        field.set(team, "ABC");
        assertEquals(field.get(team), "ABC");
    }

    @DisplayName("클래스의 private 메서드 가져와서 사용하기")
    @Test
    void usePrivateMethodInClass() throws Exception {
        Class<?> teamClass = Class.forName("reflection.study.reflection.reflectionapi.code.Team");
        Constructor<?> constructor = teamClass.getConstructor(null);
        Team team = (Team) constructor.newInstance();

        Method method = teamClass.getDeclaredMethod("printName");
        method.setAccessible(true);
        method.invoke(team);
    }

    @DisplayName("클래스의 파라미터가 존재하는 메서드 가져와서 사용하기")
    @Test
    void useMethodHasParametersInClass() throws Exception {
        Class<?> teamClass = Class.forName("reflection.study.reflection.reflectionapi.code.Team");
        Constructor<?> constructor = teamClass.getConstructor(null);
        Team team = (Team) constructor.newInstance();

        Method method = teamClass.getDeclaredMethod("sum", int.class, int.class);
        method.setAccessible(true);
        int invokeResult = (int) method.invoke(team, 1 , 2);
        assertEquals(invokeResult, 3);
    }

}
