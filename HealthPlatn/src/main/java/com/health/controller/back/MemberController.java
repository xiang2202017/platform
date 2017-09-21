package com.health.controller.back;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.health.controller.base.BaseController;
import com.health.entity.system.Page;
import com.health.entity.system.User;
import com.health.service.system.DeliveryAddressService;
import com.health.service.system.MemberCancelService;
import com.health.service.system.MemberJoinService;
import com.health.service.system.MemberRenewService;
import com.health.service.system.MemberService;
import com.health.system.util.Const;
import com.health.system.util.DateUtil;
import com.health.system.util.PageData;

@Controller
public class MemberController extends BaseController{

	String menuUrl = "/back/listMember"; //菜单地址(权限用)
	@Resource(name="memberService")
	private MemberService memberService;
	@Resource(name="memberRenewService")
	private MemberRenewService memberRenewService;
	@Resource(name="memberCancelService")
	private MemberCancelService memberCancelService;
	@Resource(name="deliveryAddressService")
	private DeliveryAddressService deliveryAddressService;
	@Resource(name="memberJoinService")
	private MemberJoinService memberJoinService;
	
	
	/**
	 * 查询用户列表
	 */
	@RequestMapping(value="/back/listMember")
	public ModelAndView listMember(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String memberNo = pd.getString("memberNo");
		String phone = pd.getString("phone");
		String name = pd.getString("name");
		String typeId = pd.getString("typeId");
		
		if(typeId != null && !"".equals(typeId)){
			pd.put("memberType", typeId);
		}
		
		if(null != phone && !"".equals(phone)){
			phone = phone.trim();
			pd.put("phone", phone);
		}
		
		if(null != name && !"".equals(name)){
			name = name.trim();
			pd.put("memberName", name);
		}
		
		if(null != memberNo && !"".equals(memberNo)){
			memberNo = memberNo.trim();
			pd.put("memberNo", memberNo);
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
	public ModelAndView toMemberView(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String id = getRequest().getParameter("id");
		pd = memberService.getMemberById(id);
		
		PageData addressPd = new PageData();
		addressPd.put("memberId", id);
		page.setPd(addressPd);
		List<PageData> addresslist = deliveryAddressService.getDatalistPage(page);
		
		mv.addObject("pd", pd);
		mv.addObject("memberAddressList", addresslist);
		mv.setViewName("back/member/member_view");
		
		return mv;
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
			mv.setViewName("back/member/member_edit");
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
	 * 修改用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateMember")
	public String updateMember() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			pg.put("editime", new Date());
			memberService.updateMember(pg);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listMember";
	}	
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	
///////////////////////////////////////////////////////会员续约管理////////////////////////////////////////////////////////////////////
	/**
	 * 跳转到会员续约界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/listMemberRenew")
	public ModelAndView toMemberRenewList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String typeId = pd.getString("typeId");
		if(typeId == null || typeId == ""){
			pd.put("typeId", "1");//默认查询待处理续约
		}
		
		page.setPd(pd);
		List<PageData>	memberRenewList = memberRenewService.getDatalistPage(page);			//列出用户列表
		
		mv.addObject("memberRenewList", memberRenewList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		mv.setViewName("back/member/member_renew_list");
		return mv;
	}
	
	/**
	 * 进入用户续约查看页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toMemberRenewView")
	public ModelAndView toRenewView() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			String id = getRequest().getParameter("id");
			pd = memberRenewService.getMemberRenewById(id);
			mv.addObject("pd", pd);
			mv.setViewName("back/member/member_renew_view");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 进入修改用户续约页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toMemberRenewEdit")
	public ModelAndView toRenewEdit() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			String id = getRequest().getParameter("id");
			pd = memberRenewService.getMemberRenewById(id);
			mv.addObject("pd", pd);
			mv.setViewName("back/member/member_renew_edit");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 用户续约处理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateMemberRenew")
	public String updateMemberRenew() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			String step = pg.get("step").toString();	//续约申请处理的步骤
			if(step.equals("1")){//续约申请中
				String status = pg.get("status").toString();
				if(status.equals("1")){//申请成功
					pg.put("status", 2);
				}else if(status.equals("2")){//申请失败
					String failReason = pg.getString("failReason");
					pg.put("status", 3);
					pg.put("failReason", failReason);
				}
			}else if(step.equals("2")){//续约申请成功
				pg.put("status", 4);
			}
			
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pg.put("operator", user.getNAME());
			pg.put("dealTime", new Date());
			memberRenewService.updateMemberRenew(pg);
			
			//修改会员的有效期
			if(pg.get("status").toString().equals("4")){//签约
				Integer years = Integer.parseInt(pg.get("requestTerm").toString());
				//判断会员是否已过有效期
				PageData member = memberService.getMemberById(pg.getString("memberId"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		        Date expireDate = format.parse(member.get("expireDate").toString());
		        Date now = new Date();
		        Date expireDateNew;
		        if(now.getTime() > expireDate.getTime()){//已过期，从当前时间往后延续
		        	expireDateNew = DateUtil.getAfterDayDate(now, 365 * years);
		        }else{//未过期，从过期日后面一天往后延期
		        	expireDateNew = DateUtil.getAfterDayDate(expireDate, (365 * years + 1));
		        }
		        member.put("period", 365*years);
		        member.put("expireDate", expireDateNew);
		        memberService.updateMember(member);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listMemberRenew";
	}
	
	
///////////////////////////////////////////////////////会员解约管理////////////////////////////////////////////////////////////////////
	/**
	 * 跳转到会员解约界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/listMemberCancel")
	public ModelAndView toMemberCancelList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String typeId = pd.getString("typeId");
		if(typeId == null || typeId == ""){
			pd.put("typeId", "1");//默认查询待处理解约
		}
		
		page.setPd(pd);
		List<PageData>	memberCancelList = memberCancelService.getDatalistPage(page);			//列出用户列表
		
		mv.addObject("memberCancelList", memberCancelList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		mv.setViewName("back/member/member_cancel_list");
		return mv;
	}
	
	/**
	 * 显示解约详情
	 */
	@RequestMapping(value="/back/toMemberCancel")
	public ModelAndView toMemberCancel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String id = getRequest().getParameter("id");
		pd = memberCancelService.getMemberCancelById(id);
		
		mv.addObject("pd", pd);
		mv.setViewName("back/member/member_cancel_view");
		
		return mv;
	}
	
	/**
	 * 进入修改用户解约页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toMemberCancelEdit")
	public ModelAndView toRenewCancelEdit() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			String id = getRequest().getParameter("id");
			pd = memberCancelService.getMemberCancelById(id);
			mv.addObject("pd", pd);
			mv.setViewName("back/member/member_cancel_edit");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 用户解约处理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateMemberCancel")
	public String updateMemberCancel() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			String step = pg.get("step").toString();	//续约申请处理的步骤
			if(step.equals("1")){//解约申请中
				String status = pg.get("status").toString();
				if(status.equals("1")){//申请成功
					pg.put("status", 2);
				}else if(status.equals("2")){//申请失败
					String failReason = pg.getString("failReason");
					pg.put("status", 3);
					pg.put("failReason", failReason);
				}
			}else if(step.equals("2")){//解约申请成功
				pg.put("status", 4);
			}
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pg.put("operator", user.getNAME());
			pg.put("dealTime", new Date());
			memberCancelService.updateMemberCancel(pg);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listMemberCancel";
	}
	
//////////////////////////////////////////////////////////////会员加盟管理////////////////////////////////////////////////////////////////////	
	/**
	 * 跳转到会员加盟界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/listMemberJoin")
	public ModelAndView toMemberJoinList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String typeId = pd.getString("typeId");
		if(typeId == null || typeId == ""){
			pd.put("typeId", "1");//默认查询待处理解约
		}
		
		page.setPd(pd);
		List<PageData>	memberJoinList = memberJoinService.getDatalistPage(page);			//列出用户列表
		
		mv.addObject("memberJoinList", memberJoinList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		mv.setViewName("back/member/member_join_list");
		return mv;
	}
	
	/**
	 * 显示加盟详情
	 */
	@RequestMapping(value="/back/toMemberJoin")
	public ModelAndView toMemberJoin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String id = getRequest().getParameter("id");
		pd = memberJoinService.getMemberJoinById(id);
		
		mv.addObject("pd", pd);
		mv.setViewName("back/member/member_join_view");
		
		return mv;
	}
	
	/**
	 * 进入修改用户加盟页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/toMemberJoinEdit")
	public ModelAndView toRenewJoinEdit() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = new PageData();
		try{
			String id = getRequest().getParameter("id");
			pd = memberJoinService.getMemberJoinById(id);
			mv.addObject("pd", pd);
			mv.setViewName("back/member/member_join_edit");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 用户加盟处理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/back/updateMemberJoin")
	public String updateMemberJoin() throws Exception{
		PageData pg = new PageData();
		try{
			pg = this.getPageData();
			pg.put("status", 2);
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pg.put("operator", user.getNAME());
			pg.put("dealTime", new Date());
			memberJoinService.updateMemberJoin(pg);
			
			//将会员类型更改为经销会员
			String memberId = pg.getString("memberId");
			PageData member = new PageData();
			member.put("id", memberId);
			member.put("memberType", 2);
			memberService.updateMember(member);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString(), e);
		}
		return "redirect:/back/listMemberJoin";
	}
}
