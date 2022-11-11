package jp.co.sss.sportsCenter.form;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.LendingTool;

public class ReserveForm {
    private int reserveManagementId;
    private Integer facilityId;
    private String date;
    private String startTime;
    private String endingTime;
    private Integer toolId;
    private Integer toolNumber;

    public int getReserveManagementId() {
        return reserveManagementId;
    }

    public void setReserveManagementId(int reserveManagementId) {
        this.reserveManagementId = reserveManagementId;
    }


    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getEndingTime() {
        return endingTime;
    }
    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public Integer getToolId() {
        return toolId;
    }
    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public Integer getToolNumber() {
        return toolNumber;
    }
    public void setToolNumber(Integer toolNumber) {
        this.toolNumber = toolNumber;
    }
}
