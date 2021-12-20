package reflection.study.reflectionapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import reflection.study.reflectionapi.code.CommonUser;
import reflection.study.reflectionapi.code.User;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@Slf4j
@DisplayName("리플렉션 API : 클래스 정보 조회 테스트")
public class ReflectionAPIFindClassInformationTest {

    @DisplayName("클래스가 로딩만 되면 Class<T> 타입의 인스턴스가 생성된다.")
    @Test
    void classTInstanceCreatedAfterLoadingByClassLoader() throws Exception {
        // ClassLoader 에 의해 모든 클래스들이 로딩이 되면, 해당 클래스 타입의 Class 객체를 생성하여 '힙' 영역에 저장한다.
        useDotClass();
        useInstanceDotGetClass();
        useFQCN();
    }

    @DisplayName("클래스에 들어있는 모든 필드중 public Field 정보를 확인")
    @Test
    void findAllPublicFieldsInClass() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Arrays.stream(userClass.getFields()).forEach(System.out::println);
    }

    @DisplayName("클래스에 들어있는 선언된 모든 필드 정보를 확인 : public, protected, default, private")
    @Test
    void findAllDeclaredFieldsInClass() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Arrays.stream(userClass.getDeclaredFields()).forEach(System.out::println);
    }

    @DisplayName("클래스에 들어있는 선언된 모든 필드 중, 지정한 이름에 대한 필드만 확인")
    @Test
    void findAllDeclaredFieldsByNameInClass() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Field field = userClass.getDeclaredField("birthdate");
        log.info("field name = {}", field.getName());
    }

    @DisplayName("필드에 지정된 애노테이션 확인")
    @Test
    void findAnnotationsInFields() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Field field = userClass.getDeclaredField("birthdate");
        log.info("field NonNull Annotation = {}", field.getAnnotation(NonNull.class)); // @org.springframework.lang.NonNull()
        log.info("field Autowired Annotation = {}", field.getAnnotation(Autowired.class)); // null
    }

    @DisplayName("접근 불가능한 필드에 접근 : 실패")
    @Test
    void accessToNotAccessibleField_Fail() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        User user = new User();
        Arrays.stream(userClass.getDeclaredFields()).forEach(field -> {
            try {
                log.info("{}, {}", field, field.get(user));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @DisplayName("접근 불가능한 필드에 접근 : 성공")
    @Test
    void accessToNotAccessibleField_Success() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        User user = new User();
        Arrays.stream(userClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                log.info("{}, {}", field, field.get(user));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @DisplayName("필드 접근지시자 확인")
    @Test
    void findDeclaredFieldsAccessModifier() throws Exception {
        Arrays.stream(User.class.getDeclaredFields()).forEach(field -> {
            int modifiers = field.getModifiers();
            log.info("{}", field);
            log.info("{}", Modifier.isPrivate(modifiers));
            log.info("{}", Modifier.isStatic(modifiers));
            log.info("{}", Modifier.isAbstract(modifiers));
        });
    }

    @DisplayName("메서드에 접근")
    @Test
    void accessToMethods() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Arrays.stream(userClass.getMethods()).forEach(System.out::println);
    }

    @DisplayName("생성자에 접근")
    @Test
    void accessToDeclaredConstructors() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Arrays.stream(userClass.getDeclaredConstructors()).forEach(System.out::println);
    }

    @DisplayName("Super Class 에 접근")
    @Test
    void accessToSuperClass() throws Exception {
        log.info("{}", CommonUser.class.getSuperclass());
    }

    @DisplayName("Interface 에 접근")
    @Test
    void accessToInterface() throws Exception {
        Arrays.stream(CommonUser.class.getInterfaces()).forEach(System.out::println);
    }

    private void useDotClass() {
        // 타입.class 로 Class<T> 인스턴스에 접근할 수 있다.
        Class<User> userClass = User.class;
    }

    private void useInstanceDotGetClass() {
        // 인스턴스.getClass() 로 접근
        User user = new User();
        Class<? extends User> userClass = user.getClass();
    }

    private void useFQCN() throws ClassNotFoundException {
        // FQCN : Full Qualified Class Name (Package 명을 포함한 클래스 이름)
        // ClassNotFoundException 발생 가능성이 있음
        final String FQCN = "reflection.study.reflectionapi.code.User";
        Class<?> userClass = Class.forName(FQCN);
    }
}
