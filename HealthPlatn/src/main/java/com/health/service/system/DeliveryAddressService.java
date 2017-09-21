package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("deliveryAddressService")
public class DeliveryAddressService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除邮寄地址
	public void deleteAddressById(Integer id) throws Exception{
		dao.delete("DeliveryAddressMapper.delete", id);			//删除邮寄地址申请
	}
	
	//批量删除邮寄地址
	public void deleteAllAddresss(String[] ids) throws Exception{
		dao.delete("DeliveryAddressMapper.deleteAll", ids);
	}
	
	//添加邮寄地址
	public Integer insertAddress(PageData pd) throws Exception{
		return (Integer)dao.save("DeliveryAddressMapper.save", pd);
	}
	
	//根据id查找邮寄地址信息
	public PageData getAddressById(String id) throws Exception{
		return (PageData)dao.findForObject("DeliveryAddressMapper.findById", id);
	}
	
	//根据会员id查找邮寄地址信息
	public PageData getAddressByMemberId(String id) throws Exception{
		return (PageData)dao.findForObject("DeliveryAddressMapper.findByMemberId", id);
	}
	
	/**
	 * 获取默认地址
	 * @return
	 * @throws Exception
	 */
	public PageData findDefaultAddress() throws Exception{
		return (PageData)dao.findForObject("DeliveryAddressMapper.findDefaultAddress", "");
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("DeliveryAddressMapper.datalistPage", page);
	}
	
	//编辑邮寄地址信息
	public void updateAddress(PageData pd) throws Exception{
		dao.update("DeliveryAddressMapper.update", pd);
	}
	
}
