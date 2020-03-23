package com.wen.crawler.Tools;

import java.util.ArrayList;
import java.util.List;

public class JpaPageHelper {
//        public List<JpaPageInfo> SetStartPage(List<?> list,int pageNow,int Size){
public JpaPageInfo SetStartPage(List<?> list,int pageNow,int Size){
            boolean isFristPage=false;
            boolean isLastPage=false;
            boolean haveNexPage=false;
            boolean havePerPage=false;
            int pageSize=0;
            int fromIndex=(pageNow-1)*Size;
            int toIndex=pageNow*Size;
            if(fromIndex==0) {
                isFristPage=true;
            }else if (!isFristPage) {
                havePerPage=true;
            }
            if(toIndex>=list.size()) {
                toIndex=list.size();
                isLastPage=true;
            }else if (!isLastPage) {
                haveNexPage=true;
            }
            if(list.size()%Size==0) {
                pageSize=list.size()/Size;
            }else {
                pageSize=list.size()/Size+1;
            }
            List<JpaPageInfo> pageInfos=new ArrayList<>();
            JpaPageInfo pageInfo=new JpaPageInfo();
            pageInfo.setPageNow(pageNow);
            pageInfo.setTotlePage(pageSize);
            pageInfo.setPgaeSize(Size);
            pageInfo.setFristPage(isFristPage);
            pageInfo.setLastPage(isLastPage);
            pageInfo.setHaveNexPage(haveNexPage);
            pageInfo.setHavePerPage(havePerPage);
            pageInfo.setList(list.subList(fromIndex, toIndex));
            pageInfo.setTotalNum(list.size());
//            pageInfos.add(pageInfo);
            return pageInfo;
    }

}
