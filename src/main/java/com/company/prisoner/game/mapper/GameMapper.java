package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Game;
import com.company.prisoner.game.param.GameParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user
 */
@Repository
public interface GameMapper {


    List<Game> getGameList(GameParam param);


    Integer insertGame(Game game);


    Integer updateGame(Game game);
}
