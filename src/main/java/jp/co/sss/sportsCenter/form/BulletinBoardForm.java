package jp.co.sss.sportsCenter.form;

public class BulletinBoardForm {
    private int bulletinBoardId;
    private String createTime;
    private String bbsTitle;
    private String bbsContent;
    private String bbsAuthor;

    public String getBbsTitle() {
        return bbsTitle;
    }

    public void setBbsTitle(String bbsTitle) {
        this.bbsTitle = bbsTitle;
    }

    public int getBulletinBoardId() {
        return bulletinBoardId;
    }

    public void setBulletinBoardId(int bulletinBoardId) {
        this.bulletinBoardId = bulletinBoardId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBbsContent() {
        return bbsContent;
    }

    public void setBbsContent(String bbsContent) {
        this.bbsContent = bbsContent;
    }

    public String getBbsAuthor() {
        return bbsAuthor;
    }

    public void setBbsAuthor(String bbsAuthor) {
        this.bbsAuthor = bbsAuthor;
    }

}
