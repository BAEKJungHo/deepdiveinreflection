# POJO 

- [Martinfowler](https://martinfowler.com/bliki/POJO.html). POJO 는 애플리케이션 핵심 코드를 작성할 때, EJB 기술을 사용하지 말고 일반 자바 객체로 작성하자라는 것이 전부이다. 
- [토비의 스프링](#). POJO 는 객체지향적 원리에 충실하고, 특정 규약과 환경에 종속되지 않게 재활용될 수 있는 방식으로 설계된 객체이다.
  - 특정 기술과 규약, 환경에서 자유롭기 때문에 객체지향적인 설계를 자유롭게 적용할 수 있다. 그래서 자동화된 테스트에 유리하며, 유지보수성과 확장성이 좋아진다.
- [Rod Johnson](#). OO design is more important than any particular implementation technology (such as J2EE, or even Java). And now important than even Spring or Hibernate. Good Programming practices and sound OO design underpin good J2EE applications. Bad Java code is bad J2EE code.

> POJO 방식으로 개발을 했다면 `반드시 테스트를 작성`해야 하며, 그래야 POJO 를 POJO 답게 쓰게하고 그 가치를 누릴 수 있다. 
> 
> 토비의 스프링 저자. 이일민

## OO 란 ?

OO(Object Oriented) 란 다형성을 이용하여 전체 시스템의 모든 소스 코드 의존성에 대한 절대적인 제어 권한을 획득할 수 있는 능력이다. OO 를 사용하면 아키텍트는 플러그인 아키텍처를 구성할 수 있고, 이를 통해 고수준의 정책을 포함하는 모듈은 저수준의 세부사항을 포함하는 모듈에 대해 독립성을 보장할 수 있다. 저수준의 세부사항은 중요도가 낮은 플러그인 모듈로 만들 수 있고, 고수준의 정책을 포함하는 모듈과는 독립적으로 개발하고 배포할 수 있다.

## POJO 란 ? 

- 특정 프레임워크에 대한 참조가 없는 간단한 유형

```java
public class EmployeePojo {

    public String firstName;
    public String lastName;
    private LocalDate startDate;

    public EmployeePojo(String firstName, String lastName, LocalDate startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
    }

    public String name() {
        return this.firstName + " " + this.lastName;
    }

    public LocalDate getStart() {
        return this.startDate;
    }
}
```

이 클래스는 프레임워크에 연결되지 않으므로 모든 Java 프로그램에서 사용할 수 있다.

### 스프링에 종속적인 어노테이션 붙어있는 클래스는 POJO 일까?

- 어노테이션이 어떤 역할을 하는지 알고 있다면 쉽게 답할 것이다.
- 어노테이션은 단지 DI, AOP 와 같은 동작 등을 프로덕션 코드에 직접적으로 노출시키지 않고 기능을 편리하게 사용하기 위함이다.
  - 물론, 어노테이션이 동작하기 위해서 리플렉션 같은 기술을 사용해서 뒷단에서 추가적인 코드 구현은 해야 한다.

__따라서, @Service 같은 어노테이션이 붙어있는 클래스가 특정 EJB 같은 특정 기술에 종속적이지 않고, 어노테이션을 제거했을때 POJO 객체라면, POJO 객체로 취급한다.__

예를 들어, 스프링이 아닌 다른 프레임워크를 사용한다고 했을때, 스프링에 종속적인 어노테이션을 먼저 걷어낼 것이다. 그리고 새로운 프레임워크에서 DI, AOP 와 같은 기존에 스프링에서 제공하는 어노테이션과 동일한 기능을 하는 어노테이션이 있다면 그것을 추가하면 될 뿐이며, 만약에 제공하지 않더라도, DI, AOP 를 직접 구현하면된다.

## POJO 객체로 작성해야하는 이유

- POJO 로 개발을 하면 유연한 객체지향 설계 원칙을 적용할 수 있고, 유지보수성과 확장성을 갖출 수 있다.
- 테스트 작성이 쉬워진다.

## Jakarata EE Evolution

![JakartaEEEvolution2](https://user-images.githubusercontent.com/47518272/156877138-a0101c8f-8e68-43c3-8d5b-f0ac0e763e27.png)

- Jakarata EE 는 대규모의 네트워크, 다 계층 애플리케이션을 더 쉽고 안전하게 구축할 수 있게 해준다.
- 웹을 통한 UI, 선언적 트랜잭션 처리 등 전체 애플리케이션 스택을 제공하고있다.

수많은 사용자의 요청과 데이터를 처리하는 엔터프라이즈 애플리케이션을 개발하는것은 쉽지 않다. 복잡한 비지니스 요구사항을 구현하면서 기술적인 문제도 함게 해결 해야 하기 때문이다.
그래서 자바는 엔터프라이즈 자바 플랫폼을 만들면서 `EJB(Enterprise Java Beans)` 를 출시하였다.

EJB 는 애플리케이션 개발자들이 저수준의 기술에 관심을 가질 필요 없이, `애플리케이션을 쉽게 개발할 수 있다는 비전`을 내세우면서 시장에 출시되었다.
EJB 의 중심에는 분산 컴퓨팅기술이 있다. 이를 통해 여러 서버간에 분산되어있는 모듈들을 원격 호출할 수 있으며 동시에 트랜잭션 관리 등을 선언적으로 할 수 있다.
또한 모듈을 독립적으로 배포 및 연동하여 동작하게 할 수 있다.

## 침투적인 기술과 비침투적인 기술

- EJB 로 작성된 코드를 보면, EJB 환경에서 동작하기 위해서 특정 인터페이스를 구현하고, 특정 클래스를 상속하고, 서버에 종속적인 서비스를 통해서만 접근하고 사용이 가능하다.
-  __`침투적인 기술`은 어떤 기술을 적용했을 때, 그 기술과 관련된 코드나 규약 등이 개발자가 작성하는 코드에 등장한다.__
- __`비침투적인 기술`은 해당 기술의 적용 사실이 코드에 드러나지 않는다.__
  - 즉, 해당 기술과 관련된 코드나 규약이 애플리케이션 코드에 여기저기에 등장하지 않으며, 코드의 설계와 구현의 방식을 제한하지 않는다.

## 스프링의 철학

- __애플리케이션 코드가 특정 기술에 종속적이지 않도록(`비침투적`) 하는 것이 스프링의 철학이며, POJO 를 사용해 엔터프라이즈 애플리케이션을 쉽고 효과적으로 개발할 수 있도록 지원해주는 데 있다.__
- __loosely coupled__
  - Dependency 가 tightly coupled 할 수록 해당 코드를 변경하기도 어렵고, 변경의 이유도 많아지며, 테스트 하기도 어려워진다.

> 스프링이 단순한 프레임워크가 아닌 플랫폼으로 발전했다는 사실과 그럼에도 `초기 스프링의 철학은 여전히 유효하고 더욱 강조`돼야 하며 `스프링 자체 보다 중요`하다. 
> 
> KSUS(한국 스프링 사용자 모임) 3대 큰일꾼 박성철

## 스프링이 POJO 방식의 개발을 돕기 위해 제공하는 기술들

- __스프링의 3대 요소(Spring Triangle)__
  - IoC/DI
  - AOP
  - PSA

## References

- [토비의 스프링 3](http://www.yes24.com/Product/Goods/4020006)
- [당신의 코드는 POJO하신가요?](https://www.youtube.com/watch?v=5NcqgXgmmjg)
