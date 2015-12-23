package de.oth.Jit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Jit
{
	HashMap<Integer, Mnode> map = new HashMap<Integer, Mnode>();
	ArrayList<Mnode> children = new ArrayList<Mnode>();

	FileInputStream fileInputStream;
	// JitFile jitFile;
	Mnode head = new Mnode(new File("commit"));
	Mnode Mnode;
	String hash;
	File newFile;
	File file;
	byte[] bFile;
	Mtree tree;

	List<File> list = new ArrayList<File>();

	public void init()
	{

		tree = new Mtree(head);
		File currentFolder = new File(".");
		File workingFolder = new File(currentFolder, ".jit");
		File objects = new File(workingFolder, "objects");
		File staging = new File(workingFolder, "staging");

		if (!workingFolder.exists())
		{
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

	public void add(File p)
	{
		// String base = "C:/Users/Michi/workspace/Jit/";
		tree.addElement(p);

		tree.print();

	}

	public void remove()
	{

	}

	public void commit(String message)
	{
		tree.computeHash(message);
	}

	public void checkout(File file)
	{
		
		
		String[] lines = readFile(file);
		for(int i = 0; i<lines.length; i++){
			recreateFile(lines[i]);
			System.out.println(lines[i]);
		}
		
	}
	
	public void recreateFile(String line){
		String details[] = line.split(";");
		String base = "./Test";
		String target = base+"/"+details[2];
		System.out.println(target);
		File file = new File(target);
		if(details[0].equals("Directory")){
			System.out.println("is dir");
			file.mkdirs();
		}
	}
	
	public String[] readFile(File file){
		boolean firstLine = true;
		StringBuilder contents = new StringBuilder();
		try{
		BufferedReader input = new BufferedReader(new FileReader(file));
		try{
			String line = null;
			
			while((line = input.readLine()) != null){
				if(firstLine){
					firstLine = false;
				}else{
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
				}
			}
		}finally{
			input.close();
		}
		}catch (IOException ex){
			ex.printStackTrace();
		}
		
		String[] lines = contents.toString().split("\n");
		
		return lines;
	}
}
