package com.ipp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.ipp.domain.SearchCriteria;
import com.ipp.domain.UserVO;
import com.ipp.dto.LoginDTO;
import com.ipp.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Inject
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto) {
		// views/user/login.jsp 페이지 이동
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		Object obj = session.getAttribute("login");

		if (obj != null) {
			logger.info("logout: session.invalidate() ");

			session.removeAttribute("login");
			session.invalidate();

		}

		return "redirect:/";
	}

	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception {
		UserVO vo = service.login(dto);

		if (vo == null) { // 로그인 정보가 없는 경우
			return;
		}

		// 로그인 정보가 있는 경우, session 객체에 정보 담기 - > Interceptor에게 이관
		// model 객체에 정보 담기
		model.addAttribute("userVO", vo);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET() {

		logger.info("register GET......");

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(UserVO user, RedirectAttributes rttr) throws Exception {

		service.register(user);
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";

	}

	@RequestMapping(value = "/memberRegister", method = RequestMethod.GET)
	public void memberRegisterGET(UserVO user, Model model) throws Exception {
		logger.info("register get.............");
	}

	@RequestMapping(value = "/memberRegister", method = RequestMethod.POST)
	public String memberRegisterPOST(UserVO user, RedirectAttributes rttr) throws Exception {
		service.register(user);
		rttr.addFlashAttribute("result", "SUCCESS");

		return "redirect:/user/login";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		logger.info("listPage " + cri.toString());

		// 1) cri에 맞는 게시글 정보 담아서 화면으로 전달
		model.addAttribute("list", service.listSearch(cri));

		// 2) 페이징 정보를 담아서 화면으로 전달
		// 2-1) 페이징 정보(cri) 설정
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);

		// 2-2) 검색과 페이지 정보(cri)에 따른 totalCount(->calcData() 실행) 설정
		pageMaker.setTotalCount(service.listSearchCount(cri));

		// 2-3) 화면으로 전달
		model.addAttribute("pageMaker", pageMaker);
	}

	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void readPage(@RequestParam("usid") String usid, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {

		model.addAttribute(service.read(usid));
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET) // 수정 화면으로 이동
	public void modifyPageGET(String usid, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		model.addAttribute(service.read(usid));

	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST) // 실제 게시글 데이터베이스 수정
	public String modifyPagePOST(UserVO user, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.modify(user);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";

	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String removePage(@RequestParam("usid") String usid, @ModelAttribute("cri") SearchCriteria cri,
			RedirectAttributes rttr) throws Exception {

		service.remove(usid);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";
	}
	
	

}
