package com.vote.controller;

import com.nix.controller.base.BaseController;
import com.nix.util.Util;
import com.nix.util.log.LogKit;
import com.vote.common.Const;
import com.vote.service.ApiService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/vote/system")
public class SystemController extends BaseController{
    @Autowired
    private ApiService apiService;

    /**
     * 设置投票系统的属性
     * */
    @ResponseBody
    @RequestMapping(value = "/setting",method = RequestMethod.POST)
    public Map<String,Object> setting(@RequestParam(value = "md5",defaultValue = "") String md5, @RequestParam(value = "type",defaultValue = "队伍")String type,
                                      @RequestParam(value = "keys",defaultValue = "{}")String[] keys, @RequestParam(value = "title",defaultValue = "")String title){
        Map<String,Object> map = new HashMap<>();
        if (md5 == null || !apiService.checkMd5(md5)){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"权限码错误");
            return map;
        }
        if (title.isEmpty()){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"请输入标题");
            return map;
        }
        if (keys.length == 0){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"投票对象组不能为空");
            return map;
        }
        if (!Const.getOpenSetting()){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"系统运行中，无法初始化");
            return map;
        }
        apiService.saveSetting(type,keys,title);
        map.put(STATUS,SUCCESS);
        Const.setOpenSetting(false);
        return map;
    }

    /**
     * 投票
     * */
    @ResponseBody
    @RequestMapping(value = "/vote",method = RequestMethod.GET)
    public Map<String,Object> vote(HttpServletRequest request,@RequestParam(value = "key",defaultValue = "") String key){
        Map<String,Object> map = new HashMap<>();
        if (key.isEmpty()){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"请选择投票项");
            return map;
        }
        if (!Const.isVoteOpen()){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"投票未开始");
            return map;
        }
        apiService.vote(request,key);
        map.put(STATUS,SUCCESS);
        LogKit.info(this.getClass(), Util.getIpAddress(request) + "给" +key + "投一票" );
        return map;
    }

    /**
     * 设置是否开启投票
     * */
    @ResponseBody
    @RequestMapping(value = "/is_vote",method = RequestMethod.GET)
    public Map<String,Object> stopVote(@RequestParam(value = "md5",defaultValue = "") String md5,@RequestParam(value = "vote")Boolean vote){
        Map<String,Object> map = new HashMap<>();
        if (md5.isEmpty() || !apiService.checkMd5(md5)){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"权限码错误");
            return map;
        }
        Const.setVoteOpen(vote);
        map.put(STATUS,SUCCESS);
        return map;
    }

    /**
     * 本次投票结束
     * */
    @ResponseBody
    @RequestMapping(value = "/stop",method = RequestMethod.GET)
    public Map<String,Object> closeVote(@RequestParam(value = "md5",defaultValue = "") String md5){
        Map<String,Object> map = new HashMap<>();
        if (md5 == null || !apiService.checkMd5(md5)){
            map.put(STATUS,FAIL);
            map.put(MESSAGE,"权限码错误");
            return map;
        }
        Const.setOpenSetting(true);
        map.put(STATUS,SUCCESS);
        return map;
    }
}
