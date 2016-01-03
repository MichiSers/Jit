package de.oth.Jit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a file or directory that is currently in the staging
 * area and not committed yet.
 * 
 * 
 * @author Michi
 *
 */
public class Mnode implements Serializable
{
	/**
	 * Variables
	 */
	private static final long serialVersionUID = 1L;

	// these are only used by the head node
	private boolean isRoot = false;
	private String message = "";

	// here is differentiated between a leaf and a child
	// for easier working
	private List<Mnode> children;
	private List<Mnode> leafs;

	// value of the file path
	private File dir;

	// hash code of the node
	private String hash;

	// name is the name of the file without its path
	private String name;

	// used just to create file paths before putting them into
	// the new File() method
	private String target;

	public Mnode(File dir)
	{
		children = new ArrayList<Mnode>();
		leafs = new ArrayList<Mnode>();
		this.dir = dir;
		name = dir.getName();
	}

	/**
	 * Checks if file is a leaf
	 * 
	 * @return true/false
	 */
	public boolean isLeaf()
	{
		return children.isEmpty() && leafs.isEmpty();
	}

	/**
	 * Adds an element to the tree by recursively adding directories from the
	 * top.
	 * 
	 * @param files
	 *            List of files that represent the paths of all directories that
	 *            need to be added
	 */
	public void addElement(List<File> files)
	{
		int index = files.size() - 1;
		Mnode currentChild = new Mnode(files.get(index));
		files.remove(index);
		index--;
		if (index < 0)
		{
			if (!(leafs.contains(currentChild)))
			{
				leafs.add(currentChild);
			}
			return;
		} else
		{
			if (!(children.contains(currentChild)))
			{
				children.add(currentChild);
				currentChild.addElement(files);
			} else
			{
				int i = children.indexOf(currentChild);
				children.get(i).addElement(files);
			}
		}
	}

	/**
	 * Removes a file from the tree, similar to adding it. Recursively checks if
	 * nodes don't have children and deletes them.
	 * 
	 * @param files
	 *            Files that represent the path like in 'add()'
	 */
	public void removeElement(List<File> files)
	{
		int index = files.size() - 1;
		Mnode currentNode = new Mnode(files.get(index));
		files.remove(index);
		index--;
		if (index < 0)
		{
			int i = leafs.indexOf(currentNode);
			leafs.remove(i);
		} else
		{
			int i = children.indexOf(currentNode);
			children.get(i).removeElement(files);
			if (children.get(i).children.isEmpty() && children.get(i).leafs.isEmpty())
			{
				children.remove(i);
			}
		}
	}

	/**
	 * Creates the hash objects in the jit/objects directory. Recursively starts
	 * from the bottom and works to the top. Creates a dummy file for folders
	 * that contains the hashes of the folders children.
	 */
	public void computeHash()
	{
		for (Mnode n : leafs)
		{
			n.computeHash();
		}

		for (Mnode n : children)
		{
			n.computeHash();
		}

		if (dir.isFile())
		{
			FileInputStream fileInputStream = null;

			byte[] bFile = new byte[(int) dir.length()];

			if (!dir.isFile())
			{
				return;
			}

			try
			{
				fileInputStream = new FileInputStream(dir);
				fileInputStream.read(bFile);
				fileInputStream.close();
				hash = HashAlgorithmExample.byteArrayToHexString(bFile);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			target = "./.jit/objects/" + hash;
			File result = new File(target);
			try
			{
				Files.write(result.toPath(), bFile);
				result.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			List<String> lines = new ArrayList<String>();
			if (!(isRoot()))
			{
				lines.add("Directory");
			} else
			{
				lines.add(getMessage());
			}
			for (Mnode k : leafs)
			{
				lines.add("File;" + k.hash + ";" + k.name);
			}

			for (Mnode k : children)
			{
				lines.add("Directory;" + k.hash + ";" + k.name);
			}

			target = "./.jit/staging/dummy";
			File dummy = new File(target);

			try
			{
				Files.write(dummy.toPath(), lines);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			FileInputStream fileInputStream = null;

			byte[] bFile = new byte[(int) dummy.length()];

			try
			{
				fileInputStream = new FileInputStream(dummy);
				fileInputStream.read(bFile);
				fileInputStream.close();
				hash = HashAlgorithmExample.byteArrayToHexString(bFile);
				dummy.delete();

			} catch (Exception e)
			{
				e.printStackTrace();
			}

			target = "./.jit/objects/" + hash;
			File result = new File(target);
			try
			{
				Files.write(result.toPath(), bFile);
				result.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Prints a node and its children. Just for testing purposes.
	 */
	public void printNode()
	{
		for (Mnode n : leafs)
		{
			n.printNode();
		}

		for (Mnode n : children)
		{
			n.printNode();
		}
	}

	/**
	 * Eclipse generated hashCode.
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

	/**
	 * Eclipse generated equals. I don't want to compare by hash so I left that
	 * out.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mnode other = (Mnode) obj;
		if (dir == null)
		{
			if (other.dir != null)
			{
				return false;
			}
		} else if (!dir.equals(other.dir))
		{
			return false;
		}
		return true;
	}

	/**
	 * toString
	 */
	@Override
	public String toString()
	{
		return name;
	}
	
	/**
	 * Getters and Setters Here
	 */

	public boolean isRoot()
	{
		return isRoot;
	}

	public void setRoot(boolean isRoot)
	{
		this.isRoot = isRoot;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
