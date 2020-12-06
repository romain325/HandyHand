package Core.Listener;

import Core.Position.HandPosition;
import Core.Position.Position;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import java.util.*;

public class PositionListener extends MainListener{
    HandPosition handPosition = new HandPosition();
    String wantedHand;
    Set<Position> wantedPos;
    private boolean isActive;


    private PositionListener(String hand, Set<Position> pos){
        this.wantedHand = hand;
        this.wantedPos = pos;
    }

    /**
     * factory method to get a PositionListener
     * @param hand which hand to track
     * @param positions space position of the hand, if incoherent, return null
     * */
    public static PositionListener PositionListenerFactory(String hand, Position[] positions){
        if(hand.toUpperCase() != "LEFT" && hand.toUpperCase(Locale.ROOT) != "RIGHT"){
            return null;
        }
        if(positions.length > 2){ return null; }
        if(Arrays.asList(positions).contains(Position.NONE) || (Arrays.asList(positions).contains(Position.FRONT) && Arrays.asList(positions).contains(Position.BACK)) || (Arrays.asList(positions).contains(Position.LEFT) && Arrays.asList(positions).contains(Position.RIGHT))){ return null ;}

        return new PositionListener(hand, new HashSet<>(Arrays.asList(positions)));
    }

    @Override
    public void onConnect(Controller controller) {
    }

    @Override
    public void action(Frame frame) {
        Map<String, Set<Position>> currentState = handPosition.getHandsPosition(frame);
        if(currentState.get(wantedHand) != null){
            if(wantedPos.equals(currentState.get(wantedHand))){
                isActive = true;
                return;
            }
        }
        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
