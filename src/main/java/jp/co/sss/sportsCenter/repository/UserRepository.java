package jp.co.sss.sportsCenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email , String password);
}
