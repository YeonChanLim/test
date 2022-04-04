package com.ipp.service;

import java.util.List;

import com.ipp.domain.BoardVO;
import com.ipp.domain.Criteria;
import com.ipp.domain.SearchCriteria;

public interface BoardService {

	public void regist(BoardVO board) throws Exception;

	public BoardVO read(Integer bno) throws Exception;

	public void modify(BoardVO board) throws Exception;

	public void remove(Integer bno) throws Exception;

	public List<BoardVO> listAll() throws Exception;

	// 페이징 처리하여 리스트 가져오는 기능
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;

	// 페이징 처리를 위한 전체 게시글 수 가져오는 기능
	public int listCountCriteria(Criteria cri) throws Exception;

	// 페이징 + 검색을 처리하여 리스트 가져오는 기능
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;

	// 페이징 + 검색을 위한 전체 게시글 수 가져오는 기능
	public int listSearchCount(SearchCriteria cri) throws Exception;

}
