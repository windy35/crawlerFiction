package com.wen.crawler.vo;

import java.util.Date;

public interface BookChapterVo {

   long getBookId();

   String getAuthor();

   String getName();

   String getHref();

   Date getLastUpdateTime();

   String getLastChapter();

   String getLastChapterHref();

   String getLocalCoverImgPath();

   String getWebCoverImgPath();

   String getIntroduction();

   String getType();

   int getHeat();

   String getTitle();

   String getContent();

   String getChapterHref();

}
