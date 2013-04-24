package com.voracious.dragons.client.net;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class StatisticsPacket implements Packet {

    public static final char FINISHED_CODE      = 'F';
    public static final char CURRENT_CODE       = 'C';
    public static final char WINS_CODE          = 'W';
    public static final char LOSSES_CODE        = 'L';
    public static final char WIN_RATE_CODE      = 'w';
    public static final char LOSS_RATE_CODE     = 'l';
    public static final char AVE_TURNS_PER_CODE = 'T';
    public static final char TIME_TO_TURN_CODE  = 't';
    
    @Override
    public boolean wasCalled(Message message) {
        return false;
    }

    @Override
    public void process(Message message, ConnectionManager cm) {

    }

    @Override
    public boolean isString() {
        return true;
    }
}
