package com.health.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.FMenu;
import com.health.entity.system.News;
import com.health.entity.system.Page;
import com.health.service.system.FMenuService;
import com.health.service.system.NewsService;
import com.health.service.system.ProductService;
import com.health.system.util.Const;
import com.health.system.util.CustomUtil;
import com.health.system.util.PageData;
/** 
 * 类名称：MenuController
 * @version
 */
@Controller
public class FMenuController extends BaseController {

	@Resource(name="fmenuService")
	private FMenuService fmenuService;
	@Resource(name="newsService")
	private NewsService newsService;
	@Resource(name="productService")
	private ProductService productService;
	
	
	/**
	 * 跳转到首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/home")
	public ModelAndView gohome() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		try{
			@SuppressWarnings("unchecked")
			List<FMenu> fmenulist = (List<FMenu>)this.getRequest().getSession().getAttribute(Const.SESSION_FRONT_MENULIST);
			if(fmenulist == null){
				fmenulist = fmenuService.listAllMenu();
				
				//单独设置产品中心的子菜单
				for(FMenu menu : fmenulist){
					if(menu.getMenu_id() == Const.MENU_PRODUCT){
						List<PageData> typeList = productService.getProductTypeList();
						List<FMenu> sublist = new ArrayList<FMenu>();
						for(PageData type : typeList){
							FMenu submenu = convertTypeToMenu(type, menu.getMenu_url());
							sublist.add(submenu);
						}
						menu.setSubMenu(sublist);
						break;
					}
				}
				
				this.getRequest().getSession().setAttribute(Const.SESSION_FRONT_MENULIST, fmenulist);
			}
			
			List<News> comNews = newsService.getNewsForLen(0, 5, Const.NEWS_TYPE_COMPONY);
			List<News> healthNews = newsService .getNewsForLen(0, 8, Const.NEWS_TYPE_HEALTH);
			pd.put("comNews", comNews);
			pd.put("healthNews", healthNews);
			
			pd.put("pagePath", Const.PATH_HOME);
			mv.setViewName("front/index");
			mv.addObject("fpd", pd);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	/**
	 * 点击资讯菜单，跳转到资讯页，默认显示公司资讯
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web/newsList")
	public ModelAndView goNewsList(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			Integer menuid = Integer.parseInt(pd.getString("menuId"));
			FMenu menu = fmenuService.getMenuById(menuid);
			Integer parentId = menu.getParent_id() == 0 ? menu.getMenu_id() : menu.getParent_id();
			List<FMenu> pfmenulist = fmenuService.listSubMenuByParentId(parentId);
			
			pd.put("type", Const.NEWS_TYPE_COMPONY);
			page.setPd(pd);
			List<PageData> comNews = newsService.getDatalistPage(page);
			pd.put("newslist", comNews);
			
			pd.put("submenulist", pfmenulist);
			pd.put("pagePath", Const.PATH_NEWS);
			
			mv.setViewName("front/index");
			mv.addObject("fpd", pd);
			mv.addObject("title", "公司资讯");
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 返回公司资讯列表页面代码
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/compNews",method=RequestMethod.GET)
	public String getCompNews(Page page, ModelMap model) throws Exception{
		try{
			PageData pd = new PageData();
			pd.put("type", Const.NEWS_TYPE_COMPONY);
			page.setPd(pd);
			List<PageData> comNews = newsService.getDatalistPage(page);
			pd.put("newslist", comNews);
			model.addAttribute("fpd", pd);
			model.addAttribute("title", "公司资讯");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "front/news/newsList";
	}
	
	/**
	 * 返回健康资讯列表页面代码
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/healthNews",method=RequestMethod.GET)
	public String getHealthNews(Page page, ModelMap model) throws Exception{
		try{
			PageData pd = new PageData();
			pd.put("type", Const.NEWS_TYPE_HEALTH);
			page.setPd(pd);
			List<PageData> comNews = newsService.getDatalistPage(page);
			pd.put("newslist", comNews);
			model.addAttribute("fpd", pd);
			model.addAttribute("title", "健康资讯");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "front/news/newsList";
	}
	
	/**
	 * 获取资讯详情
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/newsDetail",method=RequestMethod.GET)
	public String getNewsDetail(ModelMap model) throws Exception{
		PageData pd = new PageData();
		String id = getRequest().getParameter("newsId");
		pd = newsService.getNewsDetailById(id);
		
		PageData nextPd = newsService.getNewsDetailByNextId(pd);
		PageData prePd = newsService.getNewsDetailByPreId(pd);
		
		String ctime = pd.getString("creatime");
		String timestr = (ctime.equals("") || ctime == null) ? pd.getString("editime") : ctime;
		String headstr = CustomUtil.getNewsHeader(pd.getString("title"), timestr, pd.get("clickNum")+"");
		model.addAttribute("content", headstr + pd.getString("content"));
		model.addAttribute("previousNews", prePd);
		model.addAttribute("nextNews", nextPd);
		return "front/news/newsDetail";
	}
	
	//============================================================
	//将产品种类转换成产品中心菜单的子菜单
	private FMenu convertTypeToMenu(PageData pd, String menurl){
		FMenu menu = new FMenu();
		menu.setMenu_name(pd.getString("name"));
		menu.setMenu_url(menurl + "?type="+ pd.getString("id"));
		return menu;
	}
	
	/**
	 * 显示菜单列表
	 * @param model
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView list()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		try{
//			List<FMenu> menuList = fmenuService.listAllParentMenu();
//			mv.addObject("menuList", menuList);
//			mv.setViewName("system/FMenu/menu_list");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		
//		return mv;
//	}
//	
//	/**
//	 * 请求新增菜单页面
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/toAdd")
//	public ModelAndView toAdd()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		try{
//			List<FMenu> menuList = fmenuService.listAllParentMenu();
//			mv.addObject("menuList", menuList);
//			mv.setViewName("system/FMenu/menu_add");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 保存菜单信息
//	 * @param FMenu
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/add")
//	public ModelAndView add(FMenu fmenu)throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			
//			
//			Integer PARENT_ID = fmenu.getParent_id();
//			if(!"0".equals(PARENT_ID)){
//				//处理菜单类型====
//				pd.put("MENU_ID",PARENT_ID);
//				pd = fmenuService.getMenuById(pd);
//				fmenu.setMenu_type(Integer.parseInt(pd.getString("MENU_TYPE")));
//				//处理菜单类型====
//			}
//			fmenuService.saveMenu(fmenu);
//			mv.addObject("msg","success");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.addObject("msg","failed");
//		}
//		
//		mv.setViewName("save_result");
//		return mv;
//		
//	}
//	
//	/**
//	 * 请求编辑菜单页面
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value="/toEdit")
//	public ModelAndView toEdit(String MENU_ID)throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("MENU_ID",MENU_ID);
//			pd = fmenuService.getMenuById(pd);
//			List<FMenu> menuList = fmenuService.listAllParentMenu();
//			mv.addObject("menuList", menuList);
//			mv.addObject("pd", pd);
//			mv.setViewName("system/FMenu/menu_edit");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 请求编辑菜单图标页面
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value="/toEditicon")
//	public ModelAndView toEditicon(String MENU_ID)throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("MENU_ID",MENU_ID);
//			mv.addObject("pd", pd);
//			mv.setViewName("system/FMenu/menu_icon");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 保存菜单图标 (顶部菜单)
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value="/editicon")
//	public ModelAndView editicon()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd = fmenuService.editicon(pd);
//			mv.addObject("msg","success");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.addObject("msg","failed");
//		}
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**
//	 * 保存编辑
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value="/edit")
//	public ModelAndView edit()throws Exception{
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			
//			String PARENT_ID = pd.getString("PARENT_ID");
//			if(null == PARENT_ID || "".equals(PARENT_ID)){
//				PARENT_ID = "0";
//				pd.put("PARENT_ID", PARENT_ID);
//			}
//			
//			if("0".equals(PARENT_ID)){
//				//处理菜单类型====
//				fmenuService.editType(pd);
//				//处理菜单类型====
//			}
//			
//			pd = fmenuService.edit(pd);
//			mv.addObject("msg","success");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.addObject("msg","failed");
//		}
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**
//	 * 获取当前菜单的所有子菜单
//	 * @param menuId
//	 * @param response
//	 */
//	@RequestMapping(value="/sub")
//	public void getSub(@RequestParam String MENU_ID,HttpServletResponse response)throws Exception{
//		try {
//			List<FMenu> subMenu = fmenuService.listSubMenuByParentId(Integer.parseInt(MENU_ID));
//			JSONArray arr = JSONArray.fromObject(subMenu);
//			PrintWriter out;
//			
//			response.setCharacterEncoding("utf-8");
//			out = response.getWriter();
//			String json = arr.toString();
//			out.write(json);
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//	}
//	
//	/**
//	 * 删除菜单
//	 * @param menuId
//	 * @param out
//	 */
//	@RequestMapping(value="/del")
//	public void delete(@RequestParam String MENU_ID,PrintWriter out)throws Exception{
//		
//		try{
//			fmenuService.deleteMenuById(Integer.parseInt(MENU_ID));
//			out.write("success");
//			out.flush();
//			out.close();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		
//	}
}
