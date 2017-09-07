package com.vote.service.impl;

import com.nix.web.util.DataSecurityUtil;
import com.vote.common.Const;
import com.vote.service.ApiService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public class ApiServiceImpl implements ApiService{

    /**
     * 校验投票系统的权限值是否正确
     * @param md5
     *  前端输入权限值得md5值
     * @return
     *  返回权限值是否等于系统common.Const.VOTE_PERMISSION_VALUE设定的值
     *
     * */
    @Override
    public boolean checkMd5(String md5) {
        return DataSecurityUtil.stringMD5(Const.VOTE_PERMISSION_VALUE).toLowerCase().equals(md5.toLowerCase());
    }

    /**
     * 初始化系统静态属性
     * @param type
     *  投票对象的类型
     * @param keys
     *  投票对象数组
     * @param title
     *  本次投票标题
     * */
    @Override
    public void saveSetting(String type, String[] keys, String title) {
        Const.initVote(keys);
        Const.setTitle(title);
        Const.setType(type);
    }

    @Override
    public void vote(HttpServletRequest request,String key) {
        Const.addOneTicket(key);
    }
}
