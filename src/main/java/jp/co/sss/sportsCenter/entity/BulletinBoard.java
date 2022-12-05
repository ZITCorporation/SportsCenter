package jp.co.sss.sportsCenter.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BULLETIN_BOARD")
public class BulletinBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bbs_gen")
    @SequenceGenerator(name = "seq_bbs_gen", sequenceName = "SEQ_BBS", allocationSize = 1)
    private Integer bulletinBoardId;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "bbs_title")
    private String bbsTitle;

    @Column(name = "bbs_content")
    private String bbsContent;

    @Column(name = "bbs_author")
    private String bbsAuthor;

    public BulletinBoard() {
    }

    public BulletinBoard(Integer bulletinBoardId, Timestamp createTime, String bbsTitle, String bbsContent,
            String bbsAuthor) {
        this.bulletinBoardId = bulletinBoardId;
        this.createTime = createTime;
        this.bbsTitle = bbsTitle;
        this.bbsContent = bbsContent;
        this.bbsAuthor = bbsAuthor;
    }

    public Integer getBulletinBoardId() {
        return bulletinBoardId;
    }

    public void setBulletinBoardId(Integer bulletinBoardId) {
        this.bulletinBoardId = bulletinBoardId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getBbsTitle() {
        return bbsTitle;
    }

    public void setBbsTitle(String bbsTitle) {
        this.bbsTitle = bbsTitle;
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
