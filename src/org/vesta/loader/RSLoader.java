/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vesta.loader;

import java.applet.Applet;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.jar.JarFile;

/**
 *
 * @author James
 */
public class RSLoader {
	private Applet applet;
	private Class<?> appletSub;
	private JarClassLoader classLoader;

	public RSLoader(int world, int game, boolean signed) throws Exception {
		String prefix="";
		//GameJar gameJar = new GameJar();

		File oldSchool = new File("oldschool.jar");
		File classic = new File("classic.jar");
		File rs4 = new File("rs4.jar");
		//boolean ex = (f.exists()) ? true:false;

		/*
		if (f.exists()) {
			FileInputStream fin = new FileInputStream("game.jar");
			ObjectInputStream in = new ObjectInputStream(fin);
			GameJar o = (GameJar) in.readObject();
			gameJar = o;
			in.close();
		}
		 */

		if (game==0) {
			prefix = "classic";
			if (!classic.exists()) {
				URL website = (new URL("http://" + prefix + world + ".runescape.com/rsclassic_-1091943135.jar!/"));
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("classic.jar");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
			}
			//Runescape Classic Class to Load from Jar
			File file  = new File("classic.jar");
			URL url = file.toURI().toURL();
			URL[] urls = new URL[]{url};
			ClassLoader cl = new URLClassLoader(urls);
			appletSub = cl.loadClass("client");
		}
		else if (game==1) {
			prefix="oldschool";
			if (!oldSchool.exists()) {
				URL website = (new URL("http://" + prefix + world + ".runescape.com/gamepack_5462530.jar!/"));
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("oldschool.jar");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
			}

			//Runescape 2007 Class to Load from Jar
			File file  = new File("oldschool.jar");
			URL url = file.toURI().toURL();
			URL[] urls = new URL[]{url};
			ClassLoader cl = new URLClassLoader(urls);
			appletSub = cl.loadClass("client");
		} else {
			prefix="world";
			if (!rs4.exists()) {
				URL website = new URL("http://" + prefix + world + ".runescape.com/gamepack8GkJ13TpYEY1cPbVYa5/Ru7mnQT+BM0B_9629289.jar!/");
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("RS4.jar");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
			}
			File file  = new File("RS4.jar");
			URL url = file.toURI().toURL();
			URL[] urls = new URL[]{url};
			ClassLoader cl = new URLClassLoader(urls);
			appletSub = cl.loadClass("Rs2Applet");
		}

		/*
		// Write out the Jar
		FileOutputStream fout = new FileOutputStream("game.jar");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		System.out.println("WRITING TO JARFILE");
		oos.writeObject(gameJar.jarFileInfo());
		oos.close();
		 */

		applet = (Applet) appletSub.newInstance();
		applet.setStub(new RSStub(prefix, applet, world, game));
		applet.init();
		applet.start();
	}

	public Applet getApplet() {
		return applet;
	}

	public JarClassLoader getClassLoader() {
		return classLoader;
	}

}
