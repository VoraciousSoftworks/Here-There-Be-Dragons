package com.voracious.dragons.client.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Text;
import com.voracious.dragons.client.graphics.ui.UnitMenu;
import com.voracious.dragons.client.utils.InputHandler;
import com.voracious.dragons.common.GameState;
import com.voracious.dragons.common.Turn;
import com.voracious.dragons.common.Vec2D;
import com.voracious.dragons.common.towers.Tower;
import com.voracious.dragons.common.units.Dragon;

public class PlayScreen extends Screen {

	public static final Vec2D.Short start_node = new Vec2D.Short((short)300,(short) 1140);
	public static final Vec2D.Short end_node = new Vec2D.Short((short)1860, (short)300);
	
    public static final int ID = 2;
    public static final int WIDTH = 2160;
    public static final int HEIGHT = 1440;
    public static final long ticksPerTurn = 300;
    public long currentTickCount = 0;
    private static Logger logger = Logger.getLogger(Game.class);
    private Sprite background;
    private GameState gamestate;
    private UnitMenu unitsMenu;
    private boolean isMyTurn = false;
    private boolean executingTurn = false;
    private boolean inMenu=false;
    private boolean inPathMode=false;
    private boolean inUnitMode=false;
    private boolean inTowerMode=false;
    
    private Turn myTurn,oppTurn;
    private short pathNum=0;
    
    Vec2D.Short temp;
    
    public PlayScreen() {
        super(HEIGHT, WIDTH);
        
        this.setBackground(new Sprite("/backgroundLarge.png"));
        this.unitsMenu = new UnitMenu();
    }
    
    @Override
	public void start(){
        if(myTurn == null || gamestate == null){
            throw new IllegalArgumentException("Play screen started without choosing a game");
        }
        
    	InputHandler.registerButton(KeyEvent.VK_W);
        InputHandler.registerButton(KeyEvent.VK_A);
        InputHandler.registerButton(KeyEvent.VK_S);
        InputHandler.registerButton(KeyEvent.VK_D);
        InputHandler.registerScreen(this);
	}
	
	@Override
	public void stop(){
		InputHandler.deregisterButton(KeyEvent.VK_W);
        InputHandler.deregisterButton(KeyEvent.VK_A);
        InputHandler.deregisterButton(KeyEvent.VK_S);
        InputHandler.deregisterButton(KeyEvent.VK_D);
        InputHandler.deregisterScreen(this);
        
        myTurn = null;
        oppTurn = null;
        gamestate = null;
	}
	
	public void init(int gameId, boolean isPlayer1, boolean canMakeTurn, GameState s){
	    myTurn = new Turn(gameId, Game.getClientConnectionManager().getSessionId(), isPlayer1);
	    isMyTurn = canMakeTurn;
	    gamestate = s;
	}
	
	public void onTurnReceived(byte[] turn){
	    Turn temp = new Turn(turn);
	    if(temp.isPlayer1()){
	        myTurn = temp;
	    }else{
	        oppTurn = temp;
	    }
	}

    @Override
    public void render(Graphics2D g) {
    	this.getBackground().draw(g, 0, 0);
    	
    	gamestate.draw(g);
    	
    	//Block used for Paths
    	{
    		List<List<Vec2D.Short>>outer=myTurn.getPaths();
    		Iterator<List<Vec2D.Short>>outIt=outer.iterator();
    		while(outIt.hasNext()){
    			Vec2D.Short last=null;
    			List<Vec2D.Short>inner=outIt.next();
    			Iterator<Vec2D.Short>inIt=inner.iterator();
    			while(inIt.hasNext()){
    				Vec2D.Short tmp=inIt.next();
    				g.drawOval(tmp.getx()-8, tmp.gety()-8, 16, 16);
    				if(last!=null){
    					g.drawLine(last.getx(), last.gety(), tmp.getx(), tmp.gety());
    				}
    				last=tmp;
    			}
    		}
    	}
    	//Block used for Towers
    	//NOTE:Entire block should be replaced with drawing towers from the tower list when turn
    	//execution is implemented. I will comment out that code below this block.
    	{
    		List<List<Vec2D.Short>>outer=myTurn.getTowers();
    		Iterator<List<Vec2D.Short>>outIt=outer.iterator();
    		while(outIt.hasNext()){
    			List<Vec2D.Short>inner=outIt.next();
    			Iterator<Vec2D.Short>inIt=inner.iterator();
    			while(inIt.hasNext()){
    				Vec2D.Short tmp=inIt.next();
    				g.drawRect(tmp.getx()-8, tmp.gety()-8, 16, 16);
    			}
    		}
    	}
    	
    	g.translate(this.getOffsetx(), this.getOffsety());
    	
    	if(inMenu){
    		g.setColor(Color.BLACK);
    		g.fillRect(0, Game.HEIGHT-15, Game.WIDTH, 15);
    		Text t = new Text("t");
    		t.setLocation(0, Game.HEIGHT-t.getHeight());
    		t.setColor(Color.WHITE);
    		if(inPathMode)
    			t.setText("You are in path mode");
    		else if(inTowerMode)
    			t.setText("You are in tower mode");
    		else if(inUnitMode){
    			//t.setText("You are in unit mode");
    			unitsMenu.draw(g);
    		}
    		t.draw(g);
    	}
    }

