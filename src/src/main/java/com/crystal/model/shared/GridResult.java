package com.crystal.model.shared;

import java.util.List;

/**
 * Created by Administrator on 12/18/2015.
 */
public class GridResult<T> {
    private Long total;
    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
