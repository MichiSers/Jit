package de.oth.Jit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mnode
{
	List<Mnode> children;
	List<Mnode> leafs;
	File dir;
	String hash;

	public Mnode(File dir)
	{
		children = new ArrayList<Mnode>();
		leafs = new ArrayList<Mnode>();
		this.dir = dir;
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
			}else{
				int i = children.indexOf(currentChild);
				children.get(i).addElement(files);
			}
		}
	}

	public void computeHash()
	{
		
		

		if (isLeaf())
		{
			FileInputStream fileInputStream = null;

			byte[] bFile = new byte[(int) dir.length()];
			
				try
				{
					fileInputStream = new FileInputStream(dir);
					fileInputStream.read(bFile);
					fileInputStream.close();
					hash = HashAlgorithmExample.byteArrayToHexString(bFile);
					System.out.println(hash);

					for (int i = 0; i < bFile.length; i++)
					{
						System.out.print((char) bFile[i]);
					}
					
					System.out.println("\n");

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}else{
				String out = dir.getName();
//				String a = "C:/Users/Michi/workspace/Jit/.jit/objects/NEWFILE.txt";
				String a = "./.jit/objects/NEWFILE.txt";
				File n = new File(a);
				try
				{
					n.createNewFile();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		for (Mnode n : leafs)
		{
			n.computeHash();
		}

		for (Mnode n : children)
		{
			n.computeHash();
		}

	}

	public void printNode()
	{
		System.out.println(this);

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
		return dir.toString();
	}
}
