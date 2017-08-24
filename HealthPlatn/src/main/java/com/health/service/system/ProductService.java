package com.health.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("productService")
public class ProductService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除产品
	public void deleteProductById(Integer id) throws Exception{
		dao.delete("ProductDetailMapper.delete", id);		//删除产品详情
		dao.delete("ProductMapper.delete", id);			//删除产品
	}
	
	//批量删除产品
	public void deleteAllProducts(String[] productIds) throws Exception{
		dao.delete("ProductDetailMapper.deleteAllProducts", productIds);
		dao.delete("ProductMapper.deleteAll", productIds);
	}
	
	//添加产品
	public Integer insertProduct(PageData pd) throws Exception{
		return (Integer)dao.save("ProductMapper.save", pd);
	}
	
	//添加产品详情
	public void insertProductDetail(PageData pd) throws Exception{
		dao.save("ProductDetailMapper.save", pd);
	}
	
	//根据id查找产品信息
	public PageData getProductById(String id) throws Exception{
		return (PageData)dao.findForObject("ProductMapper.findById", id);
	}
	
	//根据产品id查找产品详情
	public PageData getProductDetailById(String id) throws Exception{
		return (PageData)dao.findForObject("ProductMapper.findDetailById", id);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ProductMapper.datalistPage", page);
	}
	
	//编辑产品信息
	public void updateProduct(PageData pd) throws Exception{
		dao.update("ProductMapper.update", pd);
		dao.update("ProductDetailMapper.update", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getProductTypeList() throws Exception{
		return (List<PageData>)dao.findForList("ProductTypeMapper.listAll","");
	}
	
	public String getTypeName(Integer id) throws Exception{
		return (String)dao.findForObject("ProductTypeMapper.getNameById", id);
	}
	
}
