package com.juliohenrique.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.juliohenrique.data.vo.v1.BooksVO;
import com.juliohenrique.model.Books;

public class MockBook {


    public Books mockEntity() {
        return mockEntity(0);
    }
    
    public BooksVO mockVO() {
        return mockVO(0);
    }
    
    public List<Books> mockEntityList() {
        List<Books> book = new ArrayList<Books>();
        for (int i = 0; i < 14; i++) {
            book.add(mockEntity(i));
        }
        return book;
    }

    public List<BooksVO> mockVOList() {
        List<BooksVO> book = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            book.add(mockVO(i));
        }
        return book;
    }
    
    public Books mockEntity(Integer number) {
        Books book = new Books();
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setLaunchDate(new Date());
        book.setId(number.longValue());
        book.setPrice(25D);
        return book;
    }

    public BooksVO mockVO(Integer number) {
        BooksVO book = new BooksVO();
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setLaunchDate(new Date());
        book.setKey(number.longValue());
        book.setPrice(25D);
        return book;
    }

}
