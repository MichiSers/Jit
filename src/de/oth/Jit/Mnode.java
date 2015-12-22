package de.oth.Jit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mnode
{
	List<Mnode> children;
	List<Mnode> leafs;
	File dir;
	String hash;

	public Mnode(File dir)
	{
		children = new ArrayList<Mnode>();
		leafs = new ArrayList<Mnode>();
		this.dir = dir;
	}

	public boolean isLeaf()
	{
		return children.isEmpty() && leafs.isEmpty();
	}

	public void addElement(List<File> files)
	{
		int index = files.size() - 1;
		Mnode currentChild = new Mnode(files.get(index));
		files.remove(index);
		index--;
		if (index < 0)
		{
			leafs.add(currentChild);
			return;
		} else
		{
			children.add(currentChild);
			currentChild.addElement(files);
		}
	}
	
	public void computeHash(){
		
		if(isLeaf()){
			
		}
		
	}

	public void printNode()
	{
		System.out.println(this);
		
		for (Mnode n : leafs)
		{
			n.printNode();
		}
		
		for (Mnode n : children)
		{
			n.printNode();
		}
		
		
	}

	@Override
	public String toString()
	{

		return dir.toString();
	}
}
