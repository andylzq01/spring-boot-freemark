package com.wooyoo.learning.service;

import com.wooyoo.learning.annotation.ReadDataSource;
import com.wooyoo.learning.annotation.WriteDataSource;
import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.mapper.TbUserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tbUserService")
public class TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @ReadDataSource
    public TbUser selectByPrimaryKey(Long id){
        return tbUserMapper.selectByPrimaryKey(id);
    }

    @WriteDataSource
    public int addUser(TbUser user){
        return  tbUserMapper.insert(user);
    }
    @ReadDataSource
    public TbUser selectByName(String userName){
        return tbUserMapper.selectByName(userName);
    }

    @ReadDataSource
    public TbUser getLoginUser() {
        String number = SecurityUtils.getSubject().getPrincipal().toString();
        System.out.println("login user is:"+ number);
        return  this.tbUserMapper.selectByName(number);
    }
}
