package com.todolist.userTest;

import com.todolist.domain.user.dto.LoginRequestDTO;
import com.todolist.domain.user.dto.LoginResponseDTO;
import com.todolist.domain.user.dto.SignUpDTO;
import com.todolist.domain.user.dto.UserResponseDTO;
import com.todolist.domain.user.model.User;
import com.todolist.domain.user.model.UserRole;
import com.todolist.domain.user.repository.UserRepository;
import com.todolist.domain.user.service.UserService;
import com.todolist.domain.user.service.UserServiceImpl;
import com.todolist.exception.NotFoundException;
import com.todolist.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtUtil jwtUtil;



    @Test
    public void testSignUp() {
        SignUpDTO signUpDTO = new SignUpDTO(
                "테스터훈",
                "njunju77",
                "Qwer1234",
                UserRole.USER
        );
        UserService userService = new UserServiceImpl(userRepository, jwtUtil);

        //given

        UserResponseDTO result = userService.signup(signUpDTO);
        // when
        assertEquals(signUpDTO.getUsername(), result.getUsername());
        assertEquals(signUpDTO.getNickname(), result.getNickname());
        //then

    }

    @Test
    public void testSignUp실패() {
        SignUpDTO signUpDTO = new SignUpDTO(
                "테스터훈",
                "njunju77",
                "Qwer1234",
                UserRole.USER
        );
//        User user = new User(
//                "테스터훈",
//                "njunju77",
//                "Qwer1234",
//                UserRole.USER
//        );
//
//        userRepository.save(user);
        when(userRepository.existsByUsername(signUpDTO.getUsername())).thenReturn(true);

        UserService userService = new UserServiceImpl(userRepository, jwtUtil);

        //given

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.signup(signUpDTO));

        assertEquals(
                "Username is already in use", exception.getMessage()
        );
        //then
    }

    @Test
    void loginTest() {
        LoginRequestDTO loginDTO = new LoginRequestDTO(
                "njunju77",
                "Qwer1234"
        );
        // requestDTO 객체 생성

        User user = new User("테스터훈", "njunju77", "Qwer1234", UserRole.USER);
        // 유저생성
        String token = "mockToken";
        // 모킹 토큰 생성

        UserService userService = new UserServiceImpl(userRepository, jwtUtil);
        // 유저 서비스 불러오기

        when(userRepository.findByUsername(loginDTO.getUsername())).thenReturn(Optional.of(user));
        //유저레포지토리의 findByUsername()을 모킹해서 가져와서 위에 만들어 놓은 user를 반환 한다고 가정하는것
        when(jwtUtil.createToken(user.getUsername(), user.getRole())).thenReturn(token);
        // 위 와같이 jwtUtil의 creatToken()을 가져와서 모킹 토큰을 반환 한다고 거짓부렁 하는거 하는척 ;
        HttpServletResponse response = mock(HttpServletResponse.class);
        //HttpServletResponse 를 모킹하기
        LoginResponseDTO responseDTO = userService.login(loginDTO,response);
        // 로그인 실행하기

        assertNotNull(responseDTO);
        // 반환 값이 있는지 없는지
        assertEquals(token, responseDTO.getToken());
        // 메서드 실행하고 반환형이 responseDTO의 토큰 값과 같은가?
        verify(jwtUtil).addJwtToCookie(token,response);
        // 위 메서드가 호출되었는지 검증
    }

    @Test
    void loginFailTest유저없음() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("njunju77", "Qwer1234");

        // 사용자 존재하지 않음
        when(userRepository.findByUsername(loginDTO.getUsername())).thenReturn(Optional.empty());

        HttpServletResponse response = mock(HttpServletResponse.class);

        UserService userService = new UserServiceImpl(userRepository, jwtUtil);

        // 예외가 발생해야 함
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.login(loginDTO, response)
        );

        assertEquals("user 정보가 없습니다 아이디를 확인해보세요", exception.getMessage());
    }

    @Test
    void loginFailTest비밀번호없음() {
        LoginRequestDTO loginDTO = new LoginRequestDTO("njunju77", "비번아니지롱");
        User user = new User("테스터훈", "njunju77", "Qwer1234", UserRole.USER);
        when(userRepository.findByUsername(loginDTO.getUsername())).thenReturn(Optional.of(user));

        UserService userService = new UserServiceImpl(userRepository, jwtUtil);
        HttpServletResponse response = mock(HttpServletResponse.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () ->userService.login(loginDTO,response)
        );

        assertEquals("password is wrong",exception.getMessage());



    }
}
