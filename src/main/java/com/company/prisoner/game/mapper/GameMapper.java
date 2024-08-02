package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Game;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user
 */
@Repository
public interface GameMapper {


    List<Game> getGame(@Param("aliveFlag") Integer aliveFlag, @Param("gameId") Integer gameId);


    Integer insertGame(Game game);


    Integer updateGame(Game game);
}
