package de.oth.Jit;

import java.io.File;

public interface NodeI
{
	public void setFile(File file);
	
	public File getFile();
	
	public NodeI getFather();
	
	public void setFather(JitDirectory father);
}
