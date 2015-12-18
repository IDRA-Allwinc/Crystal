package com.crystal.service.shared;

import com.crystal.model.shared.GridResult;

/**
 * Created by Administrator on 12/18/2015.
 */
public interface GridService<T> {
    GridResult<T> toGrid();
}
