package com.health.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.health.dao.system.DaoSupport;
import com.health.entity.system.News;
import com.health.entity.system.Page;
import com.health.system.util.PageData;

@Service("newsService")
public class NewsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//删除资讯
	public void deleteNewsById(Integer id) throws Exception{
		dao.delete("NewsDetailMapper.delete", id);		//删除资讯详情
		dao.delete("NewsMapper.delete", id);			//删除资讯
	}
	
	//批量删除资讯
	public void deleteAllNews(String[] newsIds) throws Exception{
		dao.delete("NewsDetailMapper.deleteAllNews", newsIds);
		dao.delete("NewsMapper.deleteAll", newsIds);
	}
	
	//添加资讯
	public Integer insertNews(PageData pd) throws Exception{
		return (Integer)dao.save("NewsMapper.save", pd);
	}
	
	//添加资讯详情
	public void insertNewsDetail(PageData pd) throws Exception{
		dao.save("NewsDetailMapper.save", pd);
	}
	
	//根据id查找资讯信息
	public PageData getNewsById(String id) throws Exception{
		return (PageData)dao.findForObject("NewsMapper.findById", id);
	}
	
	//根据资讯id查找资讯详情
	public PageData getNewsDetailById(String newsId) throws Exception{
		return (PageData)dao.findForObject("NewsMapper.findDetailById", newsId);
	}
	
	//根据资讯id查找上一条资讯详情
	public PageData getNewsDetailByPreId(PageData pd) throws Exception{
		return (PageData)dao.findForObject("NewsMapper.findDetailByPreId", pd);
	}
	
	//根据资讯id查找下一条资讯详情
	public PageData getNewsDetailByNextId(PageData pd) throws Exception{
		return (PageData)dao.findForObject("NewsMapper.findDetailByNextId", pd);
	}
	
	//根据查询条件查询资讯信息
	@SuppressWarnings("unchecked")
	public List<PageData> getNewsList(Page page) throws Exception{
		return (List<PageData>)dao.findForList("NewsMapper.findList", page);
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getDatalistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("NewsMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<News> getNewsForLen(Integer fromIndex, Integer toIndex, Integer type) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("fromIndex", fromIndex);
		map.put("toIndex", toIndex);
		map.put("type", type);
		return (List<News>)dao.findForList("NewsMapper.getNewsForLen", map);
	}
	
	//编辑资讯信息
	public void updateNews(PageData pd) throws Exception{
		dao.update("NewsMapper.update", pd);
		dao.update("NewsDetailMapper.update", pd);
	}
	
}
