package jp.co.sss.sportsCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
