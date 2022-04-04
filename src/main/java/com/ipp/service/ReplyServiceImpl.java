package com.ipp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ipp.domain.ReplyVO;
import com.ipp.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	private ReplyDAO dao;

	// 1. QnA - 댓글 생성
	@Override
	public void add(ReplyVO revo) throws Exception {
		dao.create(revo);

	}

	// 2. QnA - 댓글 삭제
	@Override
	public void remove(int replyNo) throws Exception {
		dao.delete(replyNo);

	}

	// 3. QnA - 댓글 전체 삭제
	@Override
	public void removeAll(int qnaNo) throws Exception {
		dao.deleteAll(qnaNo);

	}

	// 4. QnA - 댓글 전체 보기
	@Override
	public List<ReplyVO> list(int qnaNo) throws Exception {
		return dao.list(qnaNo);
	}

}
