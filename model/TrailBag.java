package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

import model.UserBag.UserBagIterator;

public class TrailBag implements Serializable, Iterable<Trail>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3040587659708288559L;
	private Trail[] arr;
	private int nElem;
	private UserBag userBag;
	public  int id;
	
	
	public TrailBag(int maxSize)
	{
		arr = new Trail[maxSize];
		userBag = new UserBag();
	}
	
	public void insert(Trail trail)
	{
		//ONLY AVAILABLE TO ADMIN
		
		if(userBag.hasAdminAccess())
		{
			ifFullResize();
			trail.setId(""+(id++));
			arr[nElem++] = trail;
		}
		
		else System.out.println("ADMIN ACCESS REQUIRED!");
		
	}
	
	//elipses
	public void insert(Trail ...trails)
	{
		for(Trail trail : trails)
		 insert(trail);
			
	}
	
	public void setUserBag(UserBag userBag)
	{
		this.userBag = userBag;
	}
	
	public Trail searchById(String id)
	{
		for(int i = 0; i < nElem; i++)
		{
			if(arr[i].getId().equals(id)) return arr[i];
		}
		
		return null;
	}
	
	public Trail[] searchByFilter(Predicate<Trail> predicate)
	{
		Trail[] searchResults = new Trail[nElem];
		int x = 0;
		
		for(int i = 0 ; i < nElem; i++)
		{
			if(predicate.test(arr[i])) searchResults[x++] = arr[i];
		}
		
		return Arrays.copyOf(searchResults, x);
	}
	
	public Trail[] searchByName(String name)
	{
		return searchByFilter(trail -> trail.getName().equals(name));
	}
	
	private int indexOf(Trail trail)
	{
		for(int i = 0; i < nElem; i++)
		{
			if(arr[i] == trail) return i;
		}
		
		return -1;
	}
	
	private void remove(Trail trail)
	{
		int idx = indexOf(trail);
		if (idx > -1) 
		{
			arr[idx] = arr[nElem - 1];
			arr[--nElem] = null;
		}
		
		removeResize();
	}
	
	private Trail find(Predicate<Trail> predicate)
	{
		for(int i = 0; i < nElem; i++)
		{
			if(predicate.test(arr[i])) return arr[i];
		}
		
		return null;
	}
	
	public void display()
	{
		
		System.out.print("[");
		for(int i = 0; i < nElem; i++)
		{
			System.out.print(arr[i].getName());
			if(i < nElem - 1) System.out.print(",");
		}
		
		System.out.print("]\n");
	}
	
	
	public void removeById(String id)
	{
		//ONLY AVAILABLE TO ADMIN
		if(userBag.hasAdminAccess())
		{
			remove(find(trail -> trail.getId().equals(id)));
		}
		else System.out.println("ADMIN ACCESS REQUIRED!");
		
	}
	
	public void resize(int maxSize)
	{
		arr = Arrays.copyOf(arr, maxSize);
	}
	
	public void ifFullResize()
	{
		if(isFull())
			resize(nElem*2);
	}
	
	public void removeResize()
	{
		if(nElem == arr.length/4)
			resize(arr.length/2);
	}
	
	public boolean isFull()
	{
		return nElem == arr.length;
	}
	
	
/*Iterable method implementation*/
	
	
	public Iterator<Trail> iterator()
	{
		return new TrailBagIterator();
	}
	
	public class TrailBagIterator implements Iterator<Trail>
	{
		int currIdx = 0;
		
		public boolean hasNext()
		{
			return currIdx < nElem;
		}
		
		public Trail next()
		{
			return arr[currIdx++];
		}
	}

	
}
	
