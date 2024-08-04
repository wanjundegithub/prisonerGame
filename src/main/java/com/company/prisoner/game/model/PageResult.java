package com.company.prisoner.game.model;

import lombok.Data;

import java.util.List;

/**
 * @author user
 */
@Data
public class PageResult<T> {

    private int total;

    private int totalPage;

    private int page;

    private int pageSize;

    private List<T> data;

    public static <T> PageResult<T> buildPageResult(List<T> data, int total, int pageSize, int page){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        int totalPage = calculateTotalPages(total, pageSize);
        pageResult.setTotalPage(totalPage);
        pageResult.setPage(page);
        pageResult.setPageSize(pageSize);
        pageResult.setData(data);
        return pageResult;
    }


    public static int calculateTotalPages(int total, int pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        int totalPages = total / pageSize;
        if (total % pageSize!= 0) {
            totalPages++;
        }
        return totalPages;
    }

}
