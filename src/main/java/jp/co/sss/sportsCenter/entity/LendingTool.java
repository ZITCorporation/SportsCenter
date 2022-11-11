package jp.co.sss.sportsCenter.entity;

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
@Table(name = "LENDING_TOOL")
public class LendingTool {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tool_gen")
    @SequenceGenerator(name = "seq_tool_gen", sequenceName = "SEQ_TOOL", allocationSize = 1)
    private Integer toolId;
    
    @Column(name = "tool_name")
    private String toolName;
    
    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "facilityId")
    private LendingFacility facilityId;

    // 道具のID
    public LendingTool() {
    }
    public Integer getToolId() {
        return toolId;
    }
    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    // 道具の名前
    public String getToolName() {
        return toolName;
    }
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }
    
    // 施設
    public LendingFacility getFacilityId() {
        return facilityId;
    }
    public void setFacilityId(LendingFacility facilityId) {
        this.facilityId = facilityId;
    }
}