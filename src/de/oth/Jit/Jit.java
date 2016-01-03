package de.oth.Jit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

/**
 * The CommandHandler class delegates to this class. Contains all 'jit'
 * commands.
 * 
 * The head node of the tree used to store the data is created automatically.
 * 
 * @author Michi
 *
 */
public class Jit
{

	/**
	 * Variables
	 */

	// for the tree
	private Mnode head = new Mnode(new File("commit"));
	private Mtree tree;

	// create jit directory
	File currentFolder;
	File workingFolder;
	File objects;
	File staging;

	public Jit()
	{
		tree = new Mtree(head);
		
	}

	/**
	 * Initializes the jit directory and makes it hidden.
	 */
	public void init()
	{

		currentFolder = new File(".");
		workingFolder = new File(currentFolder, ".jit");
		objects = new File(workingFolder, "objects");
		staging = new File(workingFolder, "staging");

		if (!workingFolder.exists())
		{
			workingFolder.mkdir();
			objects.mkdir();
			staging.mkdir();
		}

		try
		{
			Files.setAttribute(workingFolder.toPath(), "dos:hidden", true);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writeTree();
	}

	/**
	 * Reads the serializes Tree and writes it back in the end.
	 * 
	 * @param p
	 *            the File to add
	 */
	public void add(File p)
	{
		tree = getTree();
		tree.addElement(p);
		writeTree();
	}

	/**
	 * Reads the serializes Tree and writes it back in the end.
	 * 
	 * @param p
	 *            the File to remove
	 */
	public void remove(File p)
	{
		tree = getTree();
		tree.removeElement(p);
		writeTree();

	}

	public void commit(String message)
	{
		tree = getTree();
		tree.computeHash(message);
		writeTree();
	}

	/**
	 * Deletes all other directories and recreates from the Commit file. +++
	 * Dont't give files that are not the commit file as parameter, don't have a
	 * mechanism for checking other files +++
	 * 
	 * @param file
	 *            File to start recreating
	 */
	public void checkout(File file)
	{

		String basePath = ".";
		File dummy = new File(basePath);
		if (dummy.exists())
		{
			deleteFolder(dummy);
		}
		recreateFile(file, basePath);

	}

	/**
	 * Fetches the tree from the ser file.
	 * 
	 * @return the tree as MTree object
	 */
	public Mtree getTree()
	{
		Mtree getTree;
		try
		{
			String treePath = ".jit/staging/tree.ser";
			FileInputStream fin = new FileInputStream(treePath);
			BufferedInputStream bin = new BufferedInputStream(fin);
			ObjectInputStream in = new ObjectInputStream(bin);
			getTree = (Mtree) in.readObject();
			in.close();
			return getTree;
		} catch (ClassNotFoundException | IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// in case there is an error, but does not work i think
		getTree = new Mtree(head);
		return getTree;

	}

	/**
	 * Writes the current tree to a tree.ser file in the staging area
	 */
	public void writeTree()
	{
		try
		{
			String outPath = ".jit/staging/tree.ser";
			File output = new File(outPath);
			FileOutputStream fout = new FileOutputStream(output);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(tree);
			out.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the given folder and all other files in it except for the .jit
	 * folder. Here only used to delete the workspace.
	 * 
	 * @param folder
	 *            The folder to delete
	 */
	public static void deleteFolder(File folder)
	{
		String jitPath = ".jit";
		File jit = new File(jitPath);
		File[] files = folder.listFiles();
		files = folder.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				if (pathname.getName().equals(jit.getName()))
				{
					return false;
				} else
					return true;
			}

		});
		if (files != null)
		{
			for (File f : files)
			{
				if (f.isDirectory())
				{
					deleteFolder(f);
				} else
				{
					f.delete();
				}
			}
		}
		folder.delete();
	}

	/**
	 * Creates the directory by following the content of a commit file in
	 * jit/objects.
	 * 
	 * Works recursively
	 * 
	 * @param file
	 *            File that is created
	 * @param basePath
	 *            Path that is updated when finished
	 */
	public void recreateFile(File file, String basePath)
	{
		String[] lines = readFile(file);

		for (int i = 0; i < lines.length; i++)
		{
			String details[] = lines[i].split(";");
			details[2] = details[2].trim();

			if (details[0].equals("Directory"))
			{
				File current = new File(basePath, details[2]);
				current.mkdirs();
				basePath = basePath + "/" + details[2];
				String nextPath = ".jit/objects/" + details[1];
				File next = new File(nextPath);
				recreateFile(next, basePath);

			} else if (details[0].equals("File"))
			{
				String oldPath = ".jit/objects/" + details[1];
				String newPath = basePath + "/" + details[2];
				File newFile = new File(newPath);
				File oldFile = new File(oldPath);
				try
				{
					Files.copy(oldFile.toPath(), newFile.toPath());
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Reads a file and puts each line into an array of strings.
	 * 
	 * @param file
	 *            The file to read
	 * @return String[] with file lines as content
	 */
	public String[] readFile(File file)
	{
		boolean firstLine = true;
		StringBuilder contents = new StringBuilder();
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(file));
			try
			{
				String line = null;
				while ((line = input.readLine()) != null)
				{
					if (firstLine)
					{
						firstLine = false;
					} else
					{
						contents.append(line);
						contents.append(System.getProperty("line.separator"));
					}
				}
			} finally
			{
				input.close();
			}
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		String[] lines = contents.toString().split("\n");
		return lines;
	}
}
