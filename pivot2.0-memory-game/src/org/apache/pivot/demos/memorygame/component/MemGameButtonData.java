package org.apache.pivot.demos.memorygame.component;

import org.apache.pivot.wtk.content.ButtonData;
import org.apache.pivot.wtk.media.Image;

public class MemGameButtonData extends ButtonData{

	private Image defaultImage;
	private Image buttonImage;	
	
	public MemGameButtonData(Image defaultImage, Image buttonImage){
		
		super();
		this.buttonImage = buttonImage;
		this.defaultImage = defaultImage;		
		setDefaultURL();		
	}
	
	public void setDefaultURL(){		
		this.setIcon(defaultImage);
	}
	
	public void setButtonURL(){		
		this.setIcon(buttonImage);
	}	
	
	public Image getButtonURL(){		
		return this.getIcon();
	}
}
