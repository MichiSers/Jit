package de.oth.Jit;

import java.io.File;

public class Converter
{
	public static void main(String[] args){
		
//		 String path = args[0];
//		    // ... 
//		    File CP_file = new File(path);
		
//		ContentStream ct = new ContentStream();
//		ct.readFile();
		
		Jit jit = new Jit();
//		jit.init();
//		String path = "src/de/oth/Jit/ContentStream.java";
//		String path2 = "src/de/oth/convert.txt";
//		File arg = new File(path);
//		File arg2 = new File(path2);
//		jit.add(arg);
//		jit.add(arg2);
//		jit.commit("My Commit");
		
		String path = ".jit/objects/3cb7981519a6879e91a26780e75214288db46384";
		File a = new File(path);
		jit.checkout(a);
		
	}
}
