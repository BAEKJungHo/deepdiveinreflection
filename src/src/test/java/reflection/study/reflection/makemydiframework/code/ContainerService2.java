package reflection.study.reflection.makemydiframework.code;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerService2 {

    // 메서드로 넘겨주는 클래스 타입으로 리턴하기 위해서 제네릭 메서드 생성
    public static <T> T getObject(Class<T> classType) {
        T instance = createInstance(classType);
        Arrays.stream(classType.getDeclaredFields()).forEach(field -> {
            if(field.getAnnotation(Inject.class) != null) {
                Object fieldInstance = createInstance(field.getType());
                field.setAccessible(true); // 필드는 보통 private 으로 되어있어서(public 이면 안해줘도 된다.) accessible true 설정
                try {
                    field.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
