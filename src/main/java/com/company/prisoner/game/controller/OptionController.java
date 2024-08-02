package com.company.prisoner.game.controller;

import com.alibaba.fastjson.JSON;
import com.company.prisoner.game.model.Option;
import com.company.prisoner.game.model.Result;
import com.company.prisoner.game.param.OptionParam;
import com.company.prisoner.game.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prisoner/game/option")
@Slf4j
public class OptionController {

    @Autowired
    private OptionService optionService;

    @PostMapping("/submit")
    public Result submit(@RequestBody OptionParam param){
        if(param.getGameId()==null||param.getGroupId()==null||param.getUserId()==null||
                StringUtils.isEmpty(param.getSelectOptionValue())){
            log.error("存在空数据,{}", JSON.toJSONString(param));
            throw new RuntimeException("存在空数据");
        }
        return optionService.submitOption(param);
    }

    @PostMapping("/getAllOptions")
    public List<Option> getAllOptions(@RequestBody OptionParam param){
        if(param.getGameId()==null){
            log.error("存在空数据,{}", JSON.toJSONString(param));
            throw new RuntimeException("存在空数据");
        }
        return optionService.getAllOptions(param);
    }
}
