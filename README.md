
<h1 align="center">
  <br>
<img src="https://github.com/user-attachments/assets/c08c03b0-7205-48fa-bf87-09974744c245" alt="bokkie logo" width="200">
  <p>
  Fortunetoss
  <p>
</h1>

<hr/>
<h4 align="center">  Team Bokkie </h4>

<h4 align="center"> 자신에 대한 퀴즈를 내기 위한 공유 웹 사이트 </h4>

<hr>

##  팀 Bokkie 🐰
- **BE**: 전형근
- **PM**: 김미연
- **FE**: 김혜원, 이윤아
- **DE**: 김채원, 신지원


Fortunetoss 주소( 배포 중단 )   ~~[fortuntoss 홈페이지](https://fortunetoss.vercel.app)~~  

---

백엔드 프로젝트 목표 : 

엔티티 사용, querydsl 사용

연관관계를 고려하고, 도메인 객체 지향적으로 사용

기본적인 테스트 코드 작성






## 프로젝트 이미지
<hr>


퀴즈 출제자 (복 나누미)

<img width="787" alt="스크린샷 2025-07-02 오전 10 53 13" src="https://github.com/user-attachments/assets/eeba0802-2f83-4961-b1d4-f8c4e141f671" />
<br>
로그인 및 회원가입 화면

<br>
<br>
<br>
<img width="228" alt="스크린샷 2025-07-02 오전 10 53 32" src="https://github.com/user-attachments/assets/131d57e7-30ea-41ca-afc5-d4811d53b0ad" />
<br>
홈 화면

<br>
<br>
<br>
<img width="984" alt="스크린샷 2025-07-02 오전 10 53 54" src="https://github.com/user-attachments/assets/7196273e-c5a3-45e6-8f20-ab472e1bc59b" />
<br>
출제할 문제 작성 , 덕담 작성 및 공유

<br>
<br>
<br>
<img width="684" alt="스크린샷 2025-07-02 오전 10 54 07" src="https://github.com/user-attachments/assets/ee2b2cf7-c1d5-4905-908a-fb5f92549355" />
 
자신이 낸 문제에 대한 정답자,오답자 및 통계 확인

<br>
<br>
<img width="332" alt="스크린샷 2025-07-02 오전 10 54 19" src="https://github.com/user-attachments/assets/9702ccd1-664c-4199-a701-71e69ce6986e" />
<br>
로그아웃 및 회원탈퇴
<br>
<br>
<br>
<br>
퀴즈 푸는자(복 받으미)  

<img width="873" alt="스크린샷 2025-07-02 오전 10 55 29" src="https://github.com/user-attachments/assets/e4e42114-ecaa-4fd9-a6b2-289477df5764" />
<br>
공유받은 사람의 신원 확인 (로그인 불필요)
<br>
<br>
<br>
<img width="1196" alt="스크린샷 2025-07-02 오전 10 55 56" src="https://github.com/user-attachments/assets/fa32e888-c68a-4c68-80a8-eae5dbe8804b" />
<br>
공유받은 사람의 문제 풀이 및 덕담 확인 가능
<br>

<br>
<br>
<img width="507" alt="스크린샷 2025-07-02 오전 10 56 09" src="https://github.com/user-attachments/assets/ca96b075-454a-4b26-a657-8f78c1c69871" />

풀은 문제 공유 or 나도 새로운 문제 내기 (로그인 필요)


## 📷 ERD


<img width="300" height="300" alt="image" src="https://github.com/user-attachments/assets/44ce5cb0-552b-4c5c-b253-51c571f37d6e" />

<hr>
프로젝트 기간

2024/12/20 ~ 2025/1/23 (5주)


팀 Bokkie BE

| <img src="https://avatars.githubusercontent.com/u/152036928?v=4" width="150"> |
|:--------------------------------------------------------:|
|        [HyungGeun](https://github.com/HyungGeun94)         |
|                            BE                            |

기술 스택
## Spring
- **Java**: 17
- **Spring Boot**: 3.4.0
- **Spring Security**: 6.4.1
- **OAuth2 Client**: 6.4.1
- **Slf4j**: 2.24.1
- **JPA**: 3.4.0
- **JWT**: 0.12.3
- **QueryDsl**: 5.0.0

---

## Database
- **MySQL**: 8.0
- **H2**: 2.3.232

---

## Web
- **Docker**: 27.3.1
- **Docker Compose**: v2.30.3-desktop.1


<hr>
주요 기능

복주머니 생성
```java


```
공유
```java


```
풀이
```java


```
통계 확인
```java


```

kakao oauth2 소셜 로그인
```java 


```
access, refresh 토큰 사용
```java



```

<h2 >기능 일부 상세 코드</h2> 

```java
    @BeforeEach
    public void insertMember() throws Exception {

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String jsonRequest = objectMapper.writeValueAsString(joinDTO);

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));
    }

```

```java
 @Test
    @DisplayName("일반로그인_토큰발급성공")
    public void 로그인_성공() throws Exception {

        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("goorm94@naver.com");
        joinDTO.setPassword("password1234&");

        String loginRequest = objectMapper.writeValueAsString(joinDTO);


        // When
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(cookie().exists("refresh")) // 쿠키에 토큰 포함 여부 확인
                .andExpect(header().exists("access")) // 헤더에 포함 여부 확인
                .andExpect(status().isOk())
                .andReturn();

        // Then
        // 검증: 헤더 또는 쿠키에 포함된 토큰 값 확인
        String accessToken = result.getResponse().getHeader("access");
        String refreshToken = result.getResponse().getCookie("refresh").getValue();

        assertNotNull(accessToken);
        assertNotNull(refreshToken);

        RefreshTokenInfo refreshTokenInfo = refreshRepository.findByUsername(joinDTO.getUsername());
        assertNotNull(refreshTokenInfo);
        assertEquals(refreshToken, refreshTokenInfo.getRefresh());


        String username = jwtUtil.getUsername(accessToken);

        assertEquals(joinDTO.getUsername(),username ); // 사용자 이름 확인

    }
```

```java
@Test
    @DisplayName("유효기간이_끝난토큰")
    public void 유효기간이끝난_토큰() throws Exception{


        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsInVzZXJuYW1lIjoiZ29vcm05NCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MzQ1OTc5OTUsImV4cCI6MTczNDU5ODAzMX0.ZpHcPsjt09B2gJHWF8pIAzJaFyNZOiqTT_2KpMfr8pY";


        // When: /my 경로에 토큰으로 접근
        mockMvc.perform(get("/my")
                        .header("access", accessToken)) //헤더 추가
                .andExpect(status().isUnauthorized())  // 응답이 401코드
                .andExpect(content().string("access token expired")); // 응답 메시지 검증
    }
```




```java

public LuckyPouch findUsersLuckyPouches(Long questionCustomId) {

  LuckyPouch luckyPouch1 = queryFactory.select(luckyPouch)
          .from(luckyPouch)
          .join(luckyPouch.shape, shape).fetchJoin()
          .join(luckyPouch.questionCustom, questionCustom).fetchJoin()
          .join(luckyPouch.user, user).fetchJoin()
          .where(luckyPouch.questionCustom.id.eq(questionCustomId))
          .fetchOne();


  return luckyPouch1;
}

public LuckyPouch findUsers(Long questionCustomId){

  LuckyPouch luckyPouch1 = queryFactory.select(luckyPouch)
          .from(luckyPouch)
          .join(luckyPouch.user, user).fetchJoin()
          .where(luckyPouch.questionCustom.id.eq(questionCustomId))
          .fetchOne();

  return luckyPouch1;


}
```

```java
 public Slice<LuckyPouch> findUsersLuckyPouches(User finduser, Pageable pageable) {

        List<LuckyPouch> luckyPouches = queryFactory.select(luckyPouch)
                .from(luckyPouch)
                .join(luckyPouch.shape, shape).fetchJoin()
                .leftJoin(luckyPouch.questionCustom).fetchJoin() // questionCustom은 Left Join으로 fetchJoin
                .where(luckyPouch.user.eq(finduser))
                .offset(pageable.getOffset()) // 시작 위치 설정
                .limit(pageable.getPageSize() + 1) // 요청 크기보다 1 더 가져옴 (다음 페이지 존재 여부 확인)
                .orderBy(
                        questionCustomIsNotNullDesc(),  //  OrderSpecifier 메서드 사용
                        luckyPouch.questionCustom.createdAt.desc()   // 최신순 정렬
                )
                .fetch();

        boolean hasNext = luckyPouches.size() > pageable.getPageSize();
        if (hasNext) {
            luckyPouches.remove(pageable.getPageSize()); // 초과된 데이터 제거
        }

        return new SliceImpl<>(luckyPouches, pageable, hasNext);
    }

    // null 여부를 기준으로 정렬하는 메서드 (OrderSpecifier 기반)
    private OrderSpecifier<Integer> questionCustomIsNotNullDesc() {
        return new CaseBuilder()
                .when(luckyPouch.questionCustom.isNotNull()).then(1)
                .otherwise(0)
                .desc();
    }
```


서브쿼리 사용.
```java
  public Question findRandomQuestion(User findUser){
        Question findQuestion = queryFactory.select(question)
                .from(question)
                .where(question.id.notIn(
                        queryFactory.select(pouch.question.id)
                                .from(pouch)
                                .join(pouch.user, user)
                                .where(user.eq(findUser))
                ))
                .limit(1)
                .fetchOne();



        return findQuestion;

    }



```


@valid
```java
@Constraint(validatedBy = NicknameValidator.class)  // 유효성 검사를 처리할 Validator 지정
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nickname {

  String message() default "닉네임은 10자 이내, 띄어쓰기, 이모지, 특수문자 미포함 이어야합니다."; // 기본 오류 메시지

  Class<?>[] groups() default {};  // 그룹 분류

  Class<? extends Payload>[] payload() default {};  // 부가 정보
}
}
```

```java
public class NicknameValidator implements ConstraintValidator<Nickname, String> {

  // 유효성 검사 로직 구현
  @Override
  public boolean isValid(String nickname, ConstraintValidatorContext context) {
    if (nickname == null) {
      return false; // 닉네임이 null이면 유효하지 않음
    }

//         조건: 10자 이내, 특수문자/띄어쓰기/이모지 포함 금지

    String nicknamePattern = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$";

    return nickname.matches(nicknamePattern);
//        return true;
  }
}
```

```java
public class NicknameRequest {

    @NotNull(message = "닉네임은 필수 입력 값입니다.") // 추가
    @Nickname
    private String nickname;
}
```
```java
    @PostMapping("/users/validate")
    public ResponseEntity<ResponseDto<?>> validateUser(@Valid @RequestBody NicknameRequest request) {

        return new ResponseEntity<>(
                new ResponseDto<>("success", "user validate success", null, null, 200),
                HttpStatus.OK);
    }
```

생명주기를 활용한 도메인 객체 초기화 로직
````java
@Component
@RequiredArgsConstructor
public class ShapeInitializer {

    private final ShapeRepository shapeRepository;


    @PostConstruct
    public void init() {
        if (shapeRepository.count() == 0) {
//            List<String> shapes = List.of("Circle", "Square", "Triangle", "Rectangle", "Hexagon", "Octagon", "Pentagon", "Ellipse");
            List<String> shapes = List.of("A", "B", "C", "D", "E", "F", "G", "H");
            shapes.forEach(shapeName -> shapeRepository.save(Shape.builder().domain(shapeName).build()));
            System.out.println("Shape 데이터가 성공적으로 초기화되었습니다! 🌟");
        }
    }
}


````

userService 연관관계 고려한 람다식 활용한 삭제
```java
    public void deleteUser() {
        User currentUser = getCurrentUser(); // 현재 로그인된 사용자 가져오기

        pouchRepository.deleteByUserId(currentUser.getId());


        luckyPouchRepository.findByUserId(currentUser.getId()).forEach(luckyPouch -> {
            if (luckyPouch.getQuestionCustom() != null) {
                luckyPouchRepository.delete(luckyPouch);
                answerRepository.deleteByQuestionCustomId(luckyPouch.getQuestionCustom().getId());
                questionCustomRepository.deleteById(luckyPouch.getQuestionCustom().getId());
            }
        });


        luckyPouchRepository.deleteByUserId(currentUser.getId());


        userRepository.delete(currentUser);

    }
```

의존성 및 기타







## ⚙ Reference
- https://web.supertone.ai/v
- https://colormytree.me/auth
---





