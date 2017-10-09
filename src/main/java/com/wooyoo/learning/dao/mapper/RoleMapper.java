package com.wooyoo.learning.dao.mapper;

import com.wooyoo.learning.dao.domain.Role;
import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    public Role selectByName(@Param("name") String name);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}