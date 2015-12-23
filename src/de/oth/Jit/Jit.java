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

	public Jit()
	{
		tree = new Mtree(head);
	}

	public void init()
	{

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

		tree = getTree();

		tree.addElement(p);


		writeTree();


	}

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
			return getTree;
		} catch (ClassNotFoundException | IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		getTree = new Mtree(head);
		return getTree;
		

	}

	public void writeTree()
	{
		try
		{
			String outPath = ".jit/staging/tree.ser";
			File output = new File(outPath);
			FileOutputStream fout = new FileOutputStream(output);
			ObjectOutputStream out = new ObjectOutputStream(fout);

			out.writeObject(tree);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	public void checkout(File file)
	{

		String basePath = "Test";
		File dummy = new File(basePath);
		if (dummy.exists())
		{
			deleteFolder(dummy);
		}
		recreateFile(file, basePath);

	}

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
		{ // some JVMs return null for empty dirs
			for (File f : files)
			{
				// if (f.getName() != jit.getName())
				// {
				if (f.isDirectory())
				{
					deleteFolder(f);
				} else
				{
					f.delete();
				}
				// }
			}
		}
		folder.delete();
	}

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
				System.out.println(current.mkdirs());

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
