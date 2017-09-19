package com.linyi.config;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private int pageNo = 1;
    private int pageSize = 10;
    private int totalRecord;
    private int totalPage;
    private List<T> results;

    public int getOffset() {

        if (this.pageSize <= 0) {
            return 0;

        } else {

            if (this.totalPage <= this.pageNo) {
                pageNo = totalPage;
            }

            int offset = (this.pageNo - 1) * this.pageSize;

            if (offset < 0) {
                offset = 0;
            }

            return offset;
        }
    }
    
    public int getLimit() {

        if (this.pageSize <= 0) {
            return 0;
        } else {

            int limit = 0;

            if (this.totalPage <= this.pageNo) {

                this.pageNo = this.totalPage;

                limit = this.totalRecord - (this.pageNo - 1) * this.pageSize;

            } else {
                limit = this.pageSize;
            }

            return limit;
        }
    }

    public void setTotalRecord(int totalRecord) {

        this.totalRecord = totalRecord;

        if (this.pageSize != 0) {

            int totalPage = totalRecord / this.pageSize;

            if (totalRecord % this.pageSize != 0) {
                totalPage++;
            }

            this.totalPage = totalPage;
        }

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}
