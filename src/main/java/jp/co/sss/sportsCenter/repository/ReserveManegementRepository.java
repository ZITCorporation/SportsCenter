package jp.co.sss.sportsCenter.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.ReserveManagement;
import jp.co.sss.sportsCenter.entity.User;

public interface ReserveManegementRepository extends JpaRepository<ReserveManagement, Integer> {
    ReserveManagement findByUserIdAndStartTimeAndEndingTime(User userId , Timestamp startTime, Timestamp endingTime);
    ReserveManagement findByFacilityId(LendingFacility facilityId);
    List<ReserveManagement> findAllByUserId(User userId,Sort sort);
    List<ReserveManagement> findAllByFacilityIdAndReserveDate(LendingFacility facilityId,Timestamp date,Sort sort);
    ReserveManagement findByUserIdAndReserveDateAndHourList(User userId, Date reserveDate, String hourList);

}
