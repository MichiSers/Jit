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
		jit.init();
		String path = "src/de/oth/Jit/convert.txt";
		File arg = new File(path);
		jit.add(arg);
		
	}
}
