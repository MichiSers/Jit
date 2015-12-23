package de.oth.Jit;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mtree implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Mnode root;
	private List<File> files;

	public Mtree(Mnode root)
	{

		this.root = root;
		root.isRoot = true;
	}

	public void addElement(File path)
	{
		files = new ArrayList<File>();

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
		files = new ArrayList<File>();

		while (path != null)
		{
			files.add(path);
			path = path.getParentFile();
		}

		String names[] = files.get(files.size() - 1).toString().split("\\\\");
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
