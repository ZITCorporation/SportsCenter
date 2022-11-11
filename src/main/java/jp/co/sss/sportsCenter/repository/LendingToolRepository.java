package jp.co.sss.sportsCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.LendingTool;

public interface LendingToolRepository extends JpaRepository<LendingTool, Integer> {

}
