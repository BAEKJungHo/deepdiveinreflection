# Java Annotation

- 어노테이션은 Java SE 5 에서 Generics 와 함께 등장하였다.
- 어노테이션의 사전적 의미는 주석이지만, 자바 언어에서 사용하는 주석(`//, /*, /**`) 과는 다르다.

## 주석의 등장 배경

어노테이션의 사전적 의미인 주석의 등장 배경을 먼저 보자. 

주석이 없던 시절에는, 소스 코드와 문서화가 별도로 진행되었다. 따라서, 소스 코드가 변경되면 그에 알맞은 문서를 찾아서 변경해줘야 했었다.

자고로 개발자는 귀찮은 것을 매우 싫어한다. 

![IMAGES](./images/metamong.jpg)

따라서, 소스 코드만 변경하고 문서를 변경하지 않는 일이 자주 발생하였고, 코드와 문서의 버전 불일치 문제를 해결하고자 탄생하게 되었다.

## 어노테이션의 등장 배경

어노테이션이 등장하기 전에는 프로그램 설정 파일들이 `XML` 형태로 작성되었다.

`컴포넌트 스캔(Component-scan)`을 예로 들어보자.

스프링에서는 XML 에서 컴포넌트 스캔을 사용하였다.

```xml
<context:component-scan base-package="com.spring.study">
    <context:exclude-filter type="annotation" 
        expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```

하지만 스프링 부트에서는 `@Configuration` 과 `@ComponentScan` 어노테이션을 활용하여 XML 이 아닌 자바 소스코드를 활용하여 설정 정보를 관리할 수 있게 되었다.

```java
@ComponentScan(basePackages = "com.spring.study")
@Configuration
public class AppConfig {
}
```

> @Test 어노테이션도 마찬가지로 해당 메서드가 테스트 대상임을 Junit Framework 에게 알린다.

어노테이션의 등장으로 인한 장점은 다음과 같다.

- 어노테이션을 통해 소스 코드와 설정 정보를 같이 관리할 수 있어서 편하다.
- 비지니스 로직을 방해하지 않고, 필요한 정보를 제공할 수 있다.

## 특징

Annotations do not directly affect program semantics, but they do affect the way programs are treated by tools and libraries, which can in turn affect the semantics of the running program. Annotations can be read from source files, class files, or reflectively at run time.

