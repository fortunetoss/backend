//package com.backend.question;
//
//import com.backend.common.security.basic.JoinDTO;
//import com.backend.fortunetoss.question.QuestionCustomRepository;
//import com.backend.fortunetoss.question.QuestionCustomService;
//import com.backend.common.security.oauth.CustomOAuth2User;
//import com.backend.fortunetoss.user.UserDTO;
//import com.backend.fortunetoss.user.UserRepository;
//import com.backend.pouch.PouchRepository;
//import com.backend.pouch.Question;
//import com.backend.pouch.QuestionRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//public class CustomQuestionTest {
//
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    private PouchRepository pouchRepository;
//
//    @Autowired
//    private QuestionCustomService questionCustomService;
//
//    @Autowired
//    private QuestionCustomRepository questionCustomRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @BeforeEach
//    public void insertMember() throws Exception {
//
//        JoinDTO joinDTO = new JoinDTO();
//        joinDTO.setUsername("goorm94@naver.com");
//        joinDTO.setPassword("password1234&");
//
//        String jsonRequest = objectMapper.writeValueAsString(joinDTO);
//
//        mockMvc.perform(post("/join")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//
//        for(int i=1; i<=10; i++) {
//
//            Question question = Question.builder()
//                    .title("title"+i)
//                    .select1("select1")
//                    .select2("select2")
//                    .select3("select3")
//                    .select4("select4")
//                    .answer("answer"+i)
//                    .category("category"+i)
//                    .content("content"+i)
//                    .build();
//
//            questionRepository.save(question);
//        }
//
//        //토큰에서 username과 role 획득
//        String username = "goorm94@naver.com";
//        String role = "ROLE_USER";
//
//        //userDTO를 생성하여 값 set
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setRole(role);
//
//        //UserDetails에 회원 정보 객체 담기
//        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
//
//        //스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
//        //세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//    }
//
//
////    /*todo 까먹기 전에 적어놓음 !
////    /*todo 문제내역은 -> userid로 onetoMany 조회되어있는거 가져오면되고
////    /*todo 링크공유는 -> 저장 후에 id값 반환해서 링크에 넣는다 생각하면 되고 !
//
////    @Test
////    @DisplayName("문제 저장")
////    @DirtiesContext
////    public void insertQuiz() throws Exception{
////
////        //given
////        CustomQuestionRequestDTO customQuestionRequestDTO = new CustomQuestionRequestDTO();
////        customQuestionRequestDTO.setTitle("newTitle");
////        customQuestionRequestDTO.setSelect1("select1");
////        customQuestionRequestDTO.setSelect2("select2");
////        customQuestionRequestDTO.setSelect3("select3");
////        customQuestionRequestDTO.setSelect4("select4");
////        customQuestionRequestDTO.setAnswer("answer");
////        customQuestionRequestDTO.setCategory("category");
////        customQuestionRequestDTO.setContent("content");
////
////        //when
////        CustomQuestion customQuestion = questionCustomService.saveCustomQuestion(customQuestionRequestDTO);
////        CustomQuestion customQuestion2 = questionCustomService.saveCustomQuestion(customQuestionRequestDTO);
////
////
////
////        //then
////        Long id = customQuestion.getId();
////        Assertions.assertThat(id).isEqualTo(1L);
////
////        String username = customQuestion.getUser().getUsername();
////        Assertions.assertThat(username).isEqualTo("goorm94@naver.com");
////
////        List<CustomQuestion> all = questionCustomRepository.findAll();
////        Assertions.assertThat(all.size()).isEqualTo(2);
////
////        User byUsername = userRepository.findByUsername("goorm94@naver.com");
////        Assertions.assertThat(byUsername.getCount()).isEqualTo(8);
////
////        List<CustomQuestion> byUser = questionCustomRepository.findByUser(userRepository.findByUsername("goorm94@naver.com"));
////        Assertions.assertThat(byUser.size()).isEqualTo(2);
////
////    }
////
////    @DisplayName("유저가 낸 문제 조회")
////    @Test
////    public void testMember() throws Exception{
////        //given
////        CustomQuestionRequestDTO customQuestionRequestDTO = new CustomQuestionRequestDTO();
////        customQuestionRequestDTO.setTitle("newTitle");
////        customQuestionRequestDTO.setSelect1("select1");
////        customQuestionRequestDTO.setSelect2("select2");
////        customQuestionRequestDTO.setSelect3("select3");
////        customQuestionRequestDTO.setSelect4("select4");
////        customQuestionRequestDTO.setAnswer("answer");
////        customQuestionRequestDTO.setCategory("category");
////        customQuestionRequestDTO.setContent("content");
////
////        //when
////        CustomQuestion customQuestion = questionCustomService.saveCustomQuestion(customQuestionRequestDTO);
////        CustomQuestion customQuestion2 = questionCustomService.saveCustomQuestion(customQuestionRequestDTO);
////
////        //then
////
////        List<CustomQuestion> questions = questionCustomService.getQuestions();
////
////
////        Assertions.assertThat(questions.size()).isEqualTo(2);
////
////    }
//
//
//
//
//
//}
