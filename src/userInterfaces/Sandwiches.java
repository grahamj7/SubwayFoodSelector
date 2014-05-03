package userInterfaces;

import java.util.LinkedList;

public interface Sandwiches 
{
	public void setTransID(int id);
	public int getTransID();
	
	public void setSize(int s);
	public int getSize();
	
	public void setBread(String b);
	public String getBread();
	
	public void setMeat(String m);
	public String getMeat();
	
	public void setCheese(String c);
	public String getCheese();
	
	public void setToasted(boolean t);
	public boolean getToasted();
	
	public void setOrderNumber(int oN);
	public int getOrderNumber();
	
	public void addVegetables(String veg);
	public LinkedList<String> getVegetables();
	
	public void addExtras(String ex);
	public LinkedList<String> getExtras();
	
	public void addSauce(String sa);
	public LinkedList<String> getSauces();	

	public void setVegetables(LinkedList<String> veg);
	public void setSauces(LinkedList<String> sauce);
	public void setExtras(LinkedList<String> ex);
}
