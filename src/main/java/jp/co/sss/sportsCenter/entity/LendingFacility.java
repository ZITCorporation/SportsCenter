package jp.co.sss.sportsCenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LENGDING_FACILITY")
public class LendingFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_facility_gen")
    @SequenceGenerator(name = "seq_facility_gen", sequenceName = "seq_facility", allocationSize = 1)
    private Integer id;
    
    @Column(name = "facility_name")
    private String facilityName;

    public LendingFacility() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

}
