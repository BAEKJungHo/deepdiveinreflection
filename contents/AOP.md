# Aspect Orientied Programming

- [Proxy Pattern](https://techvu.dev/112?category=975490)
- [JDK Dynamic Proxy](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/JDK%20Dynamic%20Proxy.md)
- [CGLIB](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/CGLIB.md)

## 정의

- AOP 는 관점 지향 프로그래밍이라고 한다.
- 관점 지향 프로그래밍의 핵심은 `역할`과 `책임`을 적절하게 분리하는데에 있다.
  - 여러 비지니스 로직에 공통으로 적용되어있는 `부가 기능 로직`을 따로 `분리`하여 관리할 수 있다.

## ProxyFactory

![cglib](https://user-images.githubusercontent.com/47518272/155869847-b90dd6f0-5375-4642-abc2-89ba0eb0b839.png)

- 스프링은 `ProxyFactory` 라는 기능을 사용하여 인터페이스 여부에 따라서 JDK Dynamic Proxy 를 쓸지, CGLIB 을 쓸지 정한다.

![proxyfactory](https://user-images.githubusercontent.com/47518272/155869966-c338d163-7186-4088-8087-c471b0519f3c.png)

- 부가 기능을 적용하기 위해서 JDK Dynamic Proxy 의 InvocationHandler 와 CGLIB 의 MethodInterceptor 를 따로 만들 필요 없고 `Advice` 만 만들면 된다.
  - 즉, InvocationHandler 나 MethodInterceptor 는 Advice 를 호출하게 된다.

![advice](https://user-images.githubusercontent.com/47518272/155869975-46984cad-3cd5-4a9c-be7b-14a28d43481a.png)

## Advice 만드는 방법

> Advice 는 프록시에서 제공할 부가 기능 로직이라고 생각하면 된다.

- 스프링이 제공하는 MethodInterceptor(CGLIB 과 이름이 동일하다.)
- MethodInterceptor 는 Interceptor 를 상속하고 Interceptor 는 Advice 인터페이스를 상속한다.

```java
package org.aopalliance.intercept;

public interface MethodInterceptor extends Interceptor {
  Object invoke(MethodInvocation invocation) throws Throwable;
}
```

- __Advice__

```java
@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // target 클래스를 호출하고 결과를 받는다
        // ProxyFactory 로 프록시를 생성하는 단계에서 이미 target 정보를 파라미터로 받기 때문에
        // 프록시가 호출할 실제 대상인 target 은 invocation 안에 존재한다.
        Object result = invocation.proceed();

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        return result;
    }
}
```

- __Test__

```java
@Slf4j
class ProxyFactoryTest {

    @Test
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        
        // target 이 인터페이스로 구현되어있으면 JDK Dynamic Proxy 생성, 구체 클래스 기반이면 CGLIB Proxy 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }
}
```

> `proxyFactory.setProxyTargetClass(true);` 를 적용하면 인터페이스가 있어도 CGLIB 을 사용한다.

## Pointcut, Advice, Advisor

- __Pointcut__
  - 어디에 부가 기능을 적용할지 판단하는 `필터링 로직`
  - 주로 클래스와 메서드 이름으로 필터링
- __Advice__
  - 프록시가 호출하는 `부가 기능 로직`(프록시 로직)
- __Advisor__
  - Pointcut + Advice 한쌍을 의미
  - Advisor 는 `어떤 부가 기능 로직`을 `어디에` 적용할지를 알고 있다.

> AOP 의 핵심은 역할과 책임을 잘 구분하는 것이다. Pointcut 은 필터링 로직만 담당하고, Advice 는 부가 기능 로직만 담당한다.

## References

- [인프런. 스프링 핵심 원리 고급](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard)
