package jp.co.sss.sportsCenter.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.ReserveManagement;
import jp.co.sss.sportsCenter.entity.User;

public interface ReserveManegementRepository extends JpaRepository<ReserveManagement, Integer> {
    ReserveManagement findByUserIdAndStartTimeAndEndingTime(User userId , Timestamp startTime, Timestamp endingTime);

}
