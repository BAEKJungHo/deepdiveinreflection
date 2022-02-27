package reflection.study.aop.code;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

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
