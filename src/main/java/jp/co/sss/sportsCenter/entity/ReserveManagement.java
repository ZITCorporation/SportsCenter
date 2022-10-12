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
    @SequenceGenerator(name = "seq_reserve_management_gen", sequenceName = "seq_reserve_management", allocationSize = 1)
    private Integer managementId;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User id;

    @Column
    private Date date;

    @Column(name = "lending_time")
    private String lendingTime;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    private LendingFacility facilityId;

    public ReserveManagement() {
    }

    public Integer getManagementId() {
        return managementId;
    }

    public void setManagementId(Integer managementId) {
        this.managementId = managementId;
    }

    public User getId() {
        return id;
    }

    public void setId(User id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLendingTime() {
        return lendingTime;
    }

    public void setLendingTime(String lendingTime) {
        this.lendingTime = lendingTime;
    }

    public LendingFacility getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(LendingFacility facilityId) {
        this.facilityId = facilityId;
    }

}
