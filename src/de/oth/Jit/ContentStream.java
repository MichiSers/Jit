package de.oth.Jit;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

public class ContentStream implements Serializable
{
	FileInputStream fileInputStream = null;

	File file = new File("C:\\Users\\Michi\\workspace\\Jit\\Content.txt");
	byte[] bFile = new byte[(int) file.length()];
	
	public void showHash(byte[] content){
		String s = HashAlgorithmExample.byteArrayToHexString(content);
		
		System.out.println(s);
	}

	public void readFile()
	{

		try
		{
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			showHash(bFile);

			for (int i = 0; i < bFile.length; i++)
			{
				System.out.print((char) bFile[i]);
			}

			System.out.println("Done");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
