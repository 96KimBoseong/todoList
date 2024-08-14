package com.todolist.domain.loginHistory;

import com.todolist.domain.loginHistory.model.LoginHistory;
import com.todolist.domain.loginHistory.repository.LoginHistoryRepository;
import com.todolist.domain.user.dto.LoginRequestDTO;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginHistoryAOP {

    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistoryAOP(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    @Pointcut("execution(* com.todolist.domain.user.controller.UserController.login(..))")
    private void login(){}
    // 포인트컷 설정
    @Before("login() && args(requestDTO,..)")
    public void loginTry(LoginRequestDTO requestDTO){
        String description = "로그인 시도";
        LoginHistory loginHistory = new LoginHistory(
                requestDTO.getUsername(),
                description
        );
        loginHistoryRepository.save(loginHistory);
    }

    @AfterReturning("login() && args(requestDTO,..)")
    public void loginSuccess( LoginRequestDTO requestDTO){
        String description = "로그인 성공";
        LoginHistory loginHistory = new LoginHistory(
                requestDTO.getUsername(),
                description
        );
        loginHistoryRepository.save(loginHistory);
    }
    @AfterThrowing("login() && args(requestDTO,..)")
    public void loginFail( LoginRequestDTO requestDTO){
        String description = "로그인 실패";
        LoginHistory loginHistory = new LoginHistory(
                requestDTO.getUsername(),
                description
        );
        loginHistoryRepository.save(loginHistory);
    }

}
