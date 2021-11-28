package model;

import java.io.Serializable;

//i. public User searchByUsername(String username)


//ii. public void insert(User user)
//iii. public void removeByUserName (String username). This method can only be 
//used by the user himself or herself, or by an admin.

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

// DELETE EVERYTHING ONLY IMPLEMENT METHODS ASKED
//  KEEP A COPY OF A BAG CLASS WITH WORKING INSERT AND REMOVE AND RESIZABLE
// MUST KNOW COMPARATORS

public class UserBag implements Iterable<User> ,Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 956277053130746236L;
	private User[] arr = new User[1];
	private int nElem;
	private User loggedInUser;
	private boolean isLoggedIn;
	private HikingHistory hikingHistory;
	private static final String DEFAULT_ADMIN_USERNAME = "admin";
	private static final String DEFAULT_ADMIN_PASSWORD = "Admin123";
	private static final User ADMIN = new User(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD,true);

	
	
	public UserBag()
	{
		insert(ADMIN);
	}
	
	public UserBag(int maxSize)
	{
		this();
		arr = new User[maxSize];
	}
	
	
	public UserBag(User ...users )
	{
		this();
		for(User user: users)
		{
			insert(user);
		}
	}
	
	public int getCapacity()
	{
		return arr.length;
	}
	
	public boolean hasAdminAccess()
	{
		return isLoggedIn && loggedInUser.isAdmin();
	}
	
	public boolean login(String username, String password)
	{
		User user = searchByUsername(username);
				
		isLoggedIn = (user != null && user.getPassword().equals(password));
		loggedInUser = user;
		
		if(user == null) System.out.println(username + " does not exist");
		else if(!(user.getPassword().equals(password))) System.out.println("INCORRECT PASSWORD FOR " + username);
		 
		
		return isLoggedIn;
	}
	
	public void logout()
	{
		System.out.println(loggedInUser.getUsername() +  " was logged out");
		loggedInUser = null;
	}
	
	
	public void sort(Comparator<User> comparator)
	{
		int size = arr.length;
		User[] aux = Arrays.copyOf(arr, nElem);
		Arrays.sort(aux,comparator);
		
		arr = Arrays.copyOf(aux, size);
	}
	
	public boolean isEmpty()
	{
		return nElem == 0;
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
	
	public void insert(User user)
	{
		String username = user.getUsername();
		if(searchByUsername(username)== null)
		{
			ifFullResize();
			arr[nElem++] = user;
		}
		else System.out.println("The username: " + username + " is taken!");
	}
	
	public void insert(User ...users)
	{
		for(User user: users)
		{
			insert(user);
		}
	}
	
	
	public User delete(int idx)
	{
		User toBeRemoved = arr[idx];
		arr[idx] = arr[nElem-1];
		arr[--nElem] = null;
		return toBeRemoved;
	}
	
	public User search(Predicate<User> predicate)
	{
		for(int i = 0; i < nElem; i++)
		{
			if(predicate.test(arr[i])) return arr[i];
		}
		
		return null;
	}
	
	public int search(User user)
	{
		for(int i = 0; i < nElem; i++)
		{
			if(arr[i] == user) return i;
		}
		
		return -1;
	}
	
	public User searchByUsername(String username)
	{
		return search(user -> user.getUsername().equalsIgnoreCase(username));
	}
	
	
	private void remove(User user)
	{
		int idx = search(user);
		if(idx < 0){
			System.out.println(user.getUsername() + " does not exist");
			return;
		}
		delete(idx);
	}
	
	private User remove(Predicate<User> predicate)
	{
			User toBeRemoved = search(predicate);
			if(toBeRemoved == null) return null;
					
			if(isLoggedIn && (loggedInUser.isAdmin() || predicate.test(loggedInUser)))
			{
				if(toBeRemoved == loggedInUser) 
				{
					logout();
				}
				
				remove(toBeRemoved);
				System.out.println("Account with the username: " + toBeRemoved.getUsername() + " was deleted");
			}
			
			else 
			{
				 System.out.println("You must log in as an admin or the account you want to delete!");
			}
			
			removeResize();
			return toBeRemoved;
	}
	
	
	
	public void removeByName(String name)
	{
		 remove(user -> user.getUsername().equalsIgnoreCase(name));
	}
	
	
	public User getLoggedInUser() 
	{
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) 
	{
		this.loggedInUser = loggedInUser;
	}

	public boolean isLoggedIn() 
	{
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn)
	{
		this.isLoggedIn = isLoggedIn;
	}

	public HikingHistory getHikingHistory() 
	{
		return hikingHistory;
	}

	public void setHikingHistory(HikingHistory hikingHistory) 
	{
		this.hikingHistory = hikingHistory;
	}

	public void display()
	{
		
		System.out.print("[");
		for(int i = 0; i < nElem; i++)
		{
			System.out.print(arr[i].getUsername());
			if(i < nElem - 1) System.out.print(",");
		}
		
		System.out.print("]\n");
	}
	
	
	/*Iterable method implementation*/
	
	
	public Iterator<User> iterator()
	{
		return new UserBagIterator();
	}
	
	public class UserBagIterator implements Iterator<User>
	{
		int currIdx = 0;
		
		public boolean hasNext()
		{
			return currIdx < nElem;
		}
		
		public User next()
		{
			return arr[currIdx++];
		}
	}
}

