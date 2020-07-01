package com.quicktron.common.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class PageInfo<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageSize;

    private int totalPages;

    private int totalRecords;

    private int currentPage;

    @SuppressWarnings("unused")
    private int fromRecord;

    public PageInfo() {
        this(1);
    }

    public int getTotalPages() {
        totalPages = (totalRecords + pageSize - 1) / pageSize;
        return totalPages;
    }

    public PageInfo(int currentPage) {
        this(currentPage, 10);
    }

    public PageInfo(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        if (pageSize > 0)
            this.pageSize = pageSize;
        if (this.currentPage < 1)
            this.currentPage = 1;
    }

    public int getFromRecord() {
        return (currentPage - 1) * pageSize;
    }

    public void setFromRecord(int fromRecord) {
        this.fromRecord = fromRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
