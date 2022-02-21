# CGLIB

> CGLIB : Code Generator Library

- CGLIB 은 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리이다.
- CGLIB 은 JDK Dynamic Proxy 와 달리 인터페이스 없이, 구체 클래스만 가지고 동적 프록시를 생성할 수 있다.
- CGLIB 은 외부 라이브러리이지만, 스프링 프레임워크가 스프링 내부 소스 코드에 포함했다. 따라서, 스프링을 사용하는 경우 별도의 라이브러리를 추가할 필요 없이 사용할 수 있다.

![cglib](https://user-images.githubusercontent.com/47518272/154974123-265cc984-840a-4ef5-9736-4aef895f9c36.png)

```java
package org.springframework.cglib.proxy;

import java.lang.reflect.Method;

public interface MethodInterceptor extends Callback {
    /**
     * @params obj : CGLIB 이 적용된 객체
     * @params method : 호출된 메서드
     * @params args : 메서드를 호출하면서 전달된 인수
     * @params proxy : 메서드 호출에 사용
     */
    Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable;
}
```

## Example

> Proxy 는 항상 호출할 대상(target) 이 필요하다.

- ConcreteC

```java
@Slf4j
public class ConcreteC {
    public String call() {
        log.info("C 호출");
        return "C";
    }
}
```

- MethodInterceptor 구현체
  - MethodInterceptor 를 구현하여 CGLIB 프록시의 실행 로직을 정의한다.

```java
@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    // 프록시가 호출할 실제 대상
    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // 실제 대상을 동적으로 호출
        Object result = methodProxy.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
```

> Method 를 사용해도 되는데, CGLIB 자체에서 내부적으로 최적화 하는 코드가 존재해서 MethodProxy 를 사용하여 호출하는 것이 성능상 이점이 있다고 한다.

- 테스트

```java
@Test
void cglib() {
    ConcreteC target = new ConcreteC();

    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(ConcreteC.class);
    enhancer.setCallback(new TimeMethodInterceptor(target));
    ConcreteC proxy = (ConcreteC) enhancer.create();
    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.call();

}
```

## References 

- [인프런. 스프링 핵심원리 고급](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard)
