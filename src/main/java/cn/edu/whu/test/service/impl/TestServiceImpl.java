package cn.edu.whu.test.service.impl;

import cn.edu.whu.common.dao.BookDao;
import cn.edu.whu.common.dto.ResultMessage;
import cn.edu.whu.common.entity.Book;
import cn.edu.whu.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Fly on 2017/7/26.
 */
@Service
public class TestServiceImpl implements TestService{
    @Autowired
    private BookDao bookDao;
    public String getBook(long id) {
        Book book = bookDao.getBookById(id);
        return new ResultMessage<String>(true, "查询成功", book.getName(), 200).toString();
    }
}
