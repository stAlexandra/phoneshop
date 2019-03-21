package com.es.core.util;

import junit.framework.TestCase;

public class PageIndexUtilTest extends TestCase {
    public void testGetIndexesWhenNothingToShow() {
        assertEquals(1, PageIndexUtil.getBegin(0, 0, 0));
        assertEquals(1, PageIndexUtil.getEnd(0, 0, 0));

        assertEquals(1, PageIndexUtil.getBegin(0, 1, 0));
        assertEquals(1, PageIndexUtil.getEnd(0, 1, 0));

        assertEquals(1, PageIndexUtil.getBegin(0, 1, 1));
        assertEquals(1, PageIndexUtil.getEnd(0, 1, 1));

        assertEquals(1, PageIndexUtil.getBegin(1, 1, 1));
        assertEquals(1, PageIndexUtil.getEnd(1, 1, 1));
    }

    public void testGetIndexesWhenCurrentIsMoreThanHalf() {
        int currentPageIndex = 6;
        int numPageIndexes = 9;

        int begin = PageIndexUtil.getBegin(currentPageIndex, numPageIndexes, 20);
        int end = PageIndexUtil.getEnd(currentPageIndex, numPageIndexes, 20);

        assertEquals(currentPageIndex - numPageIndexes / 2, begin);
        assertEquals(currentPageIndex + numPageIndexes / 2, end);
    }

    public void testGetIndexesWhenCurrentIsLastPage() {
        int numPageIndexes = 9;
        int totalPages = 30;

        int begin = PageIndexUtil.getBegin(totalPages, numPageIndexes, totalPages);
        int end = PageIndexUtil.getEnd(totalPages, numPageIndexes, totalPages);

        assertEquals(totalPages - numPageIndexes + 1, begin);
        assertEquals(totalPages, end);
    }
}