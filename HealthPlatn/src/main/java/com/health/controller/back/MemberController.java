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
import com.health.service.system.MemberService;
import com.health.system.util.AppUtil;
import com.health.system.util.Const;
import com.health.system.util.Jurisdiction;
import com.health.system.util.PageData;

@Controller
public class MemberController extends BaseController{

	String menuUrl = "/back/listMember"; //菜单地址(权限用)
	@Resource(name="memberService")
	private MemberService memberService;
	
	
	/**
	 * 查询用户列表
	 */
	@RequestMapping(value="/back/listMember")
	public ModelAndView listMember(Page page)throws Exception{
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
		List<PageData>	MemberList = memberService.getDatalistPage(page);			//列出用户列表
		List<PageData> typeList = memberService.getMemberTypeList();
		
		mv.setViewName("back/member/member_list");
		mv.addObject("memberList", MemberList);
		mv.addObject("typeList", typeList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 显示用户详情
	 */
	@RequestMapping(value="/back/toMemberView")
	public ModelAndView toMemberView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String id = getRequest().getParameter("id");
		pd = memberService.getMemberById(id);
		
		mv.addObject("pd", pd);
		mv.setViewName("back/member/member_view");
		
		return mv;
	}
	
	/**
	 * 进入添加用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toMemberAdd")
	public ModelAndView toAdd() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			List<PageData> typeList = memberService.getMemberTypeList();
			mv.setViewName("back/member/member_add");
			mv.addObject("typeList", typeList);
			mv.addObject("pg", pg);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	

	
	/**
	 * 添加用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/addMember")
	public String addMember() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			Integer typeId = Integer.parseInt(pg.getString("typeId"));
			String typeName = memberService.getTypeName(typeId);
			pg.put("typeName", typeName);
			pg.put("sellNum", 0);
			memberService.insertMember(pg);
			
			pg.put("discription", pg.getString("editorValue"));
			pg.put("Member_id", pg.get("id"));
			memberService.insertMember(pg);
			
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listMember";
	}
	
	/**
	 * 进入修改用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toMemberEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			mv.setViewName("back/Member/Member_edit");
			pd = this.getPageData();
			pd = memberService.getMemberById(pd.getString("id"));
			List<PageData> typeList = memberService.getMemberTypeList();
			mv.addObject("typeList", typeList);
			mv.addObject("pd", pd);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 进入修改用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateMember")
	public String updateMember() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			Integer typeId = Integer.parseInt(pg.getString("typeId"));
			String typeName = memberService.getTypeName(typeId);
			pg.put("typeName", typeName);
			pg.put("discription", pg.getString("editorValue"));
			memberService.updateMember(pg);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listMember";
	}
	
	/**
	 * 删除用户
	 * @param out
	 */
	@RequestMapping(value="/back/deleteMember")
	public void deleteMember(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "dels")){
				memberService.deleteMemberById(Integer.parseInt(pd.get("id").toString()));
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 批量删除用户
	 */
	@RequestMapping(value="/back/deleteAllMember")
	@ResponseBody
	public Object deleteAllMember() {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String News_IDS = pd.getString("News_IDS");
			
			if(null != News_IDS && !"".equals(News_IDS)){
				String ArrayNews_IDS[] = News_IDS.split(",");
				if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){memberService.deleteAllMembers(ArrayNews_IDS);}
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
