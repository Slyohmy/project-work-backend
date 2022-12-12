package projectwork.backend.test.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projectwork.backend.controller.AuthController;
import projectwork.backend.payload.LoginRequest;

import java.util.Objects;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ObjectMapper.class, AuthController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthJwtTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    static String registerUrl;
    static String loginUrl;
    static String logoutUrl;


    @BeforeEach
    public void setUp() {
        registerUrl = "/api/v1/auth/register";
        loginUrl = "/api/v1/auth/login";
        logoutUrl = "/api/v1/auth/logout";
    }

    @Test
    @Order(1)
    void login() throws Exception {
        LoginRequest defaultAuth = new LoginRequest("slyohmy", "123123");

        System.out.println(objectMapper.writeValueAsString(defaultAuth));

        MvcResult result = this.mvc
                .perform(MockMvcRequestBuilders.post(loginUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(defaultAuth)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String cookie = Objects.requireNonNull(result.getResponse().getCookie("slyohmy")).getValue();
        System.out.println(cookie);
    }

    @Test
    void logout() {
    }

}