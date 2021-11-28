package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import model.Address;
import model.Trail;
import model.Trail.Level;
import model.Trail.TrailType;
import model.TrailBag;
import model.User;
import model.UserBag;

public class ImportExportTools 
{
	public static void importUsers(UserBag userBag)
	{
		try {
			File file = new File("data/users.txt");
			if(file.exists())
			{
				Scanner scanner = new Scanner(file);
				while(scanner.hasNextLine())
				{
					String line = scanner.nextLine();
					User user = getUser(line);
					userBag.insert(user);
				}
				BackUpRestoreTools.backupUserBag(userBag);
				scanner.close();
			}
		}
			
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	
	
	public static void exportUsers(UserBag userBag)
	{
		FileWriter fw;
		
		try 
		{
			fw = new FileWriter("data/users.txt",true);
			PrintWriter pw = new PrintWriter(fw);
			
			for(User user: userBag)
			{
				pw.println(user.getFirstName() + " " + user.getLastName() + " " + user.getEmail() + " " + user.getUsername() + " " + 
			user.getPassword() + " " + user.getPicture());
			}
			
			pw.close();
		}
		
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void importTrails(TrailBag trailBag)
	{
		try {
			File file = new File("data/trails.txt");
			if(file.exists())
			{
				Scanner scanner = new Scanner(file);
				while(scanner.hasNextLine())
				{
					String line = scanner.nextLine();
					String[] tokens = line.split("; ");
					Address address = getAddress(tokens[1]);
					Trail trail = new Trail(tokens[0],address,Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]),
							TrailType.valueOf(tokens[4]),Level.valueOf(tokens[5]));
					trailBag.insert(trail);
				}
				
				BackUpRestoreTools.backupTrailBag(trailBag);
				
				scanner.close();
			}
		}
			
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
	}
	
	private static User getUser(String line)
	{
		String[] tokens = line.split(" ");
		return new User(tokens[0],tokens[1],tokens[2],tokens[3],tokens[4],tokens[5]);
	}
	
	private static Address getAddress(String line)
	{
		String[] tokens = line.split(", ");
		Address address = new Address(tokens[0],tokens[1],tokens[2],tokens[3],tokens[4]);
		return address;
	}
}
