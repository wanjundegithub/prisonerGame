package com.company.prisoner.game.param;

import lombok.Data;

@Data
public class GroupParam {

    private Integer gameId;

    private Integer id;

    private Integer userId;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 当前页数 从1开始
     */
    private Integer curPage;
}
