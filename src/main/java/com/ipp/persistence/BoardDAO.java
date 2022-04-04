package com.ipp.persistence;

import java.util.List;

import com.ipp.domain.BoardVO;
import com.ipp.domain.Criteria;
import com.ipp.domain.SearchCriteria;

public interface BoardDAO {

	public void create(BoardVO vo) throws Exception;

	public BoardVO read(int bno) throws Exception;

	public void update(BoardVO vo) throws Exception;

	public void delete(Integer bno) throws Exception;

	// 페이징 없는 전체 리스트 가져오는 기능
	public List<BoardVO> listAll() throws Exception;

	/* public List<BoardVO> listPage(int page) throws Exception; */

	// 페이징 처리하여 리스트 가져오는 기능
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;

	// 페이징 처리를 위한 전체 게시글 수 가져오는 기능
	public int listCountCriteria(Criteria cri) throws Exception;

	// 페이징 + 검색을 처리하여 리스트 가져오는 기능
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception;

	// 페이징 + 검색을 위한 전체 게시글 수 가져오는 기능
	public int listSearchCountCriteria(SearchCriteria cri) throws Exception;
	
	// 게시글의 조회수 업데이트 기능
	public void updateViewCount(int bno) throws Exception;
}
