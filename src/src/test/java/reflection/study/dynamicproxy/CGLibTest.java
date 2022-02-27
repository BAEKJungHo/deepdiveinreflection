package reflection.study.dynamicproxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import reflection.study.dynamicproxy.code.ConcreteC;
import reflection.study.dynamicproxy.code.TimeMethodInterceptor;

@Slf4j
class CGLibTest {

    @Test
    void cglib() {
        ConcreteC target = new ConcreteC();

        // Enhancer 가 CGLIB Proxy 를 만든다.
        Enhancer enhancer = new Enhancer();

        // CGLIB 는 구체 클래스를 상속 받아서 프록시를 생성할 수 있다.
        // 어떤 구체 클래스를 상속 받을지 정한다.
        enhancer.setSuperclass(ConcreteC.class);

        // 프록시에 적용할 실행 로직을 할당한다.
        enhancer.setCallback(new TimeMethodInterceptor(target));

        // 프록시 생성
        // ConcreteC$$EnhancerByCGLIB$$860aca8f@2209
        ConcreteC proxy = (ConcreteC) enhancer.create();

        log.info("targetClass={}", target.getClass()); // targetClass=class reflection.study.dynamicproxy.code.ConcreteC
        log.info("proxyClass={}", proxy.getClass()); // proxyClass=class reflection.study.dynamicproxy.code.ConcreteC$$EnhancerByCGLIB$$860aca8f

        proxy.call();
    }
}
