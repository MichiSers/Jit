package de.oth.Jit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mtree
{
	Mnode root;

	public Mtree(Mnode root)
	{

		this.root = root;
		root.isRoot = true;
	}

	public void addElement(File path)
	{
		List<File> files = new ArrayList<File>();

		while (path != null)
		{
			files.add(path);
			// System.out.println(path.toString());
			path = path.getParentFile();
		}

		root.addElement(files);
	}

	public void removeElement(File path)
	{
		List<File> files = new ArrayList<File>();

		while (path != null)
		{
			files.add(path);
			// System.out.println(path.toString());
			path = path.getParentFile();
		}

		String names[] = files.get(files.size() - 1).toString().split("\\\\");
//		for(File f : files){
//			System.out.println(f.toString());
//		}
//		for (String s : names)
//		{
//			System.out.println(s);
//		}
		root.removeElement(files);

	}

	public void computeHash(String message)
	{
		root.message = message;
		root.computeHash();
	}

	public void print()
	{
		root.printNode();
	}

}
