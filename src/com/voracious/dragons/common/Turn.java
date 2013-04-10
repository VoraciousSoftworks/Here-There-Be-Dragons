package com.voracious.dragons.common;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Turn {

    //This should probably be somewhere else eventually, I'm just not sure where yet
    public static final String versionString = "0.01a";
    public static final byte versionCode = 1;
    
    private boolean isPlayer1;
    
    private Map<Byte, Short> unitsCreated;
    private Map<Byte, List<Vec2D.Short>> towersCreated;
    private Map<Byte, List<Vec2D.Short>> nodes;
    
    public Turn(boolean isPlayer1){
        this.isPlayer1 = isPlayer1;
        
        unitsCreated = new HashMap<Byte, Short>();
        towersCreated = new TreeMap<Byte, List<Vec2D.Short>>();
        nodes = new TreeMap<Byte, List<Vec2D.Short>>();
    }
    
    public void createUnit(byte unitId, short numUnits) {
        if(unitsCreated.containsKey(unitId)){
            unitsCreated.put(unitId, (short) (unitsCreated.get(unitId) + numUnits));
        }else{
            unitsCreated.put(unitId, numUnits);
        }
    }
    
    public void createTower(byte towerId, Vec2D.Short location) {
        if(towersCreated.containsKey(towerId)){
            towersCreated.get(towerId).add(location);
        }else{
            List<Vec2D.Short> temp = new LinkedList<Vec2D.Short>();
            temp.add(location);
            
            towersCreated.put(towerId, temp);
        }
    }
    
    public void addNode(byte pathId, Vec2D.Short location) {
        if(towersCreated.containsKey(pathId)){
            towersCreated.get(pathId).add(location);
        }else{
            List<Vec2D.Short> temp = new LinkedList<Vec2D.Short>();
            temp.add(location);
            
            towersCreated.put(pathId, temp);
        }
    }
    
    public String toString() {
        String result = "";
        byte[] dataBytes = this.toBytes();
        
        for(int i=0; i<dataBytes.length; i++){
            result += Byte.toString(dataBytes[i]) + " ";
        }
        
        return result;
    }
    
    /*
     * byte versionCode
     * byte playerId
     * 
     * byte numberOfUnits
     * byte numberOfTowers
     * byte numberOfPathNodes
     * 
     * {
     *   byte unitId
     *   short numSpawned
     * }*numberOfUnits
     * 
     * {
     *   byte towerId
     *   short x
     *   short y
     * }*numberOfTowers
     * 
     * {
     *   byte pathNum
     *   byte nodeNum
     *   short x
     *   short y
     * }*numberOfPathNodes
     * 
     */
    
    public byte[] toBytes() {
        int bufferSize = 5; //size of header info (everything through numberOfPathNodes)
        bufferSize += unitsCreated.size() * (Byte.SIZE/8 + Short.SIZE/8); //Bytes from the unit data

        byte numberOfTowers = 0;
        { //Count the number of towers
            Iterator<List<Vec2D.Short>> it = towersCreated.values().iterator();
            
            while(it.hasNext()){
                numberOfTowers += it.next().size();
            }
            
            bufferSize += numberOfTowers * (Byte.SIZE/8 + 2*(Short.SIZE/8));
        }
        
        byte numberOfNodes = 0;
        { //Count the number of nodes
            Iterator<List<Vec2D.Short>> it = nodes.values().iterator();
            
            while(it.hasNext()){
                numberOfNodes += it.next().size();
            }
            
            bufferSize += numberOfNodes * (2*(Byte.SIZE/8) + 2*(Short.SIZE/8));
        }
        
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.limit(buffer.capacity());
        
        //put the version code
        buffer.put(versionCode);
        
        //put the player id
        buffer.put((byte) (isPlayer1 ? 1 : 0));
        
        //Put the array sizes
        buffer.put((byte) unitsCreated.size());
        buffer.put(numberOfTowers);
        buffer.put(numberOfNodes);
        
        { //put the units data
            Set<Map.Entry<Byte, Short>> entrySet = unitsCreated.entrySet();
            Iterator<Map.Entry<Byte, Short>> it = entrySet.iterator();
            while(it.hasNext()){
                Map.Entry<Byte, Short> entry = (Map.Entry<Byte, Short>) it.next();
                
                buffer.put(entry.getKey().byteValue());
                
                buffer.asShortBuffer().put(entry.getValue().shortValue());
                buffer.position(buffer.position() + (Short.SIZE/8));
            }
        }
        
        { //put the towers data
            
        }
        
        { //put the nodes data
            
        }
        return buffer.array();
    }
}
