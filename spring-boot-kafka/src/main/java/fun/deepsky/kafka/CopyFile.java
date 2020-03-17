package fun.deepsky.kafka;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFile {

	public static void main(String[] args) throws IOException {
		File dirFile = new File("/Users/zhaoxiaojie/Music/QQ音乐");
		//File dirFile = new File("/Users/zhaoxiaojie/Library/Containers/com.tencent.QQMusicMac/Data/Library/Application Support/QQMusicMac/iQmc");
		if(dirFile.isDirectory()){
			File files[]  = dirFile.listFiles();
			for(File file:files) {
				//System.out.println(file.getName());
				if(file.isDirectory() && !file.getName().equals("Music") && !file.getName().startsWith(".") 
						//&& (file.getName().toLowerCase().endsWith(".flac")||file.getName().toLowerCase().endsWith(".mp3"))
						) {
					File file_new[]  = file.listFiles();
					for(File f:file_new) {
						File reFile = new File("/Volumes/deepsky/Music/"+f.getName());
						if(!reFile.exists()) {
							System.out.println(f.getName()+"----不存在，正在创建...");
							reFile.createNewFile();
						}else {
							System.out.println(f.getName()+"----已存在");
							continue;
						}
						copyFileUsingFileStreams(f,reFile);
						
					}
				}
			}
		}
	}
	
	private static void copyFileUsingFileStreams(File source, File dest)
	        throws IOException {    
	    InputStream input = null;    
	    OutputStream output = null;    
	    try {
	           input = new FileInputStream(source);
	           output = new FileOutputStream(dest);        
	           byte[] buf = new byte[1024];        
	           int bytesRead;        
	           while ((bytesRead = input.read(buf)) != -1) {
	               output.write(buf, 0, bytesRead);
	           }
	    } finally {
	        input.close();
	        output.close();
	    }
	}
}
