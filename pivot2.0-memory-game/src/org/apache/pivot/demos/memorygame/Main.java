package org.apache.pivot.demos.memorygame;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.demos.memorygame.component.MemGameButtonData;
import org.apache.pivot.util.concurrent.TaskExecutionException;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.media.Image;


public class Main implements Application, ButtonPressListener {
	
	private BXMLSerializer bxmlSerializer ;
	private String defaultImage = "img/default.png";
	private Image defaultImg;
	private boolean firstClick = true;
	private boolean right = true;	
	private PushButton buttonOne;
	private PushButton buttonTwo;
	private Button clickedButtonOne;
	private Button clickedButtonTwo;
	private int count;
	
    private Window window = null;
    private Image[] images18;
    private Image[] images36;
    private PushButton[] buttons;
 
    @Override
    public void startup(Display display, Map<String, String> properties)
    
        throws Exception {
        bxmlSerializer = new BXMLSerializer();
        window = (Window) bxmlSerializer.readObject(getClass().getResource("memgame.bxml"));
        defaultImg = Image.load(getClass().getResource(defaultImage));
        buttons = new PushButton[36];
        
        for(int aux = 0; aux < 36; aux++){
        	buttons[aux] = (PushButton) bxmlSerializer.getNamespace().get(String.valueOf(aux+1));
         	buttons[aux].getButtonPressListeners().add(this);
         }
        
        prepareGame();
                
        window.open(display);
        window.setWidth(420);
        window.setHeight(420);        
    }
 
    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        } 
        return false;
    }
 
    @Override
    public void suspend() {
    }
 
    @Override
    public void resume() {
    }
 
    public static void main(String[] args) {
        DesktopApplicationContext.main(Main.class, args);
    }
    
    
    private void prepareGame() {
    	
    	 prepareImagesArray();
         
         for(int aux = 0; aux < 36; aux++){
         	buttons[aux].setButtonData(new MemGameButtonData(defaultImg, images36[aux]));
         	buttons[aux].setEnabled(true);         	
         }
    }
        
    private void prepareImagesArray() {
		
		int posicaoNoArray, x, y;		
		this.images18 = new Image[18];
		
		for ( x = 0; x < (18); x++ ){			
			try {
				this.images18[ x ] = Image.load(getClass().getResource("img/" + ( x + 1 ) + ".png"));
			} catch (TaskExecutionException e) {}
		}
		
		this.images36 = new Image[ (36) ];
		
		for ( x = 0; x < 2; x++ ){			
			for ( y = 0; y < (18); y++ ){				
				do{	
					posicaoNoArray = ( int ) ( Math.random() * (36) );			
				}while( this.images36[ posicaoNoArray ] != null );			
				this.images36[ posicaoNoArray ] = images18[ y ];			
			}
		} 		
	}

	@Override
	public void buttonPressed(Button button) {

		if( firstClick ){

			if ( !right ){
				
				buttonOne = (PushButton) clickedButtonOne;
				buttonTwo = (PushButton) clickedButtonTwo;
				
				((MemGameButtonData)buttonOne.getButtonData()).setDefaultURL();
				((MemGameButtonData)buttonTwo.getButtonData()).setDefaultURL();
				
				window.repaint();
			}
			
			clickedButtonOne = button;
			
			buttonOne = (PushButton) clickedButtonOne;				
			((MemGameButtonData)buttonOne.getButtonData()).setButtonURL();				

			firstClick = !firstClick;			
		}
		else{											
			clickedButtonTwo = button;
			buttonTwo = (PushButton) clickedButtonTwo;								

			if ( clickedButtonTwo == clickedButtonOne ){				
				right = false;				
				Alert.alert(MessageType.WARNING, "Not permited action!", window);					
			}			
			else{			
					
				((MemGameButtonData)buttonTwo.getButtonData()).setButtonURL();

				if ( ((MemGameButtonData)buttonOne.getButtonData()).getButtonURL().equals( 
						((MemGameButtonData)buttonTwo.getButtonData()).getButtonURL())){
				
					right = true;
					++count;		
					buttonOne.setEnabled( false );
					buttonTwo.setEnabled( false );									
				}

				else{				
					right = false;							
				}					

				firstClick = !firstClick;				
				
				if(count == 18){
					prepareGame();
					Alert.alert(MessageType.INFO, "Congratulations! You got a new challenge!", window);
					count=0;
				}				
			}
		}		
	}
}    
