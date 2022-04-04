package com.ipp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String LOGIN = "login"; // 로그인 정보를 저장할 key 이름
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// 로그인 정보를 session에 저장할 수 있도록 재정의

		// 1) 로그인 정보를 저장할 session 준비
		HttpSession session = request.getSession();

		// 2) 화면에 저장되어 있는 model 정보 가져오기 (UserController loginPost에서 저장함)
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get("userVO");

		// 3) 로그인 할 수 있는 정보 확인
		if (userVO != null) {

			// 4) session 객체에 로그인 정보 담기
			logger.info("new login success");
			session.setAttribute(LOGIN, userVO);

			// 5) 메인 화면으로 이동 -> 사용자가 마지막으로 접속 했던 경로로 다시 이동
			// response.sendRedirect("/");
			Object dest = session.getAttribute("dest");
			response.sendRedirect(dest != null ? (String) dest : "/"); // dest null인 경우 root 경로(post)
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 로그인이 실행 되기 전 기존 session 객체 정리하도록 재정의

		// 1) session 정보 가져오기
		HttpSession session = request.getSession();

		// 2) session 정보에 로그인 정보 있는지 확인
		if (session.getAttribute(LOGIN) != null) {

			// 3) 로그인 정보 삭제
			logger.info("clear login data before");
			session.removeAttribute(LOGIN);
		}

		return true;
	}

}
