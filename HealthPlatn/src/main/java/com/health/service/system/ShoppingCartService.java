package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

/**
 * 购物车
 * @author XiangYu
 *
 */
@Service("shoppingCartService")
public class ShoppingCartService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除购物车
	public void deleteShoppingCartById(Integer id) throws Exception{
		dao.delete("ShoppingCartMapper.delete", id);			//删除购物车申请
	}
	
	//批量删除购物车
	public void deleteAllShoppingCarts(String[] ids) throws Exception{
		dao.delete("ShoppingCartMapper.deleteAll", ids);
	}
	
	//添加购物车记录
	public Integer insertShoppingCart(PageData pd) throws Exception{
		return (Integer)dao.save("ShoppingCartMapper.save", pd);
	}
	
	//根据id查找购物车信息
	public PageData getShoppingCartById(String id) throws Exception{
		return (PageData)dao.findForObject("ShoppingCartMapper.findById", id);
	}
	
	//根据会员id查找购物车信息
	public PageData getShoppingCartByMemberId(String id) throws Exception{
		return (PageData)dao.findForObject("ShoppingCartMapper.findByMemberId", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ShoppingCartMapper.datalistPage", page);
	}
	
	//编辑购物车信息
	public void updateShoppingCart(PageData pd) throws Exception{
		dao.update("ShoppingCartMapper.update", pd);
	}
	
}
