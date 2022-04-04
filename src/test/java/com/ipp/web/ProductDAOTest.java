package com.ipp.web;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ipp.domain.ProductVO;
import com.ipp.persistence.ProductDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductDAOTest {

	@Inject
	private ProductDAO dao;
	
	@Test
	public void testCreate() throws Exception{
		ProductVO product = new ProductVO();
		//pno는 insert할때 seq사용
		product.setPname("예제교재");
		product.setPrice(30000);
		product.setContent("스프링 예제 교재 입니다.");
		product.setWriter("user10");
		
		dao.create(product);
	}
}
