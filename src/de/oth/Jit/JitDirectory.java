package de.oth.Jit;

import java.io.File;
import java.util.ArrayList;

public class JitDirectory implements NodeI
{
	JitDirectory father;

	File file;

	public JitDirectory()
	{

	}

	ArrayList<JitFile> fileChildren = new ArrayList<JitFile>();
	ArrayList<JitDirectory> directoryChildren = new ArrayList<JitDirectory>();

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
	public String toString(){
		return file.toString();
	}
}
