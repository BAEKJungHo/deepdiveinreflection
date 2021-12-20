package reflection.study.reflectionapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import reflection.study.reflectionapi.code.Admin;
import reflection.study.reflectionapi.code.CommonUser;
import reflection.study.reflectionapi.code.User;

import java.lang.reflect.Field;
import java.util.Arrays;


@Slf4j
@DisplayName("리플렉션 API : 애노테이션 테스트")
class ReflectionAPIAnnotationTest {

    @DisplayName("클래스에 지정된 애노테이션 확인")
    @Test
    void findAnnotationsInClass() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        log.info("field NonNull Annotation = {}", userClass.getAnnotation(Admin.class));
    }

    @DisplayName("하위 클래스에서 상속된 애노테이션 확인")
    @Test
    void findInheritedAnnotationsInSubClass() throws Exception {
        Class<CommonUser> commonUserClass = CommonUser.class; // FQCN or instance.getClass() 를 써도 상관없음
        log.info("field NonNull Annotation = {}", commonUserClass.getAnnotation(Admin.class));
    }

    @DisplayName("하위 클래스에서 지정한 애노테이션 확인")
    @Test
    void findAnnotationsOnlyInSubClass() throws Exception {
        Class<CommonUser> commonUserClass = CommonUser.class; // FQCN or instance.getClass() 를 써도 상관없음
        Arrays.stream(commonUserClass.getDeclaredAnnotations()).forEach(System.out::println);
    }

    @DisplayName("하위 클래스에서지정한 애노테이션과 상속된 애노테이션 확인")
    @Test
    void findAnnotationsInSubClass() throws Exception {
        Class<CommonUser> commonUserClass = CommonUser.class; // FQCN or instance.getClass() 를 써도 상관없음
        Arrays.stream(commonUserClass.getAnnotations()).forEach(System.out::println);
    }

    @DisplayName("필드에 지정된 애노테이션 확인")
    @Test
    void findAnnotationsInFields() throws Exception {
        Class<User> userClass = User.class; // FQCN or instance.getClass() 를 써도 상관없음
        Field field = userClass.getDeclaredField("birthdate");
        log.info("field NonNull Annotation = {}", field.getAnnotation(NonNull.class)); // @org.springframework.lang.NonNull()
        log.info("field Autowired Annotation = {}", field.getAnnotation(Autowired.class)); // null
    }

    @DisplayName("애노테이션에 지정된 값 참조")
    @Test
    void accessToAnnotationValues() throws Exception {
        Arrays.stream(User.class.getDeclaredFields()).forEach(field -> {
            Arrays.stream(field.getAnnotations()).forEach(annotation -> {
                if(annotation instanceof Admin) {
                    Admin admin = (Admin) annotation;
                    log.info("Admin Value = {}", admin.value());
                    log.info("Admin Name = {}", admin.name());
                    log.info("Admin SerialNumber = {}", admin.serialNumber());
                }
            });
        });
    }
}
