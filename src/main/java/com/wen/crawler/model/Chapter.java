package com.wen.crawler.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chapter")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Chapter implements Serializable {
    private static final long serialVersionUID = 7419229779731522702L;

    public Chapter() {
    }

    public Chapter(String title, String content, String href) {
        this.title = title;
        this.content = content;
        this.href = href;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "bookId")
    private long bookId;

    @Column(name = "chapterId")
    private long chapterId;

    @Column(name = "title",length = 80)
    private String title;

    @Lob
    @Column(name = "content",columnDefinition="text")
    private String content;

    @Column(name = "href",length = 100)
    private String href;

    @Column(name = "nextChapterId",columnDefinition = "bigint default 0")
    private long nextChapterId;


    @Column(name = "preChapterId",columnDefinition = "bigint default 0")
    private long preChapterId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNextChapterId() {
        return nextChapterId;
    }

    public void setNextChapterId(long nextChapterId) {
        this.nextChapterId = nextChapterId;
    }

    public long getPreChapterId() {
        return preChapterId;
    }

    public void setPreChapterId(long preChapterId) {
        this.preChapterId = preChapterId;
    }

    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", chapterId=" + chapterId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", href='" + href + '\'' +
                ", nextChapterId=" + nextChapterId +
                ", preChapterId=" + preChapterId +
                '}';
    }
}
