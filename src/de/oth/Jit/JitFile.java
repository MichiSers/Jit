package de.oth.Jit;

import java.io.File;

public class JitFile implements NodeI
{
	JitDirectory father;
	File file;

	public JitFile()
	{

	}

	@Override
	public NodeI getFather()
	{
		return father;
	}

	@Override
	public void setFather(JitDirectory father)
	{
		this.father = father;

	}

	@Override
	public void setFile(File file)
	{
		this.file = file;

	}

	@Override
	public File getFile()
	{
		return file;
	}

	@Override
	public String toString()
	{
		return file.toString();
	}
}
