package jp.co.sss.sportsCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.BulletinBoard;


public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Integer> {
    
}
