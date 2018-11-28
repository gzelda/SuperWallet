package com.superwallet.controller;

import com.superwallet.pojo.Items;
import com.superwallet.pojo.QueryVo;
import com.superwallet.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ItemsController {

    @Autowired
    private ItemsService itemService;

    /**
     * 显示商品列表
     *
     * @return
     */
    @RequestMapping("/itemList")
    public ModelAndView queryItemList() {
        // 获取商品数据
        List<Items> list = this.itemService.queryItemList();

        ModelAndView modelAndView = new ModelAndView();
        // 把商品数据放到模型中
        modelAndView.addObject("list", list);
        // 设置逻辑视图
        modelAndView.setViewName("itemList");

        return modelAndView;
    }

    /**
     * 根据id查询商品
     *
     * param request
     * return
     */
    /*
    //老版写法
    @RequestMapping("/itemEdit")
    public String queryItemById(HttpServletRequest request, Model model) {
        // 从request中获取请求参数
        String strId = request.getParameter("id");
        Integer id = Integer.valueOf(strId);
        System.out.println(id);
        // 根据id查询商品数据
        Items item = this.itemService.queryItemById(id);
        // 把结果传递给页面
        //ModelAndView modelAndView = new ModelAndView();
        // 把商品数据放在模型中
        model.addAttribute("item", item);
        // 设置逻辑视图
        //modelAndView.setViewName("itemEdit");

        return "itemEdit";
    }
    */
    //请求的参数名称和处理器形参名称一致时会将请求参数与形参进行绑定
    //新版id类型不需要转换
    //model对象不用创建
    @RequestMapping("/itemEdit")
    public String queryItemById(int id, ModelMap model) {
        // 根据id查询商品数据
        Items item = this.itemService.queryItemById(id);

        // 把商品数据放在模型中
        model.addAttribute("item", item);

        return "itemEdit";
    }

    /**
     * 更新商品,绑定pojo类型
     *
     * param item
     * param   model
     * return
     */
    @RequestMapping("/updateItem")
    public String updateItem(Items item) {
        // 调用服务更新商品
        this.itemService.updateItemById(item);

        // 返回逻辑视图
        return "success";
    }

    // 绑定包装数据类型
    @RequestMapping("/queryItem")
    public String queryItem(QueryVo queryVo) {
        System.out.println(queryVo.getItem().getId());
        System.out.println(queryVo.getItem().getName());

        return "success";
    }

}
