package com.ipp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ipp.domain.NoticeFileVO;
import com.ipp.domain.NoticeVO;
import com.ipp.domain.SearchCriteria;
import com.ipp.persistence.NoticeDAO;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Inject
	NoticeDAO dao;

	// 1. 공지사항 등록
	@Override
	public void register(NoticeVO vo) throws Exception {

		// (1) 텍스트에어리어 줄바꿈 적용
		vo.setContent(vo.getContent().replace("\\r\\n", "<br>"));

		// (2) 기본 신청 내역 등록 (파일 등록 x) -> 등록된 신청내역의 PK 가져오기
		int noticeNo = dao.adCreate(vo);

		// (3) 추가 이미지 존재 여부 IF문
		if (vo.getFiles() != null) {

			// (4) 추가 이미지 저장 FOR문
			for (int i = 0; i < vo.getFiles().length; i++) {

				// (4-1) 추가 이미지 저장

				NoticeFileVO fVo = new NoticeFileVO();
				fVo.setNoticeNo(noticeNo);
				fVo.setNoticeFileName(vo.getFiles()[i]);

				// (4-2) 추가 이미지 저장
				dao.insertFile(fVo);

			}
		}

	}

	// 2. 공지사항 상세보기
	@Override
	public NoticeVO read(int noticeNo) throws Exception {
		return dao.read(noticeNo);
	}

	// 3. 공지사항 수정
	@Override
	public void modify(NoticeVO vo) throws Exception {
		// (1) 텍스트에어리어 줄바꿈 적용
		vo.setContent(vo.getContent().replace("\\r\\n", "<br>"));

		// (2) 프로그램 게시글 수정
		dao.adUpdate(vo);

		// (3) 프로그램 소속된 첨부파일 삭제
		dao.deleteFile(vo.getNoticeNo());

		// (4) 추가 이미지 존재 여부 IF문
		if (vo.getFiles() != null) {

			// (5) 추가 이미지 저장 FOR문
			for (int i = 0; i < vo.getFiles().length; i++) {

				// (5-1)추가 이미지 저장

				NoticeFileVO fVo = new NoticeFileVO();
				fVo.setNoticeNo(vo.getNoticeNo());
				fVo.setNoticeFileName(vo.getFiles()[i]);

				// (5-2)추가 이미지 저장
				dao.insertFile(fVo);

			}
		}

	}

	// 4. 공지사항 삭제
	@Override
	public void remove(int noticeNo) throws Exception {
		// 1)공지사항에 속해있는 첨부 파일 삭제
		dao.deleteFile(noticeNo);

		// 2)공지사항 삭제
		dao.adDelete(noticeNo);

	}

	// 5. Notice 검색 가능한 목록
	@Override
	public List<NoticeVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	// 6. Notice 검색 가능한 목록 ---> 페이징, 카운트
	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	// 7. 파일 목록
	@Override
	public List<NoticeFileVO> fileList(int noticeNo) throws Exception {
		return dao.fileList(noticeNo);
	}

}
