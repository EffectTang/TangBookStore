package cn.tyx.TangBook.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	
	private Map<String, CartItem> map=new LinkedHashMap<String, CartItem>();
	
	public double getTotal(){
		double total=0;
		BigDecimal d1=new BigDecimal(total+"");
		for(CartItem cartitem:map.values())
		{										
			BigDecimal bd=new BigDecimal(cartitem.getSubtotal()+"");
			//d1.add(bd);
			d1=d1.add(bd);
		}
		return d1.doubleValue();
	}
	
	public void add(CartItem cartItem)
	{
		if(map.containsKey(cartItem.getBook().getBid()))
		{
			CartItem _cartitem=map.get(cartItem.getBook().getBid());//以前的条目
			_cartitem.setCount(_cartitem.getCount()+cartItem.getCount());//以前的条目 加上 现在的条目
			map.put(cartItem.getBook().getBid(), _cartitem); 
		}
		else{
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}
	
	public void clear(){
		map.clear();
	}
	
	public void delete(String bid){
		map.remove(bid);
	}
	
	public Collection<CartItem> getCartItems(){
		
		return map.values();
	}
}
