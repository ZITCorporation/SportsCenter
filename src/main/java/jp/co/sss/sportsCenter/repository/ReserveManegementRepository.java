package jp.co.sss.sportsCenter.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.ReserveManagement;
import jp.co.sss.sportsCenter.entity.User;

public interface ReserveManegementRepository extends JpaRepository<ReserveManagement, Integer> {
    ReserveManagement findByUserIdAndStartTimeAndEndingTime(User userId , Timestamp startTime, Timestamp endingTime);
    List<ReserveManagement> findByUserId(User userId);
    ReserveManagement findByFacilityId(LendingFacility facilityId);

}
