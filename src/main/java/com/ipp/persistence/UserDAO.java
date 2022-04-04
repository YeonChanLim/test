package com.ipp.persistence;

import java.util.List;

import com.ipp.domain.SearchCriteria;
import com.ipp.domain.UserVO;
import com.ipp.dto.LoginDTO;

public interface UserDAO {

	public UserVO login(LoginDTO dto) throws Exception;

	public void create(UserVO vo) throws Exception;

	public UserVO read(String usid) throws Exception;

	public void update(UserVO vo) throws Exception;

	public void delete(String usid) throws Exception;

	//페이징, 검색 할 수 있는 list
	public List<UserVO> listSearch(SearchCriteria cri) throws Exception;
	
	//페이징, 검색 하기 위한 게시물 수 반환
	public int listSearchCount(SearchCriteria cri) throws Exception;

}
