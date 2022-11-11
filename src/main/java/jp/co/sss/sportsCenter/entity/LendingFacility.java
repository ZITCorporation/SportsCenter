package jp.co.sss.sportsCenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LENDING_FACILITY")
public class LendingFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_facility_gen")
    @SequenceGenerator(name = "seq_facility_gen", sequenceName = "SEQ_FACILITY", allocationSize = 1)
    private Integer facilityId;
    
    @Column(name = "facility_name")
    private String facilityName;

    public LendingFacility() {
    }
    public LendingFacility(Integer facilityId, String facilityName) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}