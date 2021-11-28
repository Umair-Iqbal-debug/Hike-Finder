package model;

import java.util.Arrays;

import model.Trail.Level;
import model.Trail.TrailType;
import utils.BackUpRestoreTools;

public class BagDemo 
{
	public static void main(String[] args) 
	{
		User user1 = new User("A","Admin123",false);
		User user2 = new User("B");
		User user3 = new User("C");
		User user4 = new User("D");
		User user5 = new User("E");
		
	
		
		UserBag bag = new UserBag(user1,user2,user3,user4,user5);
		bag.login("admin", "Admin123");
		
		
//		for(User user: bag) {
//			System.out.println(user.getUsername());
//		}
		
		/*TrailBag testing begins here*/
		
		Trail trail1 = new Trail("A",200);
		Trail trail2 = new Trail("B",190);
		Trail trail3 = new Trail("C",300);
		Trail trail4 = new Trail("D",60);
		TrailBag trailBag = new TrailBag(10);
		trailBag.setUserBag(bag);
		trailBag.insert(trail1,trail2,trail3,trail4);
		
		trail1.setType(TrailType.LOOP);
		System.out.println(trail1.getType());
		
		trailBag.display(); // I know you asked us not to do it but I only did it to test the bag
		
		trailBag.removeById(trail1.getId());
		
		trailBag.display();
		
		Trail[] res = trailBag.searchByFilter(trail -> trail.getLength() > 100);
		System.out.println("All trails over 100 length");
		
		System.out.println(Arrays.toString(res));
		
	}
}
