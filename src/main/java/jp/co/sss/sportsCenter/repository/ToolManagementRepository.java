package jp.co.sss.sportsCenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sportsCenter.entity.ReserveManagement;
import jp.co.sss.sportsCenter.entity.ToolManagement;

public interface ToolManagementRepository extends JpaRepository<ToolManagement, Integer> {

    List<ToolManagement> findByReserveManagementId(ReserveManagement reserveManagementId);

}
