# JDK Dynamic Proxy

> [Proxy Pattern](https://techvu.dev/112?category=975490)

- __동적 프록시(Dynamic Proxy)__
  - 개발자가 직접 프록시 클래스를 만들 필요 없이, 프록시 객체를 `동적(Runtime)`으로 생성해 주는 기술
- __JDK Dynamic Proxy__
  - JDK 동적 프록시는 `인터페이스를 기반`으로 프록시를 동적으로 만들어준다. 따라서 `인터페이스가 필수`이다.
  - JDK 동적 프록시에 적용할 로직은 `InvocationHandler` 인터페이스를 구현하여 작성하면 된다.

```java
package java.lang.reflect;

public interface InvocationHandler {
    /**
     * @params proxy the proxy instance that the method was invoked on(메서드가 호출된 프록시 자신)
     * @params method 호출한 메서드
     * @params args 메서드를 호출할 때 전달한 인수
     */
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}
```

## Example

- AInterface

```java
public interface AInterface {
    String call();
}
```

- AImpl

```java
@Slf4j
public class AImpl implements AInterface {
    @Override
    public String call() {
        log.info("A 호출");
        return "a";
    }
}
```

- InvocationHandler 구현체
  - InvocationHandler 를 구현하여 JDK 동적 프록시에 적용할 공통 로직을 개발할 수 있다.

```java
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    // 프록시가 호출할 대상
    private final Object target;

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // method.invoke(target, args) : 리플렉션을 사용해서 target 인스턴스의 메서드를 실행한다. args 는 메서드 호출 시 넘겨줄 인수이다.
        Object result = method.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
```

- 테스트

```java
@Test
void dynamicA() {
    AInterface target = new AImpl();
    TimeInvocationHandler handler = new TimeInvocationHandler(target);

    AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

    proxy.call();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
}
```

- 결과

```
18:42:48.209 [main] INFO reflection.study.dynamicproxy.code.TimeInvocationHandler - TimeProxy 실행
18:42:57.953 [main] INFO reflection.study.dynamicproxy.code.AImpl - A 호출
18:43:02.900 [main] INFO reflection.study.dynamicproxy.code.TimeInvocationHandler - TimeProxy 종료 resultTime=12964
18:43:04.451 [main] INFO reflection.study.dynamicproxy.DynamicProxyTest - targetClass=class reflection.study.dynamicproxy.code.AImpl
18:43:04.451 [main] INFO reflection.study.dynamicproxy.DynamicProxyTest - proxyClass=class com.sun.proxy.$Proxy9
```

## References

- [Proxy (Java Plaform SE 8)](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
- [JDK Dynamic Proxies](https://www.byteslounge.com/tutorials/jdk-dynamic-proxies)
