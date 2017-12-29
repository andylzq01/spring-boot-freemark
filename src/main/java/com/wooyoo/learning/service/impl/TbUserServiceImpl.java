package com.wooyoo.learning.service.impl;

import com.wooyoo.learning.annotation.ReadDataSource;
import com.wooyoo.learning.annotation.WriteDataSource;
import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.mapper.TbUserMapper;
import com.wooyoo.learning.service.TbUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * All rights Reserved, Designed ByBeLLE
 * Copyright:   Copyright(C) 2014-2015
 * Company:     Wonhigh.
 * author:      laizeqi
 * Createdate:  ${date}${time}
 * <p>
 * Modification  History:
 * Date         Author             What
 * ------------------------------------------
 * ${date}     	laizeqi
 */

@Service("tbUserService")
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    @ReadDataSource
    public TbUser selectByPrimaryKey(Long id) {
        return tbUserMapper.selectByPrimaryKey(id);
    }

    @Override
    @WriteDataSource
    public int addUser(TbUser user) {
        return tbUserMapper.insert(user);
    }

    @Override
    @ReadDataSource
    public TbUser selectByName(String userName) {
        return tbUserMapper.selectByName(userName);
    }

    @Override
    @ReadDataSource
    public TbUser getLoginUser() {
        String number = SecurityUtils.getSubject().getPrincipal().toString();
        System.out.println("login user is:" + number);
        return this.tbUserMapper.selectByName(number);
    }
}
