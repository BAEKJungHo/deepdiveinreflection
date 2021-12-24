# Java Reflection

## Reflection 이란 ?

리플렉션은 일반적으로 JVM 에서 실행되는 애플리케이션의 `런타임(Runtime)`에 동작을 검사하거나 수정하는 기능이 필요한 프로그램에서 사용된다. 리플렉션은 Java 기능 중 `고급 기능`에 속하며, 
일반적으로 리플렉션을 활용하여 개발할 일은 극히 드물다. 

리플렉션은 `Class<T>` 에서부터 시작된다.

`Class<T>` 는 클래스가 로딩된 후에 `타입.class`로 접근할 수 있다.

```java
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
    final String FQCN = "reflection.study.reflection.reflectionapi.code.User";
    Class<?> userClass = Class.forName(FQCN);
}
```

클래스를 로딩하는 것은 `ClassLoader`의 역할이라는 것을 배웠다. 

> ClassLoader 는 동적 적재라는 방식을 사용하여 로딩, 링크, 초기화 과정을 수행하여 .class 파일을 JVM 의 RunTimeDataArea 즉, 메모리 영역에 넣는 작업을 담당한다.

리플렉션을 사용하면 `동적(dynamic, runtime)`으로 다음과 같은 일을 할 수 있다.

- 클래스 객체(`Class<T>`) 생성
- 클래스 객체로 인스턴스 생성
- 클래스에 있는 필드, 생성자, 메서드 정보를 조회, 동작, 수정
  - 단, 생성자의 매개변수 정보는 가져올 수 없다.

리플렉션은 프레임워크나 라이브러리에서 자주 사용된다. 예를 들면, 스프링 프레임워크에서 클래스를 빈으로 등록할때, 개발자가 어떤 클래스를 빈으로 등록할지는 아무도 모른다. 따라서 이런한 과정을
동적으로 해결하기 위해서 리플렉션이 사용된다고 보면된다. `스프링의 DI(Dependency Injection)`같은 경우에도 리플렉션이 사용된다.

## Reflection API : 클래스 로더 사용

[Reflection API 클래스 로더 사용](https://github.com/BAEKJungHo/deepdiveinreflection/tree/main/src/src/test/java/reflection/study/reflection/classloader)

## Reflection API : 클래스 정보 조회

[Reflection API 클래스 정보 조회](https://github.com/BAEKJungHo/java-reflection/blob/main/src/src/test/java/reflection/study/reflection/reflectionapi/ReflectionAPIFindClassInformationTest.java)

## Reflection API : 클래스 정보 수정 및 실행

[Reflection API 클클래스 정보 수정 및 실행](https://github.com/BAEKJungHo/java-reflection/blob/main/src/src/test/java/reflection/study/reflection/reflectionapi/ReflectionAPIModifyAndExecuteClassInformationTest.java)

## Reflection API : 애노테이션

[Reflection API : 클래스내에 지정된 애노테이션 정보 조회](https://github.com/BAEKJungHo/java-reflection/blob/main/src/src/test/java/reflection/study/reflection/reflectionapi/ReflectionAPIAnnotationTest.java)
