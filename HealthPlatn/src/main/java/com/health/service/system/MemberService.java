package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Member;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("memberService")
public class MemberService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除会员
	public void deleteMemberById(Integer id) throws Exception{
		dao.delete("MemberMapper.delete", id);			//删除会员
	}
	
	//批量删除会员
	public void deleteAllMembers(String[] ids) throws Exception{
		dao.delete("MemberMapper.deleteAll", ids);
	}
	
	//添加会员
	public Integer insertMember(PageData pd) throws Exception{
		return (Integer)dao.save("MemberMapper.save", pd);
	}
	
	//根据id查找会员信息
	public PageData getMemberById(String id) throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findById", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MemberMapper.datalistPage", page);
	}
	
	//编辑会员信息
	public void updateMember(PageData pd) throws Exception{
		dao.update("MemberMapper.update", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getMemberTypeList() throws Exception{
		return (List<PageData>)dao.findForList("MemberTypeMapper.listAll","");
	}
	
	public String getTypeName(Integer id) throws Exception{
		return (String)dao.findForObject("MemberTypeMapper.getNameById", id);
	}
	
	/*
	* 登录判断
	*/
	public Member getMemberByNameAndPwd(PageData pd)throws Exception{
		return (Member)dao.findForObject("MemberMapper.getMemberInfo", pd);
	}
	/*
	* 更新登录时间
	*/
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("MemberMapper.updateLastLogin", pd);
	}
	
}
