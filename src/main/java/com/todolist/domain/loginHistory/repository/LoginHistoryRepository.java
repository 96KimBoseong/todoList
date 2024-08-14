package com.todolist.domain.loginHistory.repository;

import com.todolist.domain.loginHistory.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
