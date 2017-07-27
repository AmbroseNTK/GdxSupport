package kiet.nguyentuan.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import kiet.nguyentuan.demo.BalloonPop.BalloonPop;
import kiet.nguyentuan.demo.CheesePlease.CheesePlease;
import kiet.nguyentuan.demo.Demo1;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new BalloonPop(), config);
	}
}
