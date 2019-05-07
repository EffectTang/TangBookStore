package cn.tyx.TangBook.category.service;

import java.util.List;

import cn.tyx.TangBook.book.dao.BookDao;
import cn.tyx.TangBook.category.dao.CategoryDao;
import cn.tyx.TangBook.category.domain.Category;
import cn.tyx.TangBook.category.web.servlet.admin.CategoryException;

public class CategoryService {
	CategoryDao categoryDao=new CategoryDao();
	BookDao bookdao=new BookDao();
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public void add(Category category) {
			categoryDao.add(category);
	} 

	public void delete(String cid) throws CategoryException {
		int count=bookdao.getCountByCid(cid);
		if(count>0) throw new CategoryException("这个分类下面还有图书,不能删除");
		categoryDao.delete(cid);
	}

	public Category load(String cid) {
		return categoryDao.load(cid);
	}

	public void edit(Category category) {
		categoryDao.edit(category);
	}
}
