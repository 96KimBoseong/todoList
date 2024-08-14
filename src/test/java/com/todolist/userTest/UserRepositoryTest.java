package com.todolist.userTest;

import com.todolist.domain.user.model.User;
import com.todolist.domain.user.model.UserRole;
import com.todolist.domain.user.repository.UserRepository;
import com.todolist.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User(
                "테스트유저",
                "test01",
                "Qwer1234",
                UserRole.USER
        );
        userRepository.save(user);
    }

    @Test
    void existsByUsername테스트하기() {
        //given
        String username = "test01";
        //when
        boolean user = userRepository.existsByUsername(username);
        //then
        assertTrue(user);

    }

    @Test
    void existsByUsername테스트하기실패() {
        //given
        String username = "test02";
        //when
        boolean user = userRepository.existsByUsername(username);
        //then
        assertFalse(user);
    }

    @Test
    void findByUserIdTest() {

        String username = "test01";

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("user not found")
        );
        assertNotNull(user);
    }

    @Test
    void findByUserIdTest실패() {

        String username = "test02";

        assertThrows(NotFoundException.class, () -> userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("user not found")
        ));
    }
}