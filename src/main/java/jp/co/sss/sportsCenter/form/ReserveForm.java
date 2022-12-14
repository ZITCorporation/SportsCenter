package jp.co.sss.sportsCenter.form;

import java.util.List;

public class ReserveForm {
    private Integer reserveManagementId;
    private Integer facilityId;
    private String facilityName;
    private String date;
    private String startTime;
    private String endingTime;
    private List<ToolForm> toolList;

    public Integer getReserveManagementId() {
        return reserveManagementId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public List<ToolForm> getToolList() {
        return toolList;
    }

    public void setToolList(List<ToolForm> toolList) {
        this.toolList = toolList;
    }
}
