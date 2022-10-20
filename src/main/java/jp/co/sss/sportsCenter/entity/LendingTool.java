package jp.co.sss.sportsCenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LENDING_TOOL")
public class LendingTool {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tool_gen")
    @SequenceGenerator(name = "seq_tool_gen", sequenceName = "seq_tool", allocationSize = 1)
    private Integer toolId;
    
    @Column(name = "tool_name")
    private String toolName;

    public LendingTool() {
    }

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    

}
