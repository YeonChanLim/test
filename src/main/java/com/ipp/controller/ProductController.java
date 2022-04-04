package com.ipp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
import com.ipp.domain.ProductVO;
import com.ipp.domain.SearchCriteria;
import com.ipp.domain.UserVO;
import com.ipp.service.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Inject
	ProductService service;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		logger.info("listPage " + cri.toString());

		// 1) cri에 맞는 게시글 정보 담아서 화면으로 전달
		model.addAttribute("list", service.listSearch(cri));

		// 2) 페이징 정보를 담아서 화면으로 전달
		// 2-1) 페이징 정보 (cri) 설정
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);

		// 2-2) 검색과 페이지 정보(cri)에 따른 totalCount(-> calcData() 실행) 설정
		pageMaker.setTotalCount(service.listSearchCount(cri));

		// 2-3) 화면으로 전달
		model.addAttribute("pageMaker", pageMaker);

	}

	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("pno") int pno, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {

		// requestParam으로 전달받은 pno의 상세 정보 가져와서 화면으로 전달
		model.addAttribute(service.read(pno));
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public String modifyPageGet(int pno, HttpSession session, @ModelAttribute("cri") SearchCriteria cri, Model model,
			RedirectAttributes rttr) throws Exception {

		// 수정 할 수 있으려면, 로그인 한 정보와 글 작성자의 정보가 동일할 경우에만 이동

		// 1) 로그인 정보 가져오기
		UserVO user = (UserVO) session.getAttribute("login");

		// 2) 게시글의 작성자 id와 로그인 정보 id를 비교
		// 2-1) 게시글 정보 가져오기
		ProductVO product = service.read(pno);

		// 2-2) 게시글 작성자 id와 로그인 정보 id비교
		if (user.getUsid().equals(product.getWriter())) {
			// 작성자와 로그인 정보 같음
			model.addAttribute(product);
			// 수정 페이지로 이동
			return "/product/modifyPage";
		} else {
			// 로그인 정보와 게시글 작성자가 일치 하지 않은 경우 -> 강제이동
			rttr.addAttribute("pno", pno);
			rttr.addAttribute("page", cri.getPage());
			rttr.addAttribute("perPageNum", cri.getPerPageNum());
			rttr.addAttribute("searchType", cri.getSearchType());
			rttr.addAttribute("keyword", cri.getKeyword());
			rttr.addFlashAttribute("msg", "로그인 정보가 일치하지 않아 수정 불가능합니다.");

			return "redirect:/product/readPage";
		}
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagePOST(ProductVO product, SearchCriteria cri, RedirectAttributes rttr) throws Exception {

		service.modify(product);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/product/list";
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String removePage(@RequestParam("pno") int pno, HttpSession session,
			@ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr) throws Exception {

		// 수정할 수 있으려면, 로그인 한 정보와 글 작성자의 정보가 동일할 때만 수정 page로 이동

		// 1) 로그인 정보 가져오기
		UserVO user = (UserVO) session.getAttribute("login");

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		// 2) 게시글의 작성자 id와 로그인 정보 id를 비교
		// 2-1) 게시글 정보 가져오기
		ProductVO product = service.read(pno);

		// 2-2) 게시글 작성자 id롸 로그인 정보 id 비교
		if (user.getUsid().equals(product.getWriter())) {
			// 작성자와 로그인 정보 같음 -> 게시글 삭제
			service.remove(pno);
			rttr.addFlashAttribute("msg", "SUCCESS");
			return "redirect:/product/list";

		} else {
			// 로그인 정보와 게시글 작성자가 일치 하지 않은 경우 -> 삭제하지 않고 강제이동
			rttr.addAttribute("pno", pno);
			rttr.addFlashAttribute("msg", "로그인 정보가 일치하지 않아 삭제 불가능합니다.");

			return "redirect:/product/readPage";
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET() {
		logger.info("register GET......");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(ProductVO product, RedirectAttributes rttr) throws Exception {

		service.regist(product);
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/product/list";
	}

}
