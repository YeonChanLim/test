package com.ipp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ipp.domain.ProductVO;
import com.ipp.domain.SearchCriteria;
import com.ipp.persistence.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	ProductDAO dao;

	@Override
	public void regist(ProductVO vo) throws Exception {
		dao.create(vo);
	}

	@Override
	public ProductVO read(int pno) throws Exception {
		dao.updateViewCount(pno);

		return dao.read(pno);
	}

	@Override
	public void modify(ProductVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	public void remove(int pno) throws Exception {
		dao.delete(pno);
	}

	@Override
	public List<ProductVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

}
