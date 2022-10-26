package jp.co.sss.sportsCenter.entity;

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

    @Column
    private Date date;

    // @Column(name = "lending_time")
    // private String lendingTime;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "ending_time")
    private String endingTime;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "facilityId")
    private LendingFacility facilityId;

    public ReserveManagement() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getendingTime() {
        return endingTime;
    }

    public void setendingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public LendingFacility getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LendingFacility facilityId) {
        this.facilityId = facilityId;
    }
    

}
