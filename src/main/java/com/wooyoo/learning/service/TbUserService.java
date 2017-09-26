package com.wooyoo.learning.service;

import com.wooyoo.learning.dao.domain.TbUser;
import com.wooyoo.learning.dao.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tbUserService")
public class TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    public TbUser selectByPrimaryKey(Long id){
        return tbUserMapper.selectByPrimaryKey(id);
    }

    public TbUser selectByName(String userName){
        return tbUserMapper.selectByName(userName);
    }
}
