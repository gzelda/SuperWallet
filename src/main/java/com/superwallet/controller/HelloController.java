package com.superwallet.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloController {


    @RequestMapping(value= "hello",method = RequestMethod.POST)
    public void hello(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //传统方法传response
        response.getWriter().print("{\"abcd\":123}");
    }

    @RequestMapping(value= "test")
    @ResponseBody
    public String testJson(@RequestBody String body) {
        //body里面有所有requestBody的信息
        System.out.println(body);
        //通过ali Json库解析body，注意多重JSON格式可能会有问题
        JSONObject object = JSON.parseObject(body);
        System.out.println(object.get("person"));
        //通过@ResponseBody注解直接return字符串
        return object.getString("person");
    }

}
