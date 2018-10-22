package com.yibi.common.model;

import lombok.Data;

/**
 * 分页实体
 */
@Data
public class PageModel {
    private Integer firstResult;
    private Integer maxResult;

    public PageModel(Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 20;
        }
        firstResult = (page - 1) * rows;
        maxResult = rows;
    }
}
