package model;

import java.io.Serializable;

public class Trail implements Serializable
{
	public enum Level{
		EASY,MODERATE,HARD
	}
	
	public enum TrailType{
		LOOP,OUT_AND_BACK,POINT_TO_POINT
	}
	
	private String id;
	private static int idNumber;
	private String name;
	Address address;
	double length;
	double elevation_gain;
	private TrailType type;
	private Level difficulty_level;
	
	public Trail()
	{
		id = (idNumber++) + "";
	}
	
	
	public Trail(String name, Address address, double length, double elevation_gain,TrailType type,Level difficulty_level) 
	{
		this();
		this.name = name;
		this.address = address;
		this.length = length;
		this.elevation_gain = elevation_gain;
		this.type = type;
		this.difficulty_level = difficulty_level;
	}
	
	public String getType() 
	{
		return type.toString();
	}


	public void setType(TrailType type) 
	{
		this.type = type;
	}


	public Level getDifficulty_level() 
	{
		return difficulty_level;
	}


	public void setDifficulty_level(Level difficulty_level) 
	{
		this.difficulty_level = difficulty_level;
	}


	public Trail(String name,double length)
	{
		this();
		this.name = name;
		this.length = length;
	}
	
	
	
	public String getId() 
	{
		return id;
	}


	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Address getAddress() 
	{
		return address;
	}

	public void setAddress(Address address) 
	{
		this.address = address;
	}

	public double getLength() 
	{
		return length;
	}

	public void setLength(double length) 
	{
		this.length = length;
	}

	public double getElevation_gain() 
	{
		return elevation_gain;
	}

	public void setElevation_gain(double elevation_gain) 
	{
		this.elevation_gain = elevation_gain;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	
	public static TrailType getTrailTypeFromString(String trailType)
	{
		if(trailType.equals("Loop"))
			return TrailType.LOOP;
		else if(trailType.equalsIgnoreCase("Point to point"))
			return TrailType.POINT_TO_POINT;
		return TrailType.OUT_AND_BACK;
	}


	@Override
	public String toString() {
		return "Trail [id=" + id + ", name=" + name + ", address=" + address + ", length=" + length
				+ ", elevation_gain=" + elevation_gain + ", type=" + type + ", difficulty_level=" + difficulty_level
				+ "]";
	}
	
	
//	@Override
//	public String toString()
//	{
//		return "[" + name + "," + length + "]";
//	}

}
