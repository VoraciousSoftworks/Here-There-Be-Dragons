package com.voracious.dragons.client.screens;

import java.awt.Graphics2D;
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
        areTextsSet = false;
        background = new Sprite("/mainMenuBackground.png");
        
        games = new ArrayList<>(4);
        
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
        g.drawRect(borderPadding, borderPadding, this.getWidth() - borderPadding*2, boxHeight);
        if(gameTexts != null){
            int max = numToDraw*2 < (gameTexts.size() - offset) ? numToDraw*2 : (gameTexts.size() - offset);
            for(int i = offset; i < max; i++){
                if(i%2 == 0){
                    gameTexts.get(i).setLocation(borderPadding + lineSpacing, borderPadding + lineSpacing + (i/2)*textHeight);
                }else{
                    gameTexts.get(i).setLocation(this.getWidth() - borderPadding*2 - gameTexts.get(i).getWidth(), borderPadding + lineSpacing + (i/2)*textHeight);
                }
                
                gameTexts.get(i).draw(g);
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
}
