package org.vesta.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.jar.JarFile;

import javax.crypto.Cipher;

public class GameJar implements Serializable {
	private static final long serialVersionUID = -7293643039643589869L;
	private transient JarFile jarFile;
    
	public GameJar() {
		
	}
	
	public void setJarFile (JarFile file) {
		jarFile = file;
	}
	public JarFile getJarFile (){
		return jarFile;
	}
	
	public Class<? extends JarFile>  jarFileInfo() {
		return jarFile.getClass();
	}
	
	public void setClass () {
		
	}
	
	public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        //ObjectOutputStream o = new ObjectOutputStream(b);
        //o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
}