package com.wen.crawler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 7419229779731522702L;

    public Book() {
    }

    public Book(String author, String name, String href, Date lastUpdateTime, String lastChapter, String lastChapterHref, String localCoverImgPath, String webCoverImgPath, String introduction, String type, int heat, long chapterCount) {
        this.author = author;
        this.name = name;
        this.href = href;
        this.lastUpdateTime = lastUpdateTime;
        this.lastChapter = lastChapter;
        this.lastChapterHref = lastChapterHref;
        this.localCoverImgPath = localCoverImgPath;
        this.webCoverImgPath = webCoverImgPath;
        this.introduction = introduction;
        this.type = type;
        this.heat = heat;
        this.chapterCount = chapterCount;
    }

    @Id
    @Column(name = "BookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long BookId;

    @Column(name = "author",length = 20)
    private String author;

    @Column(name = "name",length = 40)
    private String name;

    @Column(name = "href",length = 100)
    private String href;

    @Column(name = "lastUpdateTime")
    private Date lastUpdateTime;

    @Column(name = "lastChapter",length = 150)
    private String lastChapter;

    @Column(name = "lastChapterHref",length = 100)
    private String lastChapterHref;

    @Lob
    @Column(name = "localCoverImgPath",columnDefinition="mediumtext")
    private String localCoverImgPath;

    @Column(name="WebCoverImgPath",length = 100)
    private String webCoverImgPath;

    @Lob
    @Column(name = "introduction",columnDefinition="text")
    private String introduction;

    @Column(name = "type",length = 20)
    private String type;

    @Column(name = "heat",columnDefinition = "bigint default 0")
    private long heat;

    @Column(name = "chapterCount",columnDefinition = "bigint default 0")
    private long chapterCount;


    @Column(name = "lastUpdateChapterId",columnDefinition = "bigint default 0")
    private long lastUpdateChapterId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getBookId() {
        return BookId;
    }

    public void setBookId(long bookId) {
        BookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getLastChapterHref() {
        return lastChapterHref;
    }

    public void setLastChapterHref(String lastChapterHref) {
        this.lastChapterHref = lastChapterHref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocalCoverImgPath() {
        return localCoverImgPath;
    }

    public void setLocalCoverImgPath(String localCoverImgPath) {
        this.localCoverImgPath = localCoverImgPath;
    }

    public String getWebCoverImgPath() {
        return webCoverImgPath;
    }

    public void setWebCoverImgPath(String webCoverImgPath) {
        this.webCoverImgPath = webCoverImgPath;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public long getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(long chapterCount) {
        this.chapterCount = chapterCount;
    }

    public void setHeat(long heat) {
        this.heat = heat;
    }

    public long getHeat() {
        return heat;
    }


    public long getLastUpdateChapterId() {
        return lastUpdateChapterId;
    }

    public void setLastUpdateChapterId(long lastUpdateChapterId) {
        this.lastUpdateChapterId = lastUpdateChapterId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "BookId=" + BookId +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", href='" + href + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", lastChapter='" + lastChapter + '\'' +
                ", lastChapterHref='" + lastChapterHref + '\'' +
                ", localCoverImgPath='" + localCoverImgPath + '\'' +
                ", webCoverImgPath='" + webCoverImgPath + '\'' +
                ", introduction='" + introduction + '\'' +
                ", type='" + type + '\'' +
                ", heat=" + heat +
                ", chapterCount=" + chapterCount +
                ", lastUpdateChapterId=" + lastUpdateChapterId +
                '}';
    }
}
