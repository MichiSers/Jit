package de.oth.Jit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mtree
{
	Mnode root;
	
	public Mtree(Mnode root){
		
		this.root=root;
	}
	
	public void addElement(File path){
		List<File> files = new ArrayList<File>();
		
		while(path != null){
			files.add(path);
//			System.out.println(path.toString());
			path = path.getParentFile();
		}
		
		root.addElement(files);
	}
	
	public void computeHash(){
		root.computeHash();
	}
	
	public void print(){
		root.printNode();
	}
	
}
