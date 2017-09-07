package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("memberRenewService")
public class MemberRenewService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除会员
	public void deleteMemberRenewById(Integer id) throws Exception{
		dao.delete("MemberRenewMapper.delete", id);			//删除会员续约申请
	}
	
	//批量删除会员
	public void deleteAllMemberRenews(String[] ids) throws Exception{
		dao.delete("MemberRenewMapper.deleteAll", ids);
	}
	
	//添加会员
	public Integer insertMemberRenew(PageData pd) throws Exception{
		return (Integer)dao.save("MemberRenewMapper.save", pd);
	}
	
	//根据id查找会员信息
	public PageData getMemberRenewById(String id) throws Exception{
		return (PageData)dao.findForObject("MemberRenewMapper.findById", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MemberRenewMapper.datalistPage", page);
	}
	
	//编辑会员信息
	public void updateMemberRenew(PageData pd) throws Exception{
		dao.update("MemberRenewMapper.update", pd);
	}
	
}
