package com.ipp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipp.domain.NoticeVO;
import com.ipp.domain.PageMaker;
import com.ipp.domain.SearchCriteria;
import com.ipp.service.NoticeService;

@Controller
@RequestMapping("/notice/*")
public class NoticeController {

	@Inject
	private NoticeService service;

	private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

	// 1. 리스트 띄우기
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		logger.info("Notice listGET......");

		//TODO		
//		List<NoticeVO> notices = service.listSearch(cri);
//		
//		for(NoticeVO notice : notices) {
//			logger.info(notice.toString());
//		}
		
		
		model.addAttribute("list", service.listSearch(cri));
		logger.info("list size: " + service.listSearch(cri).size());

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		model.addAttribute("pageMaker", pageMaker);
	}

	// 2. 상세보기
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("noticeNo") int noticeNo, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {

		logger.info("readPage.............");

//TODO
//		NoticeVO notice = new NoticeVO();
//		try {
//			logger.info(String.valueOf(noticeNo));
//			notice = service.read(noticeNo);
//			logger.info(notice.toString());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		// 프로그램 내용
		model.addAttribute(service.read(noticeNo));

		// 업로드한 파일 리스트
		logger.info("readPage.FileVO", service.fileList(noticeNo).size());

		model.addAttribute("noticeFileVO", service.fileList(noticeNo));

		model.addAttribute("page", cri.getPage());
		model.addAttribute("perPageNum", cri.getPerPageNum());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
	}

	// 3. 공지사항 등록 화면
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(HttpServletRequest request) throws Exception {
		logger.info("notice register get..");

	}

	// 4. 공지사항 등록
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(NoticeVO vo, RedirectAttributes rttr) throws Exception {

		logger.info("notice Register POST .......");
		logger.info("vo : " + vo);

		service.register(vo);
		rttr.addFlashAttribute("msg", "register");

		return "redirect:/notice/list";
	}

	// 5. 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(@RequestParam("noticeNo") int noticeNo, @ModelAttribute("cri") SearchCriteria cri,
			Model model) throws Exception {

		logger.info("notice modifyGET....");

		model.addAttribute(service.read(noticeNo));
		model.addAttribute("noticeFileVO", service.fileList(noticeNo));
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(NoticeVO vo, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		logger.info("notice modifyPOST.....");
		logger.info(cri.toString());

		service.modify(vo);

		rttr.addFlashAttribute("msg", "MODIFY");
		rttr.addFlashAttribute("vo", vo);

		logger.info(rttr.toString());

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		return "redirect:/notice/list";
	}

	// 6. 공지사항 관리 - 삭제
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("noticeNo") int noticeNo, SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.remove(noticeNo);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "REMOVE");
		return "redirect:/notice/list";
	}

}
