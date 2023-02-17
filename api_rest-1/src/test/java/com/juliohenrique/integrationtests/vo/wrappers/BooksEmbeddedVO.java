package com.juliohenrique.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juliohenrique.integrationtests.vo.BooksVO;

public class BooksEmbeddedVO implements Serializable {
    
    private static final long serialVersionUID = 1L;


    @JsonProperty("booksVOList")
	private List<BooksVO> books;


    public BooksEmbeddedVO() {
	}


	public List<BooksVO> getBooks() {
		return books;
	}


	public void setBooks(List<BooksVO> books) {
		this.books = books;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        BooksEmbeddedVO other = (BooksEmbeddedVO) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		return true;
	}

    
}
