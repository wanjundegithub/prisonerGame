package com.company.prisoner.game.mapper;

import com.company.prisoner.game.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author user
 */
@Repository
public interface UserMapper {

    /**
     * 根据用户名和密码查询用户
     * @param userName
     * @param password
     * @return
     */
    User selectUser(@Param("userName") String userName, @Param("password") String password);

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
