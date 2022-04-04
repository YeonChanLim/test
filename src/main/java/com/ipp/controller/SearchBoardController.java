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

import com.ipp.domain.BoardVO;
import com.ipp.domain.PageMaker;
import com.ipp.domain.SearchCriteria;
import com.ipp.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {

	@Inject
	BoardService service;

	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info(cri.toString());

		// 1) 검색한 정보 담기
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);

		// 2) 검색한 정보의 게시글 수 설정
		pageMaker.setTotalCount(service.listSearchCount(cri));

		// 3) 페이징 정보 jsp로 전달
		model.addAttribute("pageMaker", pageMaker);

		// 4) 검색 결과 리스트 jsp로 전달
		model.addAttribute("list", service.listSearch(cri));
	}

	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {

		model.addAttribute(service.read(bno));
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET) // 수정 화면으로 이동
	public void modifyPageGET(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		model.addAttribute(service.read(bno));

	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST) // 실제 게시글 데이터베이스 수정
	public String modifyPagePOST(BoardVO board, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.modify(board);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/sboard/list";
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String removePage(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri,
			RedirectAttributes rttr) throws Exception {

		service.remove(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/sboard/list";

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET) // 등록 화면으로 이동
	public void registerGET(BoardVO board, Model model) throws Exception {
		logger.info("register get......");

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST) // 게시글 데이터베이스에 등록
	public String registerPOST(BoardVO board, RedirectAttributes rttr) throws Exception {

		service.regist(board);
		rttr.addFlashAttribute("result", "SUCCESS");

		return "redirect:/sboard/list";

	}
}
