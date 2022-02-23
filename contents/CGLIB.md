# CGLIB

> CGLIB : Code Generator Library

- CGLIB 은 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리이다.
- CGLIB 은 JDK Dynamic Proxy 와 달리 인터페이스 없이, 구체 클래스만 가지고 동적 프록시를 생성할 수 있다.
- CGLIB는 구체 클래스를 상속(extends)해서 프록시를 만든다.
    - 따라서, 상속에 의한 제약조건이 생긴다.
    - CGLIB는 자식 클래스를 동적으로 생성하기 때문에 기본 생성자가 필요하다.
    - 클래스 혹은 메서드에 final 이 붙으면 안된다.
- CGLIB 은 외부 라이브러리이지만, 스프링 프레임워크가 스프링 내부 소스 코드에 포함했다. 따라서, 스프링을 사용하는 경우 별도의 라이브러리를 추가할 필요 없이 사용할 수 있다.
- [성능 차이 분석 표](https://web.archive.org/web/20150520175004/https://docs.codehaus.org/display/AW/AOP+Benchmark)

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

    // Enhancer 가 CGLIB Proxy 를 만든다.
    Enhancer enhancer = new Enhancer();
    
    // CGLIB 는 구체 클래스를 상속 받아서 프록시를 생성할 수 있다.
    // 어떤 구체 클래스를 상속 받을지 정한다.
    enhancer.setSuperclass(ConcreteC.class);
    
    // // 프록시에 적용할 실행 로직을 할당한다.
    enhancer.setCallback(new TimeMethodInterceptor(target));
    
    // 프록시 생성
    // ConcreteC$$EnhancerByCGLIB$$860aca8f@2209
    ConcreteC proxy = (ConcreteC) enhancer.create();
    
    log.info("targetClass={}", target.getClass()); // targetClass=class reflection.study.dynamicproxy.code.ConcreteC
    log.info("proxyClass={}", proxy.getClass()); // proxyClass=class reflection.study.dynamicproxy.code.ConcreteC$$EnhancerByCGLIB$$860aca8f

    proxy.call();
}
```

![CGLIB2](https://user-images.githubusercontent.com/47518272/154981191-ced061f4-9b99-437b-beb2-d7c038cd2567.png)

## References 

- [인프런. 스프링 핵심원리 고급](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard)
