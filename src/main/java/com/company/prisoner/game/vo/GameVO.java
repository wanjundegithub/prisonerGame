package com.company.prisoner.game.vo;

import com.company.prisoner.game.model.Game;
import com.company.prisoner.game.model.User;
import lombok.Data;

/**
 * @author user
 */
@Data
public class GameVO  extends Game {

    /**
     * 创建游戏后未被分组的用户
     */
    private User unGroupUser;
}
