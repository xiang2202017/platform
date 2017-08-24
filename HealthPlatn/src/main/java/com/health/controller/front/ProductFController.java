package com.health.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.ProductService;
import com.health.system.util.PageData;

@Controller
public class ProductFController extends BaseController{

	@Resource(name="productService")
	private ProductService productService;
	
	
	/**
	 * 查询产品列表
	 */
	@RequestMapping(value="/web/listProduct")
	public ModelAndView listProduct(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String typeName = pd.getString("typeName");
		String keywords = pd.getString("keywords");
		String name = pd.getString("name");
		String typeId = pd.getString("typeId");
		
		if(typeId != null && !"".equals(typeId) && "0".equals(typeId)){
			pd.put("typeId", "");
		}
		
		if(null != keywords && !"".equals(keywords)){
			keywords = keywords.trim();
			pd.put("keywords", keywords);
		}
		
		if(null != name && !"".equals(name)){
			name = name.trim();
			pd.put("name", name);
		}
		
		if(null != typeName && !"".equals(typeName)){
			typeName = typeName.trim();
			pd.put("typeName", typeName);
		}
		
		page.setPd(pd);
		List<PageData>	productList = productService.getDatalistPage(page);			//列出产品列表
		List<PageData> typeList = productService.getProductTypeList();
		
		mv.setViewName("back/product/product_list");
		mv.addObject("productList", productList);
		mv.addObject("typeList", typeList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 显示产品详情
	 */
	@RequestMapping(value="/web/toProductView")
	public ModelAndView toProductView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String id = getRequest().getParameter("id");
		pd = productService.getProductDetailById(id);
		
		mv.addObject("pd", pd);
		mv.setViewName("back/product/product_view");
		
		return mv;
	}
	
	
	
	
}
