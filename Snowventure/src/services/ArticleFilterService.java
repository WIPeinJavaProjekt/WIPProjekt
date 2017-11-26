package services;
import java.util.ArrayList;
import java.io.*;


import classes.*;

/**
 *  Modelclass for Filtering Articlelists
 *  Obsolet auf Grund von verbesserten SELECT  
 */
/**
 * Beschreibung:
 * @author Ansprechpartner
 *
 */
public class ArticleFilterService {

	

	
	public static ArrayList<Article> FilterColor(String[] color, ArrayList<Article> articles){
		ArrayList<Article> newarticles = new ArrayList<Article>(articles);
		
		for(int i = 0; i < newarticles.size();  )
		{
			boolean contained= false;			
			
			for(int v = 0; v < newarticles.get(i).versions.size(); v++)
				for(ArticleColor col: newarticles.get(i).versions.get(v).colors)
					for(int c= 0; c< color.length;c++)
						if(col.getAcolid() == Integer.parseInt(color[c]) )
						{
							contained = true;
							break;
						}
			
			if(!contained)
				newarticles.remove(i);
			else
				i++;
		}
		return newarticles;
	}

	public static ArrayList<Article> FilterSize(String[] size, ArrayList<Article> articles){
		ArrayList<Article> newarticles = new ArrayList<Article>(articles);
		

		for(int i = 0; i < newarticles.size();  )
		{
			boolean contained= false;			
			
			for(int v = 0; v < newarticles.get(i).versions.size(); v++)
				for(String si: newarticles.get(i).versions.get(v).sizes)
					for(int s= 0; s< size.length;s++) {
						System.out.println("Vergleiche: "+ si +" mit "+size[s] );
					
						if(si.equals( size[s]) )
						{
							contained = true;
							break;
						}
					}
			if(!contained)
				newarticles.remove(i);
			else
				i++;
		}
		return newarticles;
	}
	
	public static ArrayList<Article> FilterPrice(double minprice, double maxprice, ArrayList<Article> articles){
		ArrayList<Article> newarticles = new ArrayList<Article>(articles);
		
		if(minprice ==-1)
			minprice =0;
		if(maxprice ==-1)
			maxprice = 100000;
		
		
		for(int i = 0; i < newarticles.size();  )
		{
			boolean contained= false;			
			
			for(int v= 0; v< newarticles.get(i).versions.size(); v++)
				if(newarticles.get(i).versions.get(v).price >= minprice &&newarticles.get(i).versions.get(v).price <= maxprice )
				{
					contained = true;
					break;
				}
			
			if(!contained)
				newarticles.remove(i);
			else
				i++;			
		}
		
		return newarticles;		
	}
	
	public static ArrayList<Article> FilterGender(String[] genders, ArrayList<Article> articles){
		ArrayList<Article> newarticles = new ArrayList<Article>(articles);
		
		for(int i = 0; i < newarticles.size();  )
		{
			boolean contained= false;			
			
			for(int g=0; g < genders.length;g++)
				if(newarticles.get(i).gender.indexOf(genders[g]) != -1)
				{
					contained = true;
					break;
				}
					
			if(!contained)
				newarticles.remove(i);
			else
				i++;	
		}
		
		
		return newarticles;	
	}
	
	public static ArrayList<Article> FilterManufacturer(String[] manufactures, ArrayList<Article> articles){
		ArrayList<Article> newarticles = new ArrayList<Article>(articles);
		
		for(int i = 0; i < newarticles.size();  )
		{
			boolean contained= false;
			for(int h = 0; h < manufactures.length;h++)
			{
				System.out.println("Vergleiche: "+newarticles.get(i).manufacturer +" mit "+manufactures[h] );
				if(newarticles.get(i).manufacturer.equals(manufactures[h]))
				{
					contained = true;
					break;
				}
			}
			System.out.println("Vergleich ist:"+contained);
			if(!contained)
				newarticles.remove(i);
			else
				i++;
		}
		
		return newarticles;
	}
	
	
	
	
}