> [Annotations docs](https://docs.oracle.com/javase/8/docs/technotes/guides/language/annotations.html)

Document 에 나와있는 내용을 읽어보자.

> 주석은 프로그램 의미론에 직접적인 영향을 미치지 않지만 프로그램이 도구 및 라이브러리에서 처리되는 방식에 영향을 미치므로 실행 중인 프로그램의 의미론에 영향을 줄 수 있습니다. 주석은 소스 파일, 클래스 파일에서 읽거나 런타임에 반사적으로 읽을 수 있습니다.

여기서 `런타임에 반사적`으로 읽을 수 있다는 의미만 살짝 다뤄보겠다.

## 비지니스 로직을 방해하지 않고, 필요한 정보를 제공할 수 있다.

![IMAGES](./images/annotationwithreflection2.png)

- 비지니스 로직을 방해하지 않고, 필요한 정보를 제공할 수 있다. 
    - 스프링 DI 를 생각하면 된다.
    - 런타임에 반사적으로 읽는다.

우리가 스프링을 사용하게되면 DI(Dependency Injection) 을 자주 사용하게 되는데, 어떻게 런타임에 의존성이 주입이 될까?

```java
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
@Repeatable(Resources.class)
public @interface Resource {
    // 생략
}
```

```java
@Service
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository
}
```

단순히, 어노테이션만 추가했다고 의존성이 주입되는건가? 어노테이션 등장 배경을 살펴보면 그러한 목적을 위해서 탄생 한 것 같진 않다.

여기에는 `Reflection` 이라는 기술이 사용된다.

> Reflection 기술을 이용하여 어노테이션을 효과적으로 활용할 수 있다.

런타임에 UserService 객체가 생성되는 시점(클래스 로더에 의해 메모리에 적재 되는 순간)에 해당 클래스의 필드에 선언 되어있는 어노테이션 정보를 읽어서 해당 필드의 객체를 생성하여 주입해준다.

## 표준 어노테이션

- __@Override__
    - 컴파일러에게 오버라이딩하는 메서드임을 알린다.
- __@Deprecated__
    - 앞으로 사용하지 말 것을 권장하는 대상에게 붙인다.
- __@SuppressWarnings__
    - 컴파일러의 특정 경고 메시지가 나타나지 않게 해준다.
- __@SafeVarargs__
    - 제네릭 타입의 가변인자에 사용한다. (JDK 1.7)
- __@FunctionalInterface__
    - 함수형 인터페이스라는 것을 알린다. (JDK 1.8)
- __@Native__
    - native 메서드에서 참조되는 상수 앞에 붙인다.
- __@Target__
    - 어노테이션이 적용 가능한 대상을 지정하는데 사용한다.
- __@Documented__
    - 어노테이션 정보가 javadoc 으로 작성된 문서에 포함되게 한다.
- __@Inherited__
    - 어노테이션이 하위 클래스에게 상속 되도록 한다.
- __@Retention__
    - 어노테이션이 유지되는 범위를 지정하는데 사용한다.

> @Target, @Documented, @Inherited, @Retention, @Repeatable 은 메타 어노테이션이라고도 부른다. 메타 어노테이션을 활용하여 어노테이션을 커스터마이징 할 수 있다.
>
> 메타데이터란 어플리케이션이 처리해야 할 데이터가 아니라, 컴파일 타임과 런타임에서 코드를 어떻게 컴파일하고 처리할 것인지 알려주는 정보이다.

### @Override

슈퍼 클래스의 기능을 오버라이딩할 때 `@Override` 를 작성하면 컴파일 타임에 오탈자를 확인할 수 있다.

```java
@Slf4j
public class SuperClass {
    
    public void run() {
        log.info("Run by SuperClass");
    }
}

@Slf4j
public class SubClass extends SuperClass {

    // java: method does not override or implement a method from a supertype
    @Override
    public void run1() {
        log.info("Run by SubClass");
    }

    /**
     * 오탈자를 입력했지만 @Override 를 적용하지 않아서 
     * 하위 클래스에서 만든 새로운 메서드라고 인식한다.
     */
    public void run2() {
    }
}
```

### @Deprecated

@Dprecated 는 앞으로 사용하지 말 것을 권장하는 필드나 메서드에 붙인다.

Date 클래스를 예로 들어보자. Date 에 들어있는 대부분의 메서드들은 `@Deprecated` 되었다.
주석을 읽어보면 Date 대신 Calendar 를 권장하는 것 같다.


```java
/**
    * Returns the day of the month represented by this {@code Date} object.
    * The value returned is between {@code 1} and {@code 31}
    * representing the day of the month that contains or begins with the
    * instant in time represented by this {@code Date} object, as
    * interpreted in the local time zone.
    *
    * @return  the day of the month represented by this date.
    * @see     java.util.Calendar
    * @deprecated As of JDK version 1.1,
    * replaced by {@code Calendar.get(Calendar.DAY_OF_MONTH)}.
    */
@Deprecated
public int getDate() {
    return normalize().getDayOfMonth();
}
```

하지만 @Deprecated 가 붙어있다고 해당 메서드를 사용 할 수 없다는 것은 아니다.

그럼에도 불구하고 @Deprecated 를 사용하는 이유는 무엇일까?

자바는 `하위 호환성`을 중요하게 여기는데, 과거에 getDate() 를 사용하여 작성된 프로그램이 상위 JDK 버전에서 동작이 안된다면 에러가 발생할 것이다. 따라서, 상위 버전에서 하위 버전에서 사용했던 메서드 등이 Deprecated 되었더라도, 상위 버전에서 정상적으로 동작할 수 있도록 해주며, 상위 버전에서는 더 이상 사용하지 말라는 의미를 담고있다고 보면된다.

## @ComponentScan 을 통한 메타 어노테이션 알아보기

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    // 생략
}
```

### @Retention

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Retention {
    /**
     * Returns the retention policy.
     * @return the retention policy
     */
    RetentionPolicy value();
}
```

- __@Retention__
    - 어노테이션이 어느 시점 까지 유지되는지를 정한다.
    - @Target 이 `ElementType.ANNOTATION_TYPE` 으로 되어있는 것으로 봐서, 어노테이션에만 지정할 수 있는 어노테이션이다.

잠시 `RetentionPolicy` 를 까보자.

```java
/**
 * Annotation retention policy.  The constants of this enumerated type
 * describe the various policies for retaining annotations.  They are used
 * in conjunction with the {@link Retention} meta-annotation type to specify
 * how long annotations are to be retained.
 *
 * @author  Joshua Bloch
 * @since 1.5
 */
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     */
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     */
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
```

위 주석에서 핵심 문구는 `how long annotations are to be retained.` 이다.

- __정리__
    - @Retention 어노테이션은, 어노테이션에만 적용할 수 있다.
    - @Retention 어노테이션에 RetentionPolicy 를 지정할 수 있는데, RetentionPolicy 는 어노테이션을 유지할 기간을 의미한다.

RetentionPolicy 에서 `RUNTIME` 주석 부분을 보자. RUNTIME 주석의 핵심 부분은 `retained by the VM at run time, so they may be read reflectively.` 이다.

