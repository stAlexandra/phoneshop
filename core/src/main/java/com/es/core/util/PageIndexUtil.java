package com.es.core.util;

public class PageIndexUtil {
    public static int getBegin(int currentPageIndex, int numPageIndexes, int totalPages){
        int beginPage;
        if (currentPageIndex <= numPageIndexes / 2) {
            beginPage = 1;
        } else {
            beginPage = currentPageIndex - numPageIndexes / 2;
            if (currentPageIndex + numPageIndexes / 2 > totalPages) {
                beginPage = totalPages - numPageIndexes + 2;
            }
        }
        return beginPage;
    }

    public static int getEnd(int currentPageIndex, int numPageIndexes, int totalPages){
        int endPage;
        if (currentPageIndex <= numPageIndexes / 2) {
            endPage = numPageIndexes;
        } else {
            endPage = currentPageIndex + numPageIndexes / 2;
            if (endPage > totalPages) {
                endPage = totalPages;
            }
        }
        return endPage;
    }
}
