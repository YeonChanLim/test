package com.ipp.domain;

public class Criteria {

	private int page; //시작할 페이지
	private int perPageNum; //화면에 보여줄 게시글 수 
	
	//MyBatis에서 사용할 시작 페이지 정보
	private int startPage;

	public Criteria() { //생성자로 페이징 초기화
		
		this.page = 1;
		this.perPageNum = 10;
	}

	public void setPage(int page) {
		
		//페이징에 대한 예외 사항 처리 0보다 작거나 같을 경우
		if (page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public void setPerPageNum(int perPageNum) {
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}

	public int getPage() {
		return page;
	}

	// method for MyBatis SQL Mapper
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}

	// method for MyBatis SQL Mapper
	public int getPerPageNum() {
		return this.perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}

}
