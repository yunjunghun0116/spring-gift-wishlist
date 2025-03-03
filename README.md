# [STEP-2] 위시 리스트

### 과제 진행 요구 사항

- 미션은 [위시 리스트](https://github.com/kakao-tech-campus-2nd-step2/spring-gift-wishlist) 저장소를 포크하고 클론하는 것으로 시작한다.
- [온라인 코드 리뷰 요청 1단계 문서](https://github.com/next-step/nextstep-docs/blob/master/codereview/review-step1.md)를 참고하여 실습 환경을
  구축한다.
- 기능을 구현하기 전 README.md에 구현할 기능 목록을 정리해 추가한다.
- Git의 커밋 단위는 앞 단계에서 README.md에 정리한 기능 목록 단위로
  추가한다. [AngularJS Git Commit Message Convention](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)을 참고해 커밋
  메시지를 작성한다.

### 프로그래밍 요구 사항

- 자바 코드 컨벤션을 지키면서 프로그래밍 한다. (들여쓰기는 '4 spaces' 로 한다)
- indent (들여쓰기) depth 를 3이 넘지 않도록 구현한다.
- 3항 연산자를 사용하지 않는다.
- 함수는 한가지 일만 하도록 최대한 작게 만든다.
- 함수의 길이가 15 라인을 넘어가지 않도록 구현한다.
- JUnit 5 와 AssertJ 를 이용하여 정리한 기능 목록이 정상적으로 작동하는지 테스트 코드로 확인한다.
- else 예약어를 사용하지 않는다.
- 도메인 로직에 단위 테스트를 구현해야 한다.(핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 분리해 구현한다.)

### 기능 요구 사항 (2주차)

#### 1단계

- [X] 상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다. 잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.
- [X] 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
- [X] 특수문자는 (),[],+,-,&,/,_ 만 사용될 수 있다.
- [X] "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.

#### 2단계

- [X] 사용자가 회원가입, 로그인, 추후 회원별 기능을 이용할 수 있도록 구현한다.
- [X] 회원은 이메일과 비밀번호를 입력하여 가입한다.
- [X] 토큰을 받으려면 이메일과 비밀번호를 보내야 하며, 가입한 이메일과 비밀번호가 일치하면 토큰이 발급된다.
- [X] 토큰을 생성하는 방법에는 여러가지가 있다. 방법 중 하나를 선택한다. (Basic Auth, Json Web Token, 응답코드)
- [X] 아래 예시와 같이 HTTP 메시지를 주고 받도록 구현한다.
- [X] 다양한 경우에 대한 테스트 코드를 작성한다.

![이미지](https://github.com/yunjunghun0116/codetree-TILs/assets/76200940/13f047c0-d6b6-491e-827d-21dc46dd99bb)

#### 3단계

- [X] 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트 기능을 구현한다.
- [X] 위시 리스트에 등록된 상품 목록을 조회할 수 있다.
- [X] 위시 리스트에 상품을 추가할 수 있다.
- [X] 위시 리스트의 상품 갯수를 수정할 수 있다.
- [X] 이미 위시리스트에 같은 상품이 있을경우 갯수만 추가한다.
- [X] 위시 리스트에 담긴 상품을 삭제할 수 있다.
- [X] 상품의 갯수를 0으로 수정할경우 해당 상품은 삭제된다.
- [X] 사용자 정보는 요청 Header 의 Authorization 필드를 사용한다.
  + **Authorization** : **<유형>** **<자격증명>**

![시나리오1](https://github.com/kakao-tech-campus-2nd-step2/spring-gift-wishlist/assets/76200940/7195b36e-f78d-4e33-b3b7-419a11b5b77e)
![시나리오2](https://github.com/kakao-tech-campus-2nd-step2/spring-gift-wishlist/assets/76200940/561cc024-1640-4129-ae62-d7998982acdd)

### 힌트

### 나만의 HTTP RULE

| HTTP Method | 사용상황                           | 반환(상태코드) |
|-------------|--------------------------------|----------|
| GET         | 리소스의 조회                        | 200      | 
| POST        | 새로운 리소스 생성                     | 201      |
| PUT         | 리소스의 전체 업데이트 또는 ID를 통한 리소스 생성  | 204      |
| PATCH       | 리소스의 일부분(일부 필드) 업데이트           | 204      |
| DELETE      | 리소스의 삭제                        | 204      |

### 나만의 계층 RULE

| 계층         | 역할                                                          |
|------------|-------------------------------------------------------------|
| Controller | HTTP 요청을 받아 적절한 Service 호출, 입력 검증, 유효성 검사, HTTP 응답 생성 및 반환  |
| Service    | 비즈니스 로직 수행, DTO 와 엔티티 변환, 다수 Repository 를 통한 하나의 트랜잭션 처리 작업 |
| Model      | Entity, DTO 가 속하며 데이터구조, 데이터베이스와의 연동되는 객체                   |
| Repository | DB 관련 CRUD 작업, DB 의 결과를 Entity 로 변환하는 작업                    |

### 나만의 개행 RULE

- 지역변수는 사이에 개행을 두지 않는다. 하지만 첫 지역변수 전줄, 마지막 지역변수 다음줄에 개행을 추가한다.
- 생성자 전후에 개행을 추가한다.
- 추상체, 구현체 모두 메서드 전후에 개행을 추가한다. 단 마지막 메서드 후에는 추가하지 않는다.
- 클래스의 마지막 줄에는 개행을 추가한다.
