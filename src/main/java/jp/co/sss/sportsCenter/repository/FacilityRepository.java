package jp.co.sss.sportsCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.LendingFacility;

public interface FacilityRepository extends JpaRepository<LendingFacility, Integer> {

}
