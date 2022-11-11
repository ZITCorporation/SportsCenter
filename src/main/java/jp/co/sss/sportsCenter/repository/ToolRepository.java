package jp.co.sss.sportsCenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.LendingTool;

public interface ToolRepository extends JpaRepository<LendingTool, Integer> {

    List<LendingTool> findByFacilityId(LendingFacility facility);

}
