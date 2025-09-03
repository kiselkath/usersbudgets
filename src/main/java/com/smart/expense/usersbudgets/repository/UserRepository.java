package com.smart.expense.usersbudgets.repository;

import com.smart.expense.usersbudgets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
