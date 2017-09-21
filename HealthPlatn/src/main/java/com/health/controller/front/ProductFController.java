package com.health.controller.front;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 * 跳转到产品页面
	 */
	@RequestMapping(value="/web/listProduct",method=RequestMethod.POST)
	public String listProduct(Page page, ModelMap map)throws Exception{
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String typeName = pd.getString("typeName");
		String keywords = pd.getString("keywords");
		String name = pd.getString("name");
		String typeId = pd.getString("type");
		
		List<PageData> typeList = productService.getProductTypeList();
		
		if(typeId != null && !"".equals(typeId) && !"0".equals(typeId)){
			pd.put("typeId", typeId);
		}else{
			PageData type1 = typeList.get(0);
			pd.put("typeId", type1.get("id").toString());
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
		
		map.put("productList", productList);
		map.put("typeList", typeList);
		map.put("pd", pd);
		return "front/product/product_main";
	}
	
	/**
	 * 查询产品列表
	 */
	@RequestMapping(value="/web/getProductList",method=RequestMethod.POST)
	public String getProductList(Page page, ModelMap map)throws Exception{
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String typeName = pd.getString("typeName");
		String keywords = pd.getString("keywords");
		String name = pd.getString("name");
		String typeId = pd.getString("type");
		
		if(typeId != null && !"".equals(typeId) && !"0".equals(typeId)){
			pd.put("typeId", typeId);
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
		
//		mv.setViewName("back/product/product_list");
//		mv.addObject("productList", productList);
//		mv.addObject("typeList", typeList);
//		mv.addObject("pd", pd);
		
		map.put("productList", productList);
		//map.put("typeList", typeList);
		map.put("pd", pd);
		return "front/product/product_list";
	}
	
	/**
	 * 显示产品详情
	 */
	@RequestMapping(value="/web/toProductView", method=RequestMethod.POST)
	public String toProductView(ModelMap mv)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String id = getRequest().getParameter("productId");
		pd = productService.getProductDetailById(id);
		
		mv.put("pd", pd);
		
		return "front/product/product_view";
	}
	
	
	
	
}
