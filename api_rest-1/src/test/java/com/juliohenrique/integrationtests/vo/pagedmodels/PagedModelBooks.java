package com.juliohenrique.integrationtests.vo.pagedmodels;

import java.util.List;

import com.juliohenrique.integrationtests.vo.BooksVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelBooks {

	@XmlElement(name = "content")
	private List<BooksVO> content;

	public PagedModelBooks() {}

	public List<BooksVO> getContent() {
		return content;
	}	

	public void setContent(List<BooksVO> content) {
		this.content = content;
	}
}
