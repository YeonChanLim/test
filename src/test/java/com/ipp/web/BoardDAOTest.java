package com.ipp.web;

import java.util.List;

import javax.inject.Inject;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ipp.domain.BoardVO;
import com.ipp.domain.Criteria;
import com.ipp.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })

public class BoardDAOTest {

	@Inject
	private BoardDAO dao;

	private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);

	/*
	 * @Test public void testCreate() throws Exception {
	 * 
	 * BoardVO board = new BoardVO(); board.setTitle("새로운 글을 넣습니다.2");
	 * board.setContent("새로운 글을 넣습니다.2"); board.setWriter("user00");
	 * dao.create(board);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Test public void testRead() throws Exception {
	 * logger.info(dao.read(2).toString()); }
	 */

	/*
	 * @Test public void testUpdate() throws Exception { BoardVO board = new
	 * BoardVO(); board.setBno(1); board.setTitle("수정된 글입니다.");
	 * board.setContent("수정 테스트"); dao.update(board); }
	 */

	/*
	 * @Test public void testDelete() throws Exception { dao.delete(1); }
	 */

	/*
	 * @Test public void testListPage() throws Exception{
	 * 
	 * int page = 1;
	 * 
	 * List<BoardVO> list = dao.listPage(page);
	 * 
	 * for (BoardVO boardVO : list) { logger.info(boardVO.getBno() + ":"
	 * +boardVO.getTitle()); } }
	 */

	@Test
	public void testListCriteria() throws Exception {

		Criteria cri = new Criteria();
		cri.setPage(3);
		cri.setPerPageNum(20);

		List<BoardVO> list = dao.listCriteria(cri);

		for (BoardVO boardVO : list) {
			logger.info(boardVO.getBno() + ":" + boardVO.getTitle());
		}
	}

}
