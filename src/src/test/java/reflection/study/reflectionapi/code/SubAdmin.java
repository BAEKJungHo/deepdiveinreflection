package reflection.study.reflectionapi.code;

import java.lang.annotation.*;

/**
 * 바이트 코드를 로딩했을 때 기본적으로 애노테이션 정보는 빼고 읽어온다.
 * 만약에 같이 읽어오고 싶으면 RetentionPolicy.RUNTIME 으로 설정하면 된다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited // 상속 가능
public @interface SubAdmin {
    String value(); // 값을 하나만 받을 때 유용 Ex. @Admin("User")

    String name() default "SUB ADMIN"; // Ex. @Admin(name = "Test")

    int serialNumber() default 111111;
}
