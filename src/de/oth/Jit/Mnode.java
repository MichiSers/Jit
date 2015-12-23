package de.oth.Jit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Mnode
{
	boolean isRoot = false;
	String message = "";
	List<Mnode> children;
	List<Mnode> leafs;
	File dir;
	String hash;
	String target;
	String name;

	public Mnode(File dir)
	{
		children = new ArrayList<Mnode>();
		leafs = new ArrayList<Mnode>();
		this.dir = dir;

		name = dir.getName();
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

	public void removeElement(List<File> files)
	{
		int index = files.size() - 1;
		// System.out.println(files.get(index));
		Mnode currentNode = new Mnode(files.get(index));
		files.remove(index);
		index--;
		if (index < 0)
		{
			int i = leafs.indexOf(currentNode);
			Mnode org = leafs.get(i);
			System.out.println(org);
			System.out.println("removing " + leafs.get(i));
			leafs.remove(i);
		} else
		{
			int i = children.indexOf(currentNode);
			Mnode org = children.get(i);
			System.out.println(org);
			children.get(i).removeElement(files);
			if (children.get(i).children.isEmpty() && children.get(i).leafs.isEmpty())
			{
				System.out.println("removing " + children.get(i));
				children.remove(i);
			}
		}
	}

	public void computeHash()
	{
		System.out.println("Computing File " + dir.toString());

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
				System.out.println(dir + "Is no file");
				return;
			}

			try
			{
				fileInputStream = new FileInputStream(dir);
				fileInputStream.read(bFile);
				fileInputStream.close();
				hash = HashAlgorithmExample.byteArrayToHexString(bFile);
				System.out.println("Leaf: " + hash);

				System.out.println("\n");

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
			if (!(isRoot))
			{
				lines.add("Directory");
			} else
			{
				lines.add(message);
			}
			for (Mnode k : leafs)
			{
				// System.out.println(n.hash);
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
				System.out.println("Child: " + hash);
				System.out.println("\n");

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

	public void printNode()
	{
		System.out.println(dir);

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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

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
		// if (hash == null)
		// {
		// if (other.hash != null)
		// return false;
		// } else if (!hash.equals(other.hash))
		// return false;
		return true;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
