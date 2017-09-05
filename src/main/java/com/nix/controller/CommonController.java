package com.nix.controller;

import com.nix.controller.base.BaseController;
import com.nix.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/nix")
public class CommonController extends BaseController{
    @Autowired
    private CommonService commonService;

    /**
     * 上传文件
     * @param file xls文件;
     * @return 返回xls的所有类标题 并返回当前计算单元的唯一id
     * */
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map<String,Object> upload(@RequestParam(value = "file", required = false) MultipartFile file){
        Map<String,Object> map = new HashMap<>();
        if (!file.getOriginalFilename().matches(".*xls") && !file.getOriginalFilename().matches(".*xlsx")){
            map.put("status",FAIL);
            map.put("msg","不支持该后缀的excel表");
            return map;
        }
        map.put("status",SUCCESS);
        map.putAll(commonService.getParamMsg(file));
        return map;
    }

    /**
     *根据筛选条件选出数据并计算
     * */
    @ResponseBody
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    public Map<String,Object> result(@RequestParam(value = "id")Integer id,@RequestParam(value = "credits")Integer credits,
                      @RequestParam(value = "results")Integer results,@RequestParam(value = "courses") Integer courses,
                      @RequestParam(value = "columns")String[] columns,@RequestParam(value = "formula") String formula){
        if (id == null || results == null || results == null || columns == null || courses == null) return null;
        Map<String,Object> map = new HashMap<>();
        if (!commonService.inspectionFormula(formula)){
            map.put("status",FAIL);
            map.put("msg","公式格式错误");
            return map;
        }
        map.put("status",SUCCESS);
        map.putAll(commonService.result(String.valueOf(id),credits,results,courses,columns,formula));
        return map;
    }
}
