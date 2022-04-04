package com.ipp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipp.domain.PageMaker;
import com.ipp.domain.QnAVO;
import com.ipp.domain.SearchCriteria;
import com.ipp.service.QnAService;

@Controller
@RequestMapping("/qna/*")
public class QnAController {

	@Inject
	private QnAService service;

	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);

	// 1. QnA 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		logger.info("get qna list......................"); // 정보 확인
		logger.info(cri.toString()); // 정보 확인
		
		model.addAttribute("list", service.listSearch(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);

		logger.info("QnA list get.,...........");
	}

	// 2. QnA 등록 화면 이동
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info("QnA register get.,...........");
	}

	// 2. QnA 등록
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(QnAVO qna, RedirectAttributes rttr) throws Exception {
		
		logger.info("regist post...........");
		logger.info(qna.toString());
		service.register(qna);

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/qna/list";

	}

	// 3. QnA 조회
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("qnaNo") int qnaNo, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {
		logger.info("QnA read get.,...........");
		model.addAttribute(service.read(qnaNo));
	}

	// 4. QnA 삭제
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("qnaNo") int qnaNo, SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.remove(qnaNo);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "REMOVE");

		return "redirect:/qna/list";

	}

	// 5.QnA 수정 화면 이동
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyPageGET(@RequestParam("qnaNo") int qnaNo, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {
		logger.info("QnA modify get ....................");

		model.addAttribute(service.read(qnaNo));

	}

	// 5.QnA 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(QnAVO vo, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {
		logger.info("QnA modify post ....................");

		service.modify(vo);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "MODIFY");

		return "redirect:/qna/list";
	}

}