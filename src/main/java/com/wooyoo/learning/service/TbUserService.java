package com.wooyoo.learning.service;

import com.wooyoo.learning.annotation.ReadDataSource;
import com.wooyoo.learning.annotation.WriteDataSource;
import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.mapper.TbUserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface TbUserService {


    public TbUser selectByPrimaryKey(Long id);

    public int addUser(TbUser user);

    public TbUser selectByName(String userName);

    public TbUser getLoginUser();
}
