package de.oth.Jit;

import java.io.File;


/**
 * Handles commands, some basic parameter checks.
 * Delegates to 'Jit' class.
 * 
 * @author Michi
 */
public class CommandHandler
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
	}
}
