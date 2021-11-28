package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class HikingHistory implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9007612699810154797L;
	private ArrayList<Hike> hikes;
	
	public HikingHistory()
	{
		hikes =  new ArrayList<Hike>();
		
	}
	
	public Hike search(Predicate<Hike> predicate)
	{
		for(Hike hike: hikes)
		{
			if(predicate.test(hike)) return hike;
		}
		
		return null;
	}
	
	public ArrayList<Hike> searchByFilter(Predicate<Hike> predicate)
	{
		ArrayList<Hike> res = new ArrayList<>();
		
		hikes.forEach(hike ->{if(predicate.test(hike)) res.add(hike);});
		
		return res;
	}
	
	public void add(Hike hike) 
	{
		hikes.add(hike);
	}
	
	public void display()
	{
		for(Hike hike: hikes)
		{
			System.out.println(hike);
		}
		
		System.out.println();
	}
	
	public ArrayList<Hike> getHikes()
	{
		return hikes;
	}
}
