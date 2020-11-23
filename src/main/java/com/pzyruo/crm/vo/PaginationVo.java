package com.pzyruo.crm.vo;

import java.util.List;

public class PaginationVo<T> {
    private Integer id;
    private List<T> dataList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
