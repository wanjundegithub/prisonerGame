package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.User;
import com.company.prisoner.game.param.UserParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user
 */
@Repository
public interface UserMapper {

    /**
     * 获取所有用户数据
     * @return
     */
    List<User> getAllUsers();

    /**
     * 根据用户名和密码查询用户
     * @param userParam
     * @return
     */
    List<User> selectUserList(UserParam userParam);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User selectUserById(@Param("id") Integer id);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(User user);
}
