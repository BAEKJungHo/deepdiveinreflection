package reflection.study.reflection.makemydiframework.code;

import java.lang.reflect.InvocationTargetException;

public class ContainerService {

    // 메서드로 넘겨주는 클래스 타입으로 리턴하기 위해서 제네릭 메서드 생성
    public static <T> T getObject(Class<T> classType) {
        return createInstance(classType);
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
