package com.company.prisoner.game.param;

import lombok.Data;

@Data
public class GroupParam {

    private Integer gameId;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 当前页数 从1开始
     */
    private Integer curPage;
}
