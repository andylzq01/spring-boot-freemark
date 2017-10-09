package com.wooyoo.learning.service;

import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.mapper.TbUserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tbUserService")
public class TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    public TbUser selectByPrimaryKey(Long id){
        return tbUserMapper.selectByPrimaryKey(id);
    }

    public int addUser(TbUser user){
        return  tbUserMapper.insert(user);
    }

    public TbUser selectByName(String userName){
        return tbUserMapper.selectByName(userName);
    }

    public TbUser getLoginUser() {
        String number = SecurityUtils.getSubject().getPrincipal().toString();
        System.out.println("login user is:"+ number);
        return  this.tbUserMapper.selectByName(number);
    }
}
