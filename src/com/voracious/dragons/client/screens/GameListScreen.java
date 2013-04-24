package com.voracious.dragons.client.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.graphics.ui.Text;
import com.voracious.dragons.client.utils.InputHandler;
import com.voracious.dragons.common.GameInfo;

public class GameListScreen extends Screen {
    public static final int ID = 4;
    public static final int borderPadding = 10;
    public static final int lineSpacing = 2;
    
    private List<GameInfo> games;
    private List<Text> gameTexts;
    private boolean areTextsSet;
    private int textHeight;
    private int offset;
    private int selected;
    private int numToDraw;
    private int boxHeight;
    private Sprite background;
    
    public GameListScreen() {
        super(Game.WIDTH, Game.HEIGHT);
        gameTexts = new ArrayList<Text>();
        textHeight = new Text("temp").getHeight() + lineSpacing;
        boxHeight = this.getHeight() - 3*borderPadding - textHeight + lineSpacing - 2*Button.defaultPadding;
        numToDraw = boxHeight / textHeight;
        offset = 0;
        selected = 0;
        areTextsSet = false;
        background = new Sprite("/mainMenuBackground.png");
        
        games = new ArrayList<>(4);
        
        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));
        
        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));
        
        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));
        
        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));
        
        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));

        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));
        
        games.add(new GameInfo(1, "gamename", "other_guy", 92, true));
        games.add(new GameInfo(2, "gameaname", "other_guy", 92, false));
        games.add(new GameInfo(4, "gamenfame", "other_guy", 92, true));
        games.add(new GameInfo(5, "gamenggame", "other_guy", 92, true));
    }
    
    public void start(){
        InputHandler.registerScreen(this);
    }
    
    public void stop(){
        InputHandler.deregisterScreen(this);
    }

    @Override
    public void render(Graphics2D g) {
        background.draw(g, 0, 0);
        g.setColor(new Color(0xCCCCCCCC, true));
        g.fillRect(borderPadding, borderPadding, this.getWidth() - borderPadding*2, boxHeight);
        g.setColor(Color.BLACK);
        g.drawRect(borderPadding, borderPadding, this.getWidth() - borderPadding*2, boxHeight);
        
        if(gameTexts != null){
            int max = numToDraw*2 < (gameTexts.size() - offset*2) ? numToDraw*2 : (gameTexts.size() - offset*2);
            for(int i = 0; i < max; i++){
                int getIndex = i + offset*2;
                
                if(i%2 == 0 && getIndex/2 == selected){
                    g.setColor(new Color(0xCCCCCC33, true));
                    g.fillRect(borderPadding + lineSpacing, borderPadding + lineSpacing + (i/2)*textHeight, this.getWidth() - borderPadding*2 - lineSpacing*2, textHeight);
                }
                
                if(i%2 == 0){
                    gameTexts.get(getIndex).setLocation(borderPadding + lineSpacing, borderPadding + lineSpacing + (i/2)*textHeight);
                }else{
                    gameTexts.get(getIndex).setLocation(this.getWidth() - borderPadding*2 - gameTexts.get(getIndex).getWidth(), borderPadding + lineSpacing + (i/2)*textHeight);
                }
                
                gameTexts.get(getIndex).draw(g);
            }
        }
    }
    
    public void onListRecieved(List<GameInfo> games){
        this.games = games;
    }
    
    @Override
    public void tick() {
        if(this.games != null && !this.areTextsSet){
            gameTexts = new ArrayList<>(gameTexts.size());
            
            for(GameInfo g : games){
                gameTexts.add(new Text(g.getGameName()));
                gameTexts.add(new Text((g.isLastMoveByMe() ? "me" : g.getOtherPlayer()) + " " + timestampToString(g.getLastMoveTime())));
            }
            
            this.areTextsSet = true;
        }
    }
    
    public String timestampToString(long timestamp){
        String datestr = new Timestamp(timestamp).toString();
        return datestr.substring(0, datestr.indexOf('.')); //chop off the milliseconds
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(this.areTextsSet){
            if(e.getKeyCode() == KeyEvent.VK_UP){
                if(selected > 0){
                    selected--;
                    
                    if(offset > selected && offset > 0){
                        offset--;
                    }
                }
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                if(selected < games.size() - 1){
                    selected++;
                    
                    if(numToDraw + offset <= selected && numToDraw + offset < games.size()){
                        offset++;
                    }
                }
            }else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                
            }   
        }
    }
}
