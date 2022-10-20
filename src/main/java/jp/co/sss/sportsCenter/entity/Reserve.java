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
@Table(name = "RESERVE")
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reserve_gen")
    @SequenceGenerator(name = "seq_reserve_gen", sequenceName = "seq_reserve", allocationSize = 1)
    private Integer reserveId;

    @ManyToOne
    @JoinColumn(name = "management_id", referencedColumnName = "reserveManagementId")
    private ReserveManagement reserveManagementId;

    @ManyToOne
    @JoinColumn(name = "tool_id", referencedColumnName = "toolId")
    private LendingTool toolId;
    
    @Column
    private Integer number;

    public Reserve() {
    }

    public Integer getReserveId() {
        return reserveId;
    }

    public void setReserveId(Integer reserveId) {
        this.reserveId = reserveId;
    }

    public ReserveManagement getManagementId() {
        return reserveManagementId;
    }

    public void setManagementId(ReserveManagement reserveManagementId) {
        this.reserveManagementId = reserveManagementId;
    }

    public LendingTool getToolId() {
        return toolId;
    }

    public void setToolId(LendingTool toolId) {
        this.toolId = toolId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    
}