	@Override
	public void translate(Vec2D t) {
		super.translate(t);
		if(this.getOffsetx()<0){
			this.setOffsetx(0);
		}
		else if(this.getOffsetx()>WIDTH-Game.WIDTH){
			this.setOffsetx(WIDTH-Game.WIDTH);
		}
		
		if(this.getOffsety()<-30){
			this.setOffsety(-30);
		}
		else if(this.getOffsety()>HEIGHT-Game.HEIGHT+30){
			this.setOffsety(HEIGHT-Game.HEIGHT+30);			
		}
	}

	@Override
    public void tick() {
		Vec2D.Short s;
        if(InputHandler.isDown(KeyEvent.VK_W)){
        	s=new Vec2D.Short((short)0,(short) -3);
            this.translate(s);
        }else if(InputHandler.isDown(KeyEvent.VK_S)){
        	s=new Vec2D.Short((short)0,(short) 3);
            this.translate(s);
        }
        
        if(InputHandler.isDown(KeyEvent.VK_A)){
        	s=new Vec2D.Short((short)-3,(short) 0);
            this.translate(s);
        }else if(InputHandler.isDown(KeyEvent.VK_D)){
        	s=new Vec2D.Short((short)3,(short) 0);
            this.translate(s);
        }
        
        if(isExecutingTurn()){
            if(currentTickCount < ticksPerTurn){
            	gamestate.tick();
            	currentTickCount++;
            }else{
                this.executingTurn = false;
            }
        }
    }
    
    
    @Override
	public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode()==KeyEvent.VK_P){
    		this.inPathMode=!this.inPathMode;
    		this.inMenu=true;
    		this.inTowerMode=false;
    		this.inUnitMode=false;
    	}
    	else if(e.getKeyCode()==KeyEvent.VK_T){
    		this.inTowerMode=!this.inTowerMode;
    		this.inMenu=true;
    		this.inPathMode=false;
    		this.inUnitMode=false;
    	}
    	else if(e.getKeyCode()==KeyEvent.VK_N){
    		this.inMenu=false;
    		this.inPathMode=false;
    		this.inTowerMode=false;
    		this.inUnitMode=false;
    	}
    	else if(e.getKeyCode()==KeyEvent.VK_U){
    		this.inUnitMode=!this.inUnitMode;
    		this.inMenu=true;
    		this.inPathMode=false;
    		this.inTowerMode=false;
    	}
    	else if(e.getKeyCode()==KeyEvent.VK_EQUALS){
    		this.pathNum++;
    	}else if(e.getKeyCode()==KeyEvent.VK_G){
    	    this.playTurn();
    	}
    	if(!this.inPathMode&&!this.inTowerMode&&!this.inUnitMode){
    		this.inMenu=false;
    	}
	}
    
    public void playTurn(){
        byte[] tbytes = myTurn.toBytes().array();
        byte[] message = new byte[tbytes.length + 1];
        message[0] = 7;
        System.arraycopy(tbytes, 0, message, 1, tbytes.length);
        Game.getClientConnectionManager().sendMessage(message);
        
        gamestate.simulate(myTurn, oppTurn);
        setExecutingTurn(true);
    }
    
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
    public void mouseMoved(MouseEvent e) {
        if(InputHandler.isDown(InputHandler.VK_MOUSE_2)){
            this.translate(InputHandler.getChangeInMouse());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        if(InputHandler.isDown(InputHandler.VK_MOUSE_2)){
            InputHandler.setMouseMoveable(false);
        }
        
        if(InputHandler.isDown(InputHandler.VK_MOUSE_1) && this.inPathMode){
        	//TODO add another button to cycle through the different units
        	temp = new Vec2D.Short((short)(InputHandler.getMousePos().x + this.getOffsetx()),
        			               (short)(InputHandler.getMousePos().y + this.getOffsety()));
        	
        	if(temp.x <= PlayScreen.WIDTH && temp.x >= 0 && temp.y <= PlayScreen.HEIGHT && temp.y >= 0){
        		myTurn.addNode((byte) this.pathNum, temp);
        	}
        	
        }
        else if(InputHandler.isDown(InputHandler.VK_MOUSE_1) && this.inTowerMode ){
        	//TODO add buttons to cycle through towers
        	//TODO -width/2 in the x. -height/2 in the y.	This will center it. 
        	temp = new Vec2D.Short((short) (InputHandler.getMousePos().x + this.getOffsetx()),
        			               (short) (InputHandler.getMousePos().y + this.getOffsety()));
        	
        	if(temp.x <= PlayScreen.WIDTH && temp.x >= 0 && temp.y <= PlayScreen.HEIGHT && temp.y >= 0){
        		myTurn.createTower((byte)0, temp);
        	}
        }else if(InputHandler.isDown(InputHandler.VK_MOUSE_1) && this.inUnitMode){
            myTurn.createUnit(Dragon.ID, (short)1);
        }
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(!InputHandler.isDown(InputHandler.VK_MOUSE_2)){
            InputHandler.setMouseMoveable(true);
        }
    }

	public Sprite getBackground() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PlayScreen.logger = logger;
	}


	public boolean isExecutingTurn() {
		return executingTurn;
	}

	public void setExecutingTurn(boolean executingTurn) {
		this.executingTurn = executingTurn;
		this.currentTickCount = 0;
	}
	
    @Override
    public int getId() {
        return ID;
    }
}
