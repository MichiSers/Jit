package de.oth.Jit;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Jit
{
	public void init(){
		
		File currentFolder = new File(".");
		File workingFolder = new File(currentFolder, ".jit");
		File objects = new File(workingFolder, "objects");
		File staging = new File(workingFolder, "staging");
		
		if(!workingFolder.exists()){
			workingFolder.mkdir();
			objects.mkdir();
			staging.mkdir();
		}
		Path path = FileSystems.getDefault().getPath(workingFolder.getPath());
		try
		{
			Files.setAttribute(path, "dos:hidden", true);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void add(){
		
	}
	
	public void remove(){
		
	}
	
	public void commit(){
		
	}
	
	public void checkout(){
		
	}
}
