package com.ipp.service;

import java.util.List;

import com.ipp.domain.ProductVO;
import com.ipp.domain.SearchCriteria;

public interface ProductService {
	
	public void regist(ProductVO vo) throws Exception;
	
	public ProductVO read(int pno) throws Exception;
	
	public void modify(ProductVO vo) throws Exception;
	
	public void remove(int pno) throws Exception;
	
	//페이징, 검색 기능을 제공하는 list
	public List<ProductVO> listSearch(SearchCriteria cri) throws Exception;
	
	//페이징, 검색 기능 게시글 수 가져오는 기능
	public int listSearchCount(SearchCriteria cri) throws Exception;

}
