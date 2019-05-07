package cn.tyx.TangBook.cart.domain;

import java.math.BigDecimal;

import cn.tyx.TangBook.book.domain.Book;

public class CartItem {
	private Book book;
	private int count;
	//条目小计
	public double getSubtotal(){
		BigDecimal b1=new BigDecimal(book.getPrice()+"");
		BigDecimal b2=new BigDecimal(count+"");
		return b1.multiply(b2).doubleValue();
	}
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + "]";
	}
	
}
