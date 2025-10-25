<h1 align="center">
  <br>
  <img src="https://github.com/user-attachments/assets/c08c03b0-7205-48fa-bf87-09974744c245" alt="bokkie logo" width="200">
  <br>
  Fortunetoss
</h1>

<p align="center">
  자신에 대한 퀴즈를 내고 친구와 공유하는 <b>행운 나눔 웹 서비스</b>
</p>

<hr/>

<h4 align="center">Team Bokkie 🐰</h4>

<p align="center">
  <i>"복 나누미가 되어 나를 퀴즈로 공유하고, 복 받으미가 되어 친구의 마음을 맞혀보세요."</i>
</p>

<hr/>

## 👥 팀 구성

| <img src="https://avatars.githubusercontent.com/u/152036928?v=4" width="120"> | <img src="https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg" width="120"> | <img src="https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg" width="120"> | <img src="https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg" width="120"> | <img src="https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg" width="120"> | <img src="https://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg" width="120"> |
|:---------------------------------------------:|:---------------------------------------------:|:---------------------------------------------:|:---------------------------------------------:|:---------------------------------------------:|:---------------------------------------------:|
| [HyungGeun](https://github.com/HyungGeun94)<br>**BE** | [MiYeon]<br>**PM** | [HyeWon]<br>**FE** | [YunA]<br>**FE** | [ChaeWon]<br>**DE** | [JiWon]<br>**DE** |

---

## 📆 프로젝트 개요

- **프로젝트명:** Fortunetoss
- **기간:** 2024.12.20 ~ 2025.01.23 (5주)
- **주제:** 자신에 대한 퀴즈를 만들어 공유하고, 친구가 퀴즈를 풀며 덕담과 행운을 나누는 서비스
- **배포:** ~~[Fortunetoss 홈페이지](https://fortunetoss.vercel.app)~~ (현재 배포 중단)

---

## 🎯 백엔드 목표

- JPA와 QueryDSL을 사용한 **도메인 중심 설계**
- **연관관계와 생명주기**를 고려한 엔티티 설계
- **@Valid 기반 유효성 검증** 및 커스텀 Validator 적용
- **OAuth2 / JWT / Refresh Token** 기반 인증 구조
- **테스트 코드**로 서비스 신뢰성 확보

---

## 🧰 기술 스택

### 🌱 Spring
| 기술 | 버전 |
|------|------|
| Java | 17 |
| Spring Boot | 3.4.0 |
| Spring Security / OAuth2 Client | 6.4.1 |
| JPA | 3.4.0 |
| QueryDSL | 5.0.0 |
| JWT | 0.12.3 |
| Slf4j | 2.24.1 |

### 🗄️ Database
| DB | 버전 |
|----|------|
| MySQL | 8.0 |
| H2 (테스트용) | 2.3.232 |

### 🌐 Infra
| 기술 | 버전 |
|------|------|
| Docker | 27.3.1 |
| Docker Compose | 2.30.3-desktop.1 |

---

## 📸 주요 화면

### 🎁 복 나누미 (퀴즈 출제자)

<p align="center">
  <img src="https://github.com/user-attachments/assets/eeba0802-2f83-4961-b1d4-f8c4e141f671" width="420"><br>
  <sub>로그인 메인 페이지</sub>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/131d57e7-30ea-41ca-afc5-d4811d53b0ad" width="420"><br>
  <sub>홈 화면</sub>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/7196273e-c5a3-45e6-8f20-ab472e1bc59b" width=""><br>
  <sub>문제 작성 및 덕담 입력</sub>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/ee2b2cf7-c1d5-4905-908a-fb5f92549355" width="420"><br>
  <sub>정답자·오답자 통계 확인</sub>
</p>

---

### 🍀 복 받으미 (퀴즈 풀이자)

<p align="center">
  <img src="https://github.com/user-attachments/assets/fa32e888-c68a-4c68-80a8-eae5dbe8804b" width=""><br>
  <sub>문제 풀이 및 덕담 확인</sub>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/ca96b075-454a-4b26-a657-8f78c1c69871" width="300"><br>
  <sub>결과 공유 및 신규 퀴즈 생성</sub>
</p>

---

## 🧩 ERD

<p align="center">
  <img src="https://github.com/user-attachments/assets/44ce5cb0-552b-4c5c-b253-51c571f37d6e" width="">
</p>

---

## 💡 주요 기능 및 코드 예시

공유,풀이,통계 확인,소셜로그인, 엑세스 리프레시 토큰 사용, 

### 🔐 인증 test (OAuth2 + JWT)
```java
@Test
@DisplayName("일반로그인_토큰발급성공")
void 로그인_성공() throws Exception {
    JoinDTO joinDTO = new JoinDTO("goorm94@naver.com", "password1234&");
    String loginRequest = objectMapper.writeValueAsString(joinDTO);

    MvcResult result = mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginRequest))
            .andExpect(cookie().exists("refresh"))
            .andExpect(header().exists("access"))
            .andExpect(status().isOk())
            .andReturn();

    String accessToken = result.getResponse().getHeader("access");
    String refreshToken = result.getResponse().getCookie("refresh").getValue();
    
    

    assertNotNull(accessToken);
    assertNotNull(refreshToken);
    assertEquals(joinDTO.getUsername(), jwtUtil.getUsername(accessToken));
}

public Slice<LuckyPouch> findUsersLuckyPouches(User findUser, Pageable pageable) {
    List<LuckyPouch> luckyPouches = queryFactory.select(luckyPouch)
        .from(luckyPouch)
        .join(luckyPouch.shape, shape).fetchJoin()
        .leftJoin(luckyPouch.questionCustom).fetchJoin()
        .where(luckyPouch.user.eq(findUser))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1)
        .orderBy(
            questionCustomIsNotNullDesc(),
            luckyPouch.questionCustom.createdAt.desc()
        )
        .fetch();

    boolean hasNext = luckyPouches.size() > pageable.getPageSize();
    if (hasNext) luckyPouches.remove(pageable.getPageSize());

    return new SliceImpl<>(luckyPouches, pageable, hasNext);
}
```
🧠 QueryDSL (복주머니 조회)
```java
private OrderSpecifier<Integer> questionCustomIsNotNullDesc() {
    return new CaseBuilder()
        .when(luckyPouch.questionCustom.isNotNull()).then(1)
        .otherwise(0)
        .desc();
}
```
서브쿼리 사용
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

📜 유효성 검증 (Custom @Valid)
```java
@Constraint(validatedBy = NicknameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nickname {
  String message() default "닉네임은 10자 이내, 특수문자·띄어쓰기·이모지 불가";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {}; 
}
public class NicknameValidator implements ConstraintValidator<Nickname, String> {
    //         조건: 10자 이내, 특수문자/띄어쓰기/이모지 포함 금지
    private static final String PATTERN = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$";
    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        return nickname != null && nickname.matches(PATTERN);
    }
}

public class NicknameRequest {

    @NotNull(message = "닉네임은 필수 입력 값입니다.") // 추가
    @Nickname
    private String nickname;
}
@PostMapping("/users/validate")
public ResponseEntity<ResponseDto<?>> validateUser(@Valid @RequestBody NicknameRequest request) {

    return new ResponseEntity<>(
        new ResponseDto<>("success", "user validate success", null, null, 200),
        HttpStatus.OK);
}
```
🌱 도메인 초기화 (생명주기 활용)
```java
@Component
@RequiredArgsConstructor
public class ShapeInitializer {
    private final ShapeRepository shapeRepository;

    @PostConstruct
    public void init() {
        if (shapeRepository.count() == 0) {
            List<String> shapes = List.of("A", "B", "C", "D", "E", "F", "G", "H");
            shapes.forEach(s -> shapeRepository.save(Shape.builder().domain(s).build()));
            System.out.println("Shape 데이터 초기화 완료 🌟");
        }
    }
}
```
🧹 회원탈퇴 로직 (연관관계 및 생명주기 고려)
```java
@Transactional
public void deleteUser() {
    User currentUser = getCurrentUser();

    pouchRepository.deleteByUserId(currentUser.getId());

    luckyPouchRepository.findByUserId(currentUser.getId()).forEach(pouch -> {
        if (pouch.getQuestionCustom() != null) {
            luckyPouchRepository.delete(pouch);
            answerRepository.deleteByQuestionCustomId(pouch.getQuestionCustom().getId());
            questionCustomRepository.deleteById(pouch.getQuestionCustom().getId());
        }
    });

    luckyPouchRepository.deleteByUserId(currentUser.getId());
    userRepository.delete(currentUser);
}
```

---

## 💭 프로젝트 회고

### 🙋🏻‍♂️ 전형근 (BE)

이번 프로젝트는 **처음으로 팀 단위에서 도메인 중심 설계(JPA + QueryDSL)** 를 적용해 본 경험이었다.  
단순 CRUD 중심의 백엔드가 아니라, **엔티티 간 연관관계와 생명주기**를 고민하며 설계했다는 점이 가장 큰 차이였다.

특히 기억에 남는 부분은 다음과 같다.

- **QueryDSL**로 복잡한 조건 검색과 페이징 로직을 직접 구현하며, SQL과 ORM 사이의 균형을 체감했다.
- **OAuth2 + JWT 인증 구조**를 구축하면서 토큰의 발급·갱신·만료 흐름을 코드로 명확히 다뤘다.
- **커스텀 @Valid 검증기**를 구현해 입력값 검증을 서비스 로직과 분리하는 구조적 사고를 익혔다.
- **생명주기(@PostConstruct)** 를 활용해 도메인 데이터를 초기화하며 Spring Lifecycle을 이해했다.
- 팀 내 코드 리뷰를 통해 “가독성보다 의도 전달이 우선”이라는 실무 기준을 체득했다.

이 프로젝트를 통해 **“조회는 SQL스럽게, 조립은 자바스럽게”** 라는 원칙의 중요성을 실감했다.  
코드를 단순히 작동시키는 것이 아니라, 유지보수와 확장성을 고려한 **설계 중심 개발자**로 성장한 계기였다.

> 💬 “5주간의 짧은 협업이었지만, 우리는 기능이 아닌 구조로 성장했다.”

---

## ⚙ 참고 자료
- [Supertone AI](https://web.supertone.ai)
- [Color My Tree](https://colormytree.me/auth)


<p align="center">
  <i>© 2025 Team Bokkie — Fortunetoss</i>
</p>

