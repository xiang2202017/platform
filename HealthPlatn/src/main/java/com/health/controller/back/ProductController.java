package com.health.controller.back;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.service.system.ProductService;
import com.health.system.util.AppUtil;
import com.health.system.util.Const;
import com.health.system.util.CustomUtil;
import com.health.system.util.Jurisdiction;
import com.health.system.util.PageData;

@Controller
public class ProductController extends BaseController{

	String menuUrl = "/back/listProduct"; //菜单地址(权限用)
	@Resource(name="productService")
	private ProductService productService;
	
	
	/**
	 * 查询产品列表
	 */
	@RequestMapping(value="/back/listProduct")
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
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 显示产品详情
	 */
	@RequestMapping(value="/back/toProductView")
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
	
	/**
	 * 进入添加产品页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toProductAdd")
	public ModelAndView toAdd() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			List<PageData> typeList = productService.getProductTypeList();
			mv.setViewName("back/product/product_add");
			mv.addObject("typeList", typeList);
			mv.addObject("pg", pg);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 上传产品主照片
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/back/uploadMainImg", produces="text/html;chartset=utf-8")
//	@ResponseBody
//	public Object uploadNewsMainImg(HttpServletRequest request, @RequestParam(value = "uploadimg") MultipartFile file) throws IOException{  
//		String realPath = getRequest().getSession().getServletContext().getRealPath("/");
//		String saveDirectoryPath = realPath + Const.FILE_UPLOAD_TEMP;
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile mulfile = multipartRequest.getFile("uploadimg");
//		//MultipartFile uploadimg = (MultipartFile)getRequest().getAttribute("uploadimg");
//		//上传文件的原名(即上传前的文件名字)
//		String filename = null;
//		//如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
//		//如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
//		//上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
//			if(file.isEmpty()){
//				return "{'msg':'fail', 'reason':'文件不能为空'}";
//			}else{
//				filename = System.currentTimeMillis()+file.getOriginalFilename();
//				try{
//					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(saveDirectoryPath, filename));
//				}catch (IOException  e) {
//					e.printStackTrace();
//					return "{'msg':'fail', 'reason':'文件上传失败'}";
//				}
//			}
//		
//		return "{'msg':'success','path':'" + filename + "'}";
//    }
	
	/**
	 * 添加产品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/addProduct")
	public String addProduct() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			Integer typeId = Integer.parseInt(pg.getString("typeId"));
			String typeName = productService.getTypeName(typeId);
			pg.put("typeName", typeName);
			pg.put("sellNum", 0);
			productService.insertProduct(pg);
			
			pg.put("discription", pg.getString("editorValue"));
			pg.put("product_id", pg.get("id"));
			productService.insertProductDetail(pg);
			
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listProduct";
	}
	
	/**
	 * 进入修改产品页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toProductEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			mv.setViewName("back/product/product_edit");
			pd = this.getPageData();
			pd = productService.getProductDetailById(pd.getString("id"));
			List<PageData> typeList = productService.getProductTypeList();
			mv.addObject("typeList", typeList);
			mv.addObject("pd", pd);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 进入修改产品页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateProduct")
	public String updateProduct() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			Integer typeId = Integer.parseInt(pg.getString("typeId"));
			String typeName = productService.getTypeName(typeId);
			pg.put("typeName", typeName);
			pg.put("discription", pg.getString("editorValue"));
			productService.updateProduct(pg);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listProduct";
	}
	
	/**
	 * 删除产品
	 * @param out
	 */
	@RequestMapping(value="/back/deleteProduct")
	public void deleteProduct(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "dels")){
				productService.deleteProductById(Integer.parseInt(pd.get("id").toString()));
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 批量删除产品
	 */
	@RequestMapping(value="/back/deleteAllProduct")
	@ResponseBody
	public Object deleteAllProduct() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("News_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){productService.deleteAllProducts(ArrayNews_IDS);}
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	
}