> 런타임에 VM에 의해 유지되므로 반사적으로 읽을 수 있습니다.

그러면 다시 위로 올라가서, [비지니스 로직을 방해하지 않고, 필요한 정보를 제공할 수 있다.](https://github.com/BAEKJungHo/deepdiveinreflection/blob/main/contents/Annotation.md#%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4-%EB%A1%9C%EC%A7%81%EC%9D%84-%EB%B0%A9%ED%95%B4%ED%95%98%EC%A7%80-%EC%95%8A%EA%B3%A0-%ED%95%84%EC%9A%94%ED%95%9C-%EC%A0%95%EB%B3%B4%EB%A5%BC-%EC%A0%9C%EA%B3%B5%ED%95%A0-%EC%88%98-%EC%9E%88%EB%8B%A4) 에 있는 @Resource 어노테이션을 보자. RetentionPolicy 가 RUNTIME 으로 되어있는 것을 볼 수 있다. 따라서, @Resource 어노테이션을 필드에 사용하면 Reflection 을 통한 DI 가 가능한 것이다.

#### @Getter, @Setter, @Override 의 RetentionPolicy ?

@Getter, @Setter, @Override 의 RetentionPolicy 가 무엇으로 되어있을지 생각해 보자.

정답은 `SROUCE`로 되어있다. 

테스트를 위해 User 라는 클래스를 생성하고 컴파일 해보자.

```
@Getter @Setter
public class User {

    private Long id;
}
```

컴파일 결과는 아래와 같다.

```java
public class User {
    private Long id;

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
```

RetentionPolicy 가 `SOURCE` 로 되어있어서 컴파일될 때 어노테이션은 사라지고, 어노테이션 정보를 가지고 실제 코드를 생성해준다.

### @Target

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
    /**
     * Returns an array of the kinds of elements an annotation type
     * can be applied to.
     * @return an array of the kinds of elements an annotation type
     * can be applied to
     */
    ElementType[] value();
}
```

- __@Target__
    - 어노테이션을 적용할 대상을 지정한다.
    - ElementType enum 에 지정되어있는 타입 중 하나 이상을 선택할 수 있다.

```java
public enum ElementType {
    /** Class, interface (including annotation type), or enum declaration */
    TYPE,

    /** Field declaration (includes enum constants) */
    FIELD,

    /** Method declaration */
    METHOD,

    /** Formal parameter declaration */
    PARAMETER,

    /** Constructor declaration */
    CONSTRUCTOR,

    /** Local variable declaration */
    LOCAL_VARIABLE,

    /** Annotation type declaration */
    ANNOTATION_TYPE,

    /** Package declaration */
    PACKAGE,

    /**
     * Type parameter declaration
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * Use of a type
     * @since 1.8
     */
    TYPE_USE,

    /**
     * Module declaration.
     * @since 9
     */
    MODULE
}
```

### @Documented

@Documented 의 주석 일부를 보면 다음과 같다.

```java
/**
 * Concretely, if an annotation type is annotated with {@code
 * Documented}, by default a tool like javadoc will display
 * annotations of that type in its output while annotations of
 * annotation types without {@code Documented} will not be displayed.
 */
```

- __@Documented__
    - @Documented 를 사용하면 javadoc tool 을 사용하여 문서를 생성하면, 문서에 어노테이션 정보까지 같이 보여진다.

### @Repeatable

```java
/**
 * The annotation type {@code java.lang.annotation.Repeatable} is
 * used to indicate that the annotation type whose declaration it
 * (meta-)annotates is <em>repeatable</em>. The value of
 * {@code @Repeatable} indicates the <em>containing annotation
 * type</em> for the repeatable annotation type.
 *
 * @since 1.8
 * @jls 9.6.3 Repeatable Annotation Types
 * @jls 9.7.5 Multiple Annotations of the Same Type
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Repeatable {
    /**
     * Indicates the <em>containing annotation type</em> for the
     * repeatable annotation type.
     * @return the containing annotation type
     */
    Class<? extends Annotation> value();
}
```

- __@Repeatable__
    - 반복해서 붙일 수 있는 어노테이션을 정의할 때 사용
    - 반복해서 표현할 어노테이션을 묶을 `컨테이너 어노테이션`도 함께 정의해서 사용해야 함
        - @ComponentScans 가 컨테이너 어노테이션에 해당된다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScans {

	ComponentScan[] value();

}
```

따라서, @ComponentScan 은 `@Repeatable` 어노테이션 덕분에 아래와 같은 형태로도 사용이 가능하다.

```java
@ComponentScan(basePackages = "hello.test")
@ComponentScan(basePackages = "hello.src")
public class AppConfig {
}
```


## Referneces

- https://www.nextree.co.kr/p5864/
- https://ahnyezi.github.io/java/javastudy-12-annotation/
