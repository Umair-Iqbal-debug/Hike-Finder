package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Hike implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 622391097681666060L;
	private String trailId;
	private Trail trail;
	private String trailName;
	private LocalDate date;
	private double duration;
	
	public Hike(String trailId, double duration,Trail trail)
	{
		this.trailId = trailId;
		this.date =  LocalDate.now();
		this.duration = duration;
		this.setTrail(trail);
	}

	public String getTrailId() 
	{
		return trailId;
	}

	public LocalDate getDate() 
	{
		return date;
	}

	public void setDate(LocalDate date) 
	{
		this.date = date;
	}

	public double getDuration() 
	{
		return duration;
	}

	public void setDuration(double duration) 
	{
		this.duration = duration;
	}

	@Override
	public String toString() 
	{
		return "Hike [trailId=" + trailId + ", date=" + date + ", duration=" + duration + "]";
	}

	public Trail getTrail() {
		return trail;
	}

	public void setTrail(Trail trail) {
		this.trail = trail;
	}

	public String getTrailName() {
		return trail.getName();
	}

	public void setTrailName(String trailName) {
		this.trailName = trailName;
	}
	
	
}
