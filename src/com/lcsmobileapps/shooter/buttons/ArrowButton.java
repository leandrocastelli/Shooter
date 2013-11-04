package com.lcsmobileapps.shooter.buttons;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.lcsmobileapps.shooter.ScreenItem;

public class ArrowButton extends ScreenItem {

	public ArrowButton(Bitmap bitmap, int x, int y) {
		super();
		this.bitmap = bitmap;
		spriteHeight = bitmap.getHeight();
		spriteWidth = bitmap.getWidth();
		sourceRect = new Rect (0,0,spriteWidth,spriteHeight);
		this.x = x;
		this.y = y;
		
		
	}
	
		
}
