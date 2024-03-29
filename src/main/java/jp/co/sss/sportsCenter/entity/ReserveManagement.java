package jp.co.sss.sportsCenter.entity;

import java.sql.Timestamp;
import java.sql.Date;

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

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "facilityId")
    private LendingFacility facilityId;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "reserve_date")
    private Date reserveDate;

    @Column(name = "hour_list")
    private String hourList;

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

    public LendingFacility getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LendingFacility facilityId) {
        this.facilityId = facilityId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getHourList() {
        return hourList;
    }

    public void setHourList(String hourList) {
        this.hourList = hourList;
    }

}
