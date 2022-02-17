package reflection.study.dynamicproxy;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reflection.study.dynamicproxy.code.*;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class DynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        /*
          newProxyInstance
          first arg : class loader
          second arg : 어떤 인터페이스 기반으로 프록시를 만들지 지정(클래스 배열), 인터페이스 구현체가 여러개 있을 수 있기 때문이다.
          third arg : InvocationHandler 구현체 -> 프록시가 사용할 로직
         */
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void concreteTest() {
        ConcreteC target = new ConcreteC();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        assertThatThrownBy(() ->
                    Proxy.newProxyInstance(ConcreteC.class.getClassLoader(), new Class[]{ConcreteC.class}, handler))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
