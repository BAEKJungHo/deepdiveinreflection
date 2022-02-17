package reflection.study.dynamicproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteC {
    public String call() {
        log.info("C 호출");
        return "C";
    }
}
