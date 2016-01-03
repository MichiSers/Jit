package de.oth.Jit;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The tree storing the folders and files in the 
 * staging area before committing.
 * 
 * @author Michi
 *
 */
public class Mtree implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Mnode root;
	private List<File> files;

	public Mtree(Mnode root)
	{
		this.root = root;
		root.setRoot(true);
	}

	/**
	 * Adds a new element.
	 * Separates the file path into its sub directories.
	 * 
	 * @param path	The File to add
	 */
	public void addElement(File path)
	{
		files = new ArrayList<File>();

		while (path != null)
		{
			files.add(path);
			path = path.getParentFile();
		}
		root.addElement(files);
	}

	/**
	 * Remove a element.
	 * Separates the file path into its sub directories.
	 * 
	 * @param path	The File to remove
	 */
	public void removeElement(File path)
	{
		files = new ArrayList<File>();

		while (path != null)
		{
			files.add(path);
			path = path.getParentFile();
		}
		root.removeElement(files);
	}

	/**
	 * Creates the hash objects.
	 * 
	 * @param message	A message to put into the commit file
	 */
	public void computeHash(String message)
	{
		root.setMessage(message);
		root.computeHash();
	}

	/**
	 * Prints the tree
	 */
	public void print()
	{
		root.printNode();
	}

}
