package com.ipp.persistence;

import java.util.List;

import com.ipp.domain.QnAVO;
import com.ipp.domain.SearchCriteria;

public interface QnADAO {

	// 1. QnA - 등록
	public int create(QnAVO vo) throws Exception;

	// 2. QnA - 조회
	public QnAVO read(int qnaNo) throws Exception;

	// 3. QnA - 수정
	public void update(QnAVO vo) throws Exception;

	// 4. QnA - 삭제
	public void delete(int qnaNo) throws Exception;

	// 5. QnA 내역 및 검색 리스트
	public List<QnAVO> listSearch(SearchCriteria cri) throws Exception;

	public int listSearchCount(SearchCriteria cri) throws Exception;

	// 6. QnA - 조회수
	public void updateHits(int qnaNo) throws Exception;

}
