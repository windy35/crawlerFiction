package com.wen.crawler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BookCase")
public class BookCase implements Serializable {
    private static final long serialVersionUID = 7419229779731522702L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "bookName",length = 40)
    private String bookName;

    @Column(name = "usersId")
    private long usersId;

    @Column(name = "bookId")
    private long bookId;

    @Column(name = "author",length = 20)
    private String author;

    @Column(name = "tagLastUpdateTime")
    private Date tagLastUpdateTime;

    @Column(name = "tagChapterName",length = 80)
    private String tagChapterName;

    @Column(name = "tagChapterId",columnDefinition = "bigint default 0")
    private long tagChapterId;

    @Column(name = "lastUpdateChapterId",columnDefinition = "bigint default 0")
    private long lastUpdateChapterId;

    @Column(name = "lastUpdateChapterName",length = 80)
    private String lastUpdateChapterName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public long getUsersId() {
        return usersId;
    }

    public void setUsersId(long usersId) {
        this.usersId = usersId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTagLastUpdateTime() {
        return tagLastUpdateTime;
    }

    public void setTagLastUpdateTime(Date tagLastUpdateTime) {
        this.tagLastUpdateTime = tagLastUpdateTime;
    }

    public String getTagChapterName() {
        return tagChapterName;
    }

    public void setTagChapterName(String tagChapterName) {
        this.tagChapterName = tagChapterName;
    }

    public long getLastUpdateChapterId() {
        return lastUpdateChapterId;
    }

    public void setLastUpdateChapterId(long lastUpdateChapterId) {
        this.lastUpdateChapterId = lastUpdateChapterId;
    }

    public String getLastUpdateChapterName() {
        return lastUpdateChapterName;
    }

    public void setLastUpdateChapterName(String lastUpdateChapterName) {
        this.lastUpdateChapterName = lastUpdateChapterName;
    }

    public long getTagChapterId() {
        return tagChapterId;
    }

    public void setTagChapterId(long tagChapterId) {
        this.tagChapterId = tagChapterId;
    }

    @Override
    public String toString() {
        return "BookCase{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", usersId=" + usersId +
                ", bookId=" + bookId +
                ", author='" + author + '\'' +
                ", tagLastUpdateTime=" + tagLastUpdateTime +
                ", tagChapterName='" + tagChapterName + '\'' +
                ", tagChapterId=" + tagChapterId +
                ", lastUpdateChapterId=" + lastUpdateChapterId +
                ", lastUpdateChapterName='" + lastUpdateChapterName + '\'' +
                '}';
    }
}
