package com.health.controller.back;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.News;
import com.health.entity.system.Page;
import com.health.entity.system.Role;
import com.health.entity.system.User;
import com.health.service.system.NewsService;
import com.health.system.util.AppUtil;
import com.health.system.util.Const;
import com.health.system.util.CustomUtil;
import com.health.system.util.Jurisdiction;
import com.health.system.util.PageData;
import com.health.system.util.StringUtil;

@Controller
public class NewsController extends BaseController{

	String menuUrl = "toNewsList.do"; //菜单地址(权限用)
	@Resource(name="newsService")
	private NewsService newsService;
	
	/**
	 * 进入资讯列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toNewsList")
	public ModelAndView toList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pg = new PageData();
		
		page.setPd(pg);
		List<PageData> newsList = newsService.getNewsList(page);
		mv.setViewName("back/news/news_list");
		mv.addObject("newsList", newsList);
		mv.addObject("pg", pg);
		
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 查询资讯列表
	 */
	@RequestMapping(value="/back/listNews")
	public ModelAndView listNews(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String title = pd.getString("title");
		String keywords = pd.getString("keywords");
		String author = pd.getString("author");
		String type = pd.getString("type");
		
		if(type != null && !"".equals(type) && "0".equals(type)){
			pd.put("type", "");
		}
		
		if(null != keywords && !"".equals(keywords)){
			keywords = keywords.trim();
			pd.put("keywords", keywords);
		}
		
		if(null != author && !"".equals(author)){
			author = author.trim();
			pd.put("author", author);
		}
		
		if(null != title && !"".equals(title)){
			title = title.trim();
			pd.put("title", title);
		}
		
		String fromTime = pd.getString("fromTime");
		String toTime = pd.getString("toTime");
		
		if(fromTime != null && !"".equals(fromTime)){
			fromTime = fromTime+" 00:00:00";
			pd.put("fromTime", fromTime);
		}
		if(toTime != null && !"".equals(toTime)){
			toTime = toTime+" 00:00:00";
			pd.put("toTime", toTime);
		} 
		
		page.setPd(pd);
		List<PageData>	newsList = newsService.getDatalistPage(page);			//列出资讯列表
		
		mv.setViewName("back/news/news_list");
		mv.addObject("newsList", newsList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 显示资讯详情
	 */
	@RequestMapping(value="/back/toNewsView")
	public ModelAndView toNewsView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String id = getRequest().getParameter("id");
		pd = newsService.getNewsDetailById(id);
		
		String ctime = pd.getString("creatime");
		String timestr = (ctime.equals("") || ctime == null) ? pd.getString("editime") : ctime;
		String imgpath = pd.getString("imgPath");
		String headstr = CustomUtil.getNewsHeader(pd.getString("title"), imgpath, timestr, pd.get("clickNum")+"");
		pd.put("content", headstr + pd.getString("content"));
		mv.addObject("pd", pd);
		mv.setViewName("back/news/news_view");
		
		return mv;
	}
	
	/**
	 * 进入添加资讯页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toNewsAdd")
	public ModelAndView toAdd() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			mv.setViewName("back/news/news_add");
			mv.addObject("pg", pg);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 上传资讯主照片
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/uploadMainImg", produces="text/html;chartset=utf-8")
	@ResponseBody
	public Object uploadNewsMainImg(HttpServletRequest request, @RequestParam(value = "uploadimg") CommonsMultipartFile  uploadimg) throws IOException{  
		String realPath = getRequest().getSession().getServletContext().getRealPath("/");
		String saveDirectoryPath = realPath + Const.FILE_UPLOAD_TEMP;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mulfile = multipartRequest.getFile("uploadimg");
		//MultipartFile uploadimg = (MultipartFile)getRequest().getAttribute("uploadimg");
		//上传文件的原名(即上传前的文件名字)
		String filename = null;
		//如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		//如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		//上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
			if(uploadimg.isEmpty()){
				return "{'msg':'fail', 'reason':'文件不能为空'}";
			}else{
				filename = System.currentTimeMillis()+uploadimg.getOriginalFilename();
				try{
					FileUtils.copyInputStreamToFile(uploadimg.getInputStream(), new File(saveDirectoryPath, filename));
				}catch (IOException  e) {
					e.printStackTrace();
					return "{'msg':'fail', 'reason':'文件上传失败'}";
				}
			}
		String path = "/HealthPlatn/uploadFiles/uploadImgs/temp/"+ filename;
		return "{'msg':'success','path':'" + path + "'}";
    }
	
	/**
	 * 添加资讯
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/addNews")
	public String addNews() throws Exception{
		//ModelAndView mv = new ModelAndView();
		
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			logger.debug(pg);
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			
			pg.put("creator", user.getNAME());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			pg.put("creatime", df.format(new Date()));
			pg.put("clickNum", 0);
			pg.put("content", pg.getString("editorValue"));
			pg.put("imgPath", pg.getString("mainImgPath"));
			pg.put("remark", StringUtil.getStrScope(pg.getString("contentTxt"), Const.STR_LENGTH));
			newsService.insertNews(pg);
			
			pg.put("news_id", pg.get("id"));
			newsService.insertNewsDetail(pg);
			
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listNews";
	}
	
	/**
	 * 进入修改资讯页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toNewsEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			mv.setViewName("back/news/news_edit");
			pd = this.getPageData();
			pd = newsService.getNewsDetailById(pd.getString("id"));
			mv.addObject("pd", pd);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 进入修改资讯页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateNews")
	public String newsEdit() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			pg.put("editime", df.format(new Date()));
			pg.put("editor", user.getNAME());
			pg.put("imgPath", pg.getString("mainImgPath"));
			pg.put("content", pg.getString("editorValue"));
			pg.put("remark", StringUtil.getStrScope(pg.getString("contentTxt"), Const.STR_LENGTH));
			newsService.updateNews(pg);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listNews";
	}
	
	/**
	 * 删除资讯
	 * @param out
	 */
	@RequestMapping(value="/back/deleteNews")
	public void deleteU(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "dels")){
				newsService.deleteNewsById(Integer.parseInt(pd.get("id").toString()));
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 批量删除资讯
	 */
	@RequestMapping(value="/back/deleteAllNews")
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("News_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){newsService.deleteAllNews(ArrayNews_IDS);}
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
