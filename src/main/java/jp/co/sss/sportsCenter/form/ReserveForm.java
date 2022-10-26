package jp.co.sss.sportsCenter.form;

public class ReserveForm {
    private int reserveManagementId;
    private int userId;
    private int facilityId;
    private String startTime;
    private String endingTime;
    private int toolId;
    private int toolNum;

    public int getReserveManagementId() {
        return reserveManagementId;
    }

    public void setReserveManagementId(int reserveManagementId) {
        this.reserveManagementId = reserveManagementId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
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

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public int getToolNum() {
        return toolNum;
    }

    public void setToolNum(int toolNum) {
        this.toolNum = toolNum;
    }
}
