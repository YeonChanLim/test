package com.ipp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ipp.domain.QnAVO;
import com.ipp.domain.SearchCriteria;
import com.ipp.persistence.QnADAO;
import com.ipp.persistence.ReplyDAO;

@Service
public class QnAServiceImpl implements QnAService {

	@Inject
	private QnADAO dao;

	@Inject
	private ReplyDAO redao;

	// 1. QnA - 게시물 등록
	@Override
	public void register(QnAVO vo) throws Exception {
		// 1. 텍스트에어리어 줄바꿈 적용
		vo.setQnaContents(vo.getQnaContents().replace("\\r\\n", "<br>"));

		// 2. 기본 신청 내역 등록 (파일 등록 x) -> 등록된 신청내역의 PK 가져오기
		dao.create(vo);

	}

	// 2. QnA - 조회
	@Override
	public QnAVO read(int qnaNo) throws Exception {
		dao.updateHits(qnaNo);
		return dao.read(qnaNo);

	}

	// 3. QnA - 수정
	@Override
	public void modify(QnAVO vo) throws Exception {
		dao.update(vo);

	}

	// 4. QnA - 삭제
	@Override
	public void remove(int qnaNo) throws Exception {
		redao.deleteAll(qnaNo); // 댓글(FK)부터 전체 삭제
		dao.delete(qnaNo); // 댓글 삭제 후 게시글 삭제

	}

	// 5. QnA 내역 및 검색 리스트
	@Override
	public List<QnAVO> listSearch(SearchCriteria cri) throws Exception {

		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

}
