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
			
			pd = this.getPageData();
			String intype = pd.getString("menuType");	//入口类型为地址栏还是菜单
			
			List<News> comNews = newsService.getNewsForLen(0, 5, Const.NEWS_TYPE_COMPONY);
			List<News> healthNews = newsService .getNewsForLen(0, 8, Const.NEWS_TYPE_HEALTH);
			pd.put("comNews", comNews);
			pd.put("healthNews", healthNews);
			
			@SuppressWarnings("unchecked")
			List<FMenu> fmenulist = (List<FMenu>)this.getRequest().getSession().getAttribute(Const.SESSION_FRONT_MENULIST);
			if(fmenulist == null || intype == null || intype.length() == 0){
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
				
				pd.put("pagePath", Const.PATH_HOME);
				mv.setViewName("front/index");
			}else{
				mv.setViewName("front/home");
			}
			
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
			List<FMenu> pfmenulist = new ArrayList<FMenu>();
			Integer menuid = Integer.parseInt(pd.getString("menuId"));
			FMenu menu = fmenuService.getMenuById(menuid);
			Integer parentId = menu.getParent_id() == 0 ? menu.getMenu_id() : menu.getParent_id();
			pfmenulist = fmenuService.listSubMenuByParentId(parentId);
			
			Integer type = pd.getString("type") == null ? Const.NEWS_TYPE_COMPONY : Integer.parseInt(pd.getString("type"));
			pd.put("type", type);
			page.setPd(pd);
			List<PageData> comNews = newsService.getDatalistPage(page);
			pd.put("newslist", comNews);
			
			pd.put("submenulist", pfmenulist);
			//pd.put("pagePath", Const.PATH_NEWS);
			
			String title = type == Const.NEWS_TYPE_COMPONY ? "公司资讯" : "健康资讯";
			mv.setViewName("front/news/news_main");
			mv.addObject("fpd", pd);
			mv.addObject("title", title);
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
	@RequestMapping(value="/web/compNews",method=RequestMethod.POST)
	public String goCompNews(Page page, ModelMap model) throws Exception{
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
		return "front/news/news_list";
	}
	
	/**
	 * 返回健康资讯列表页面代码
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/healthNews",method=RequestMethod.POST)
	public String goHealthNews(Page page, ModelMap model) throws Exception{
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
		return "front/news/news_list";
	}
	
	/**
	 * 获取资讯详情
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/web/newsDetail",method=RequestMethod.POST)
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
		return "front/news/news_view";
	}
	
	//============================================================
	//将产品种类转换成产品中心菜单的子菜单
	private FMenu convertTypeToMenu(PageData pd, String menurl){
		FMenu menu = new FMenu();
		menu.setMenu_name(pd.getString("name"));
		menu.setMenu_url(menurl + "?type="+ pd.get("id").toString());
		return menu;
	}
	
}
