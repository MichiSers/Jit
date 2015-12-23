package de.oth.Jit;

import java.io.File;
import java.io.IOException;

public class Converter
{
	public static void main(String[] args)
	{
		Jit jit = new Jit();
		if (args.length > 0)
		{
			switch (args[0])
			{
			case "init":
				jit.init();
				break;

			case "add":
				if (args.length < 2)
				{
					System.out.println("No parameter");
					return;
				} else if (args.length > 2)
				{
					System.out.println("Too many parameters");
					return;
				}
				File add = new File(args[1]);
//				System.out.println(add);
				jit.add(add);
				break;

			case "remove":
				if (args.length < 2)
				{
					System.out.println("No parameter");
					return;
				} else if (args.length > 2)
				{
					System.out.println("Too many parameters");
					return;
				}
				File remove = new File(args[1]);
				jit.remove(remove);
				break;
				
			case "commit":
				if (args.length < 2)
				{
					System.out.println("No parameter");
					return;
				} else if (args.length > 2)
				{
					System.out.println("Too many parameters");
					return;
				}
				jit.commit(args[1]);
				break;
				
			case "checkout":
				if (args.length < 2)
				{
					System.out.println("No parameter");
					return;
				} else if (args.length > 2)
				{
					System.out.println("Too many parameters");
					return;
				}
				File checkout = new File(args[1]);
				
				jit.checkout(checkout);
				
			default:
				System.out.println("Not a jit command");
			}
		}

//		jit.init();

//		String path = "src/de/oth/Jit/ContentStream.java";
//		String path2 = "src/de/oth/R/convert.txt";
//		File arg = new File(path);
//		File arg2 = new File(path2);
//		jit.add(arg);
//		jit.add(arg2);
//		jit.commit("My Commit");
//		jit.remove(arg2);

		// String path =
		// ".jit/objects/3cb7981519a6879e91a26780e75214288db46384";
		// File a = new File(path);
		// jit.checkout(a);

	}
}
