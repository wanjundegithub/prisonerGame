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
     * 获取总数
     * @param userParam
     * @return
     */
    int selectTotal(UserParam userParam);

    /**
     * 根据条件查询用户列表
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

    /**
     * 批量更新用户
     * @param userList
     * @return
     */
    int batchUpdateUserList(@Param("userList") List<User> userList);


    /**
     * 保存用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    int deleteUserById(User user);

    /**
     * 批量删除用户
     * @param idList
     * @return
     */
    int deleteUserList(@Param("idList") List<Integer> idList);

    /**
     * 保存用户列表
     * @param users
     * @return
     */
    int insertUsers(@Param("users")List<User> users);

}
