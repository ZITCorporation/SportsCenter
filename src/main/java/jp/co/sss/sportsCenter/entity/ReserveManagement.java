package jp.co.sss.sportsCenter.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RESERVE_MANAGEMENT")
public class ReserveManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reserve_management_gen")
    @SequenceGenerator(name = "seq_reserve_management_gen", sequenceName = "SEQ_RESERVE_MANAGEMENT", allocationSize = 1)
    private Integer reserveManagementId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User userId;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "ending_time")
    private Timestamp endingTime;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "facilityId")
    private LendingFacility facilityId;

    public ReserveManagement() {
    }
    
    public ReserveManagement(Integer reserveManagementId, User userId, Timestamp startTime, Timestamp endingTime, LendingFacility facilityId) {
        this.reserveManagementId = reserveManagementId;
        this.userId = userId;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.facilityId = facilityId;
    }

    public Integer getReserveManagementId() {
        return reserveManagementId;
    }
    public void setReserveManagementId(Integer reserveManagementId) {
        this.reserveManagementId = reserveManagementId;
    }

    public User getUserId() {
        return userId;
    }
    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndingTime() {
        return endingTime;
    }
    public void setEndingTime(Timestamp endingTime) {
        this.endingTime = endingTime;
    }

    public LendingFacility getFacilityId() {
        return facilityId;
    }
    public void setFacilityId(LendingFacility facilityId) {
        this.facilityId = facilityId;
    }
    
}
