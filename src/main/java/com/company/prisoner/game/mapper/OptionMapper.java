package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.Option;
import com.company.prisoner.game.param.OptionParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user
 */
@Repository
public interface OptionMapper {

    List<Option> getOptionList(OptionParam optionParam);

    Integer insertOption(Option option);

    Integer batchInsertOption(List<Option> optionList);
}
