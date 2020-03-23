package com.wen.crawler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Notice")
public class Notice implements Serializable {
    private static final long serialVersionUID = 7419229779731522702L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    @Column(name = "NoticeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noticeId;

    @Column(name = "setter",length = 30)
    private String setter;

    @Column(name = "title",length = 50)
    private String title;

    @Lob
    @Column(name = "content",columnDefinition="text")
    private String content;

    @Column(name = "releaseTime")
    private Date releaseTime;

    @Column(name = "attachmentHref" ,columnDefinition="text")
    private String attachmentHref;

    @Column(name = "attachmentName" ,columnDefinition="text")
    private String attachmentName;

    @Column(name = "lastUpdateTime")
    private Date lastUpdateTime;

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }

    public String getSetter() {
        return setter;
    }

    public void setSetter(String setter) {
        this.setter = setter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getAttachmentHref() {
        return attachmentHref;
    }

    public void setAttachmentHref(String attachmentHref) {
        this.attachmentHref = attachmentHref;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", setter='" + setter + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", releaseTime=" + releaseTime +
                ", attachmentHref='" + attachmentHref + '\'' +
                ", attachmentName='" + attachmentName + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
