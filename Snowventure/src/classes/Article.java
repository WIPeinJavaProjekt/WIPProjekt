package classes;

import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Article {

	public int ID;
	public String name;
	public String description;
	public ArrayList<ArticleVersion> versions;
	private int selectedversion;
	
	public Article(){}
	
	public Article(int ID)
	{
		this.ID = ID;
		selectedversion = 0;
	}
	
	public Article(int ID, String name, String description)
	{
		this.ID = ID;
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
	}
	
	public Article(String name, String description)
	{
		this.name = name;
		this.description = description;
		versions = new ArrayList<ArticleVersion>();
		selectedversion = 0;
	}
	
	public Article(Article a)
	{
		this.ID = a.ID;
		this.name = a.name;
		this.description = a.description;
		this.versions = new ArrayList<ArticleVersion>(a.versions);
		this.selectedversion = a.selectedversion;
	}
	
	
	public int GetSelectedVersion() {
		return selectedversion;
	}
	
	public void SetSelectedVersion(int s) {
		if(s>= 0&& s< versions.size())
			selectedversion = s;
	}
	
	public double GetPrice() {
		return versions.get(selectedversion).price;
	}
}
