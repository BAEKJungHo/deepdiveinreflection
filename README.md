# Deep Dive In Reflection

## π― Goals

- μλ°μ μ€νλ§μ λν΄ νμΈ΅ λ κΉμ μ΄ν΄λ₯Ό νμ.

1. μλ° νλ‘κ·Έλ¨μ μ€νλ°©λ²κ³Ό JVM μν€νμ²μ λν΄ λ°°μ°μ.
2. Reflection λ₯Ό μ΄ν΄νμ.
3. Reflection μ λν μ΄ν΄λ₯Ό λ°νμΌλ‘ Annotation μ λ€μ κ³΅λΆν΄λ³΄μ.
4. POJO μ μ€νλ§μ μ² νμ λν΄ μ΄ν΄νμ.
5. μ€νλ§μ 3λ ν΅μ¬ κΈ°μ μ λν΄μ κ³΅λΆνμ.

## π Table of Contents

- [Execution of Java Program](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/Execution%20of%20Java%20Program.md)
- [JVM Architecture](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/JVM%20Architecture.md)
- [Java Reflection](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/Java%20Reflection.md)
- [Annotation](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/Annotation.md)
- [POJO μ μ€νλ§μ μ² ν](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/POJO.md)
- [[μ€νλ§ 3λ ν΅μ¬ κΈ°μ ] DI](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/%EC%8A%A4%ED%94%84%EB%A7%81%20DI%20%EA%B0%80%20%EB%8F%99%EC%9E%91%ED%95%98%EB%8A%94%20%EC%9B%90%EB%A6%AC.md)
- [Proxy Pattern](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/Proxy%20Pattern.md)
- [JDK Dynamic Proxy](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/JDK%20Dynamic%20Proxy.md)
- [CGLIB](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/CGLIB.md)
- [[μ€νλ§ 3λ ν΅μ¬ κΈ°μ ] AOP](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/AOP.md)
- [Weaving](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/Weaving.md)
- [[μ€νλ§ 3λ ν΅μ¬ κΈ°μ ] PSA](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/PSA.md)
- μ€νλ§ λ°μ΄ν° JPA κ° λμνλ μλ¦¬

## References

- [λ μλ°, μ½λλ₯Ό μ‘°μνλ λ€μν λ°©λ²](https://www.inflearn.com/course/the-java-code-manipulation/dashboard)
- [μ€νλ§ ν΅μ¬ μλ¦¬ κ³ κΈ](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard)
- [Oracle Java Doc. Reflection API](https://docs.oracle.com/javase/tutorial/reflect/index.html)
- [Java Reflection Tutorial, Jenkov](http://tutorials.jenkov.com/java-reflection/index.html)
- [what is reflection and why is it useful](https://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful?rq=1)
