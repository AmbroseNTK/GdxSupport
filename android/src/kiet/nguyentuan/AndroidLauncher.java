package kiet.nguyentuan;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import kiet.nguyentuan.demo.BalloonPop.BalloonPop;
import kiet.nguyentuan.demo.CheesePlease.CheesePlease;
import kiet.nguyentuan.demo.Demo1;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BalloonPop(), config);
	}
}
