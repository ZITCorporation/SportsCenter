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
@Table(name = "TOOL_MANAGEMENT")
public class ToolManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tool_management_gen")
    @SequenceGenerator(name = "seq_tool_management_gen", sequenceName = "SEQ_TOOL_MANAGEMENT", allocationSize = 1)
    private Integer toolManagementId;

    @ManyToOne
    @JoinColumn(name = "reserve_management_id", referencedColumnName = "reserveManagementId")
    private ReserveManagement reserveManagementId;

    @ManyToOne
    @JoinColumn(name = "tool_id", referencedColumnName = "toolId")
    private LendingTool toolId;

    @Column(name = "tool_number")
    private Integer toolNumber;

    public ToolManagement() {
    }

    public Integer getToolManagementId() {
        return toolManagementId;
    }

    public void setToolManagementId(Integer toolManagementId) {
        this.toolManagementId = toolManagementId;
    }

    public ReserveManagement getReserveManagementId() {
        return reserveManagementId;
    }

    public void setReserveManagementId(ReserveManagement reserveManagementId) {
        this.reserveManagementId = reserveManagementId;
    }

    public LendingTool getToolId() {
        return toolId;
    }

    public void setToolId(LendingTool toolId) {
        this.toolId = toolId;
    }

    public Integer getToolNumber() {
        return toolNumber;
    }

    public void setToolNumber(Integer toolNumber) {
        this.toolNumber = toolNumber;
    }

}
