package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// Entity의 객체 일관성 보장 -> 이를 위한 빌드패턴 사용
// 가급적 Setter 사용안할 예정 -> @Getter면 충분
@Getter

// 기본 생성자 자동 주입
// public Posts(){}
@NoArgsConstructor
// # Entity 클래스
// - 실제 DB와 맵핑될 클래스
// - 즉, 테이블과 링크될 클래스
@Entity
public class Posts {

    // # Id = > PK(primary key) 필드
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;


    // 굳이 명시하지 않아도 Column 이 됨
    // but 위에서처럼 명시하면 최대크기 등 추가적인 옵션의 변경이 가능함
    private String author;

    // # 빌더 패턴
    // - 디자인 패턴의 일종
    // - 생성자에서 인자가 많을 떄 고려해볼 수 이쓴 패턴
    // - 정보들을 자바 빈즈패턴처럼 받지만, 데이터일관성을 위해 정보들을 다 받은 후에 객체를 생성함
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}

/*
    [도메인 모델 / Entity 관련 내용]
    # Domain model
    - 비즈니스 로직 담당 / 비즈니스적인 의미를 가지는 object
    - id 여부에 따라 두가지로 구분할 수 있음 (Entity vs VO)
    - 서비스(Service)의 역할 -> 엔티티에 단순히 필요한 요청을 위임하는 역할
        - 이렇게 설계된 것이 Domain Model Pattern
    - Jpa를 쓰면 주로 Domain Model Patternㅇ르 / SQL을 직접 다루면 Transaction Script 패턴을 많이 사용
    - 상황에 따라 유지보수성이 높은 방식을 선택해야 함

    @ Domain model 추가 내용
    - 궁극적 목표 : 유지보수가 쉬운 시스템
    - 가능한 POJO로 유지
    - 적어도 Domain Model 개념 해치지 않도록 설게된 annotation에 한해서는
      도메인 모델에 바로 붙이는 것이 더 자연스럽고 실용적 (엄격한 POJO까지는 아니여도 된다는 의미)

    - Domain Model은 내 API 요청 / 응답으로부터 독립적이어야 함
    - Domain Model(의 설계)는 data source로부터 독립적이어야 한다 (무슨말?)
        - 외부 데이터를 어디서 가져오는지(DB, external api, ...)
        - 어떤 라이브러리를 써서 가져오는지 (MyBatis, JPA, ...)
        - 어떤 형태로 가져오는지(json, xml, ...)
        - 어떤 필드를 가져오는지

    @ Entity
    - id가 있어 각각의 개체를 고유하게 식별할 수 있는 경우
    - 엄밀히 말하면불변은 X -> 시간이 지나며 상태는 변할 수 있음
    - but 그래도 앱단에서는 불변객체로 처리하는 것이 좋음 (함수형)
    - ex) Member

    - 실제 DataBase의 테이블과 1:1로 Mapping 되는 클래스
    - DB의 테이블 내 존재하는 컬럼만을 속성(필드)로 가져야 한다.
    - Entity Class는 상속을 받거나 구현체여서는 안되며,
      테이블 내에 존재하지 않는 칼럼을 가져서도 안된다


    - 최대한 외부에서 Entity Class에 접근하지 못하도록 제한해야 함
    - 해당 Class(Entity 클래스) 안에서 접근을 허용할 데이터들을 제한하고,
      logic method를 구현해야하며
      Domain Logic 만 가지며,
      Spring 3 Tier 중 Persistence Tier에서 사용한다

    - 구현 method는 주로 Sercie Layer에서 사용한다
    - Entity를 Persistence Tier에서 받아와 DTO로 변환하여
      Persentation Tier에 전달하는 것이 Business Tier, Service 단에서의 역할

    - DB에 쓰일 필드와 여러 엔티티간의 연관관계를 정의함
    - 각각 한개의 행이 "엔티티 객체"를 의미

    @ Entity와 DTO 분리 이유
    - DB Layer와 View Layer 사이의 역할을 분리하기 위해서
    - DB Layer = Persistence Tier
    - View Layer = Presentation Tier

    - Entity는 실제 테이블과 매핑됨 => 변경되면 다른 Class에 영향을 줌
        - VO도 이런 점에서 불변 ?
    - DTO는 View와 통신하며 자주 변경됨 => 떄문에 분리해주어야 함

    - DTO는 Domain Model 객체(Entity)를 그대로 두고 복사하여
      다양한 Presentation Logic을 추가한 정도로만 사용함

    - Domain Model 객체(Entity)는 Persistent 만을 위해 사용해야 함


    @ Entity와 Setter 관련
    - Entity 작성시 -> Setter 사용 지양해야함
    - 객체일관성 깨질 수 있기 때문
    - 객체일관성 유지되어야 유지 보수성이 올라감

    - 대신 객체의 생성자들에 값을 넣어줌으로써 Setter 사용 줄일 수 있음
    
    @ Entity와 빌더 패턴 관련
    - 빌더패턴 사용하면 Entity 객체 생성시 목적, 용도에 따라 객체 생성 제한할 수 있음
    - => 명시적, 안전한 Core Data 접근 가능

    @ VO (value object)
    - id가 없음
    - 필드 값 상태가 같다면 같은 객체로 처리해도 되는 경우 (이래서 'value')
        - 반대 개념('reference' object)
        - 참조가 아니라 값으로 동등 비교하는게 더 자연스러운 대상들
        - 때문에 equals,hashCode 구현 필수

    - ex) Money
    - 특별히 비즈니스적 의미를 가지지 않아도
      데이터 뭉치를 하나로 묶어주는 클래스도 VO로 봄

    - VO의 핵심 역할은 equals()와 hashcode()를 오버라이딩 하는 것
    - 내부에 선언된 속성(Field)의 모든 값들이 VO 객체마다 값이 같아야, 똑같은 객체라고 판별함
        - 즉, A 객체(홍길동,12,서자) B 객체(홍길동,12,장남) 이러면 다른 객체로 판단함
        
    - VO는 테이블 내의 속성 외에 추가적인 속성을 가질 수 있음(Entity와 차이점)
    - 여러 테이블의 공통 속성을 모아서 BaseVO 클래스를 만들어 상속받아 사용할 수도 있음


    @ DTO (Data Transfer Object)
    - 주로 비동기 처리시 사용됨
    - 계층간 데이터 교환을 위한 객체
    - DB의 데이터를 Service나 Controller 등으로 보낼 떄 사용하는 객체
    - DB의 데이터가 Presentation Logic Tier로 넘어올 떄 DTO로 변환되어 오고가는 것
    - 로직 X (로직 없음) => 순수한 데이터 객체
    - getter / setter 메서드만을 가짐
    - Controller Layer(ViewLayer)에서 Response DTO 형태로 클라이언트에 전달함


    @ VO와 DTO의 차이점
    - 데이터를 전달하는 객체로써는 동일한 개념
    - vo는 조금 더 특정한 비즈니스 로직의 결과 값을 담는 객체
    - VO를 생성하여 동일한 객체 비교까지 필요한 Logic 내에서 주로 사용함

    - DTO는 Layer간 단순 통신 용도로 오고가는 Data 전달 객체
    - 조금 더 포괄적, 제한없이 사용할 수 있는 객체
    - 민감 X이거나 동일 객체를 비교하는 로직에 사용되지 않을 때는 단순하게 DTO를 사용하면됨


   ** 다른 이야기 **
   @ Repository
   - Entity에 의해 생성된 DB에 접근하는 메서드들을 사용하기 위한 "인터페이스"
   - Entity를 선언해서 DB구조를 만들었다면,
     여기(Repository)에서는 CRUD(값의 추가, 읽기, 수정, 삭제 등)

   - 만들어낸 Repository interface에서 JpaRepository를 상속받으면  됨
       - JpaRepository<대상이 되는 Entity, 해당 Entity의 Pk 타입>
       - 그럼 기본적으로 제공되는 메소드 사용 가능함


   @ DAO (Data Access Object)
   - 데이터베이스의 data에 접근하기 위한 객체
   - DataBase에 접근하기 위한 로직 <-> 비즈니스로직을 분리하기위해 사용용
   @ equals

   @ hashcode


    */

    /*
    [빌더 패턴 관련 내용]
    # 점중적 생성자 패턴 = 점층적 생성자 패턴
    - 있어야할 멤버변수를 위해 -> 생성자에 매개변수를 넣음
    - 그리고, 선택적으로 인자를 받기 위해 추가적인 생성자를 만듦

    @ 단점
    - 인자들이 많을 수록 생성자가 많아짐

    ex)
    public class Test(){
        private String name;
        private int math;
        private int eng;
    }

    public Test(String name, int math){
        this.name = name
        this.math = math
    }

    public Test(String name, int math, int eng){
        this.name = name
        this.math = math
        this.eng = eng
    }

    - 값을 바꾸기 위해서는 객체를 새로 생성해주어야 함함




    # 자바 빈즈 패턴
    - 가독성이 해결됨
    - 대신 코드가 늘어나고 "객체일관성"이 꺠짐

    ex)
    public class Test(){
        private String name;
        private int math;
        private int eng;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setMath(int math){
        this.math = math;
    }

    public void setEng(int eng){
        this.eng = eng;
    }

    @ 객체일관성
    - 한번 객체 생성할 떄, 그 객체가 변할 여지가 생기는 것
    - 먼저 객체를 생성하고, 그 뒤에 값이 떡칠됨

    ex)
    Test test = new Test();     // 선 객체 생성
    test.setName("AA");
    test.setMath(52);
    test.setEng(63);
    ...
    - 이런식으로 값을 계속 입혀줘야 함
    - 또한 set에 따라 test라는 객체의 멤버변수들의 값이 달라짐



    # 빌더 패턴
    - 둘의 단점을 보완해서 나타난 패턴
    - 정보들은 자바빈즈패턴처럼 받고, 데이터 일관성을 위해 정보들을 다 받은 후 객체를 생성함

    @ 장점
    - 불필요한 생성자의 제거
    - 데이터의 순서에 상관없이 객체 생성 가능
    - 명시적 선언 => 덕분에 이해하기 쉬움
    - setter 없음 => 변경 불가한 객체 생성 가능
    - 한번에 객체를 생성함 => 즉, 객체일관성 보장
    - build()함수가 null인지 체크를 해줌 => 검증 가능
        - if set을 하지 않은 객체에서 get 하면 => nullPointerException 발생

    @ 만드는 법
    - A 클래스 내부에 "빌드 클래스" 생성
    - 각 멤버변수별 메서드르 작성 -> 각 메소드는 변수에 값을 set하고 빌더객체를 리턴
    - build() 메서드는 멤버변수들의 null을 체크 -> 지금까지 set 된 builder 바탕으로 A클래스의 생성자 호출, 인스턴스 리턴

    ex)
    public class Test(){
        private String name;    // 필수적으로 받아야하는 정보
        private int math;       // 선택적인 정보
        private int eng;        // 선택적인 정보


        // 빈 생성자
        public Test(){

        }

        public static class Builder{

            private String name;
            private int math;
            private int eng;

            // 필수변수만 Builder 생성자에 넣기
           public Builder(String name){
               this.name = name;
           }

           // 멤버변수별 메소드 - 빌더 클래스의 필드값을 set하고 빌더객체를 리턴
           public Builder setMath(int math){
               this.math = math;
               return this;
           }

           // return의 자료형 "Builder" -> this = Builder => 즉, Builder 반환
           public Builder setEng(int eng){
               this.eng = eng;
               return this;
           }

           // 빌드 메소드

           public Test build(){
               Test test = new Test();
               test.name = name;
               test.math = math;
               test.eng = eng;

               return test;
           }
        }
    }

    - 이렇게 만들면 메서드 체이닝 기법 사용 가능
    - build() 메서드 사용 이후 Test 클래스의 멤버변수를 변경할 수 있는 방법은
      리플렉션 기법(동적시점 변경)빼곤 존재 X

      ex)

      Test test = new Test
          .Builder("AA")        // 필수값만 입력(name)
          .setMath(25)          //
          .setEng(74)
          .build();             // build()가 객체를 생성해서 return해줌

    **/

