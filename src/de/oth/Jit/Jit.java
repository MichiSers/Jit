package de.oth.Jit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Jit
{
	HashMap<Integer,Mnode> map = new HashMap<Integer,Mnode>();
	ArrayList<Mnode> children = new ArrayList<Mnode>();

	FileInputStream fileInputStream;
//	JitFile jitFile;
	Mnode head = new Mnode(new File("."));
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


//	public void add(File p)
//	{
//		// String base = "C:/Users/Michi/workspace/Jit/";
//		
//		ArrayList<Mnode> children = new ArrayList<Mnode>();
//
//		FileInputStream fileInputStream;
//		String hash;
//		File newFile;
//		File file = p;
//		byte[] bFile;
//
//		while (p != null)
//		{
//			bFile = new byte[(int) file.length()];
//
//			if (p.isFile())
//			{
//				try
//				{
//					fileInputStream = new FileInputStream(file);
//					fileInputStream.read(bFile);
//					fileInputStream.close();
//
//					System.out.println("Done");
//
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//
//				hash = HashAlgorithmExample.byteArrayToHexString(bFile);
//				children.add(new JitFile(hash));
//			}else if(p.isDirectory()){
//				
//				
//				newFile = new File("dummy");
////				Files.write(newFile, bytes, options)
//			}
//			// newFile = new File(".jit/staging/"+hash);
//			// try
//			// {
//			// newFile.createNewFile();
//			// } catch (IOException e)
//			// {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// }
//
//			p = p.getParentFile();
//		}
//
//	}

	public void remove()
	{

	}

	public void commit()
	{

	}

	public void checkout()
	{

	}
}
