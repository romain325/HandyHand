package Core.Position;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Describe the Position of the hand
 */
public class HandPosition {

    /**
     * Get frames hands position
     * @param frame current frame
     * @return Map of Key:Which hand, Value: set of position
     */
    public Map<String, Set<Position>> getHandsPosition(Frame frame){
        Map<String,Set<Position>> rtrn = new HashMap<>();
        if (frame.hands().count() == 0) {
            rtrn.put("None", getSpacePos(null));
            return rtrn;
        }

        for (Hand hand: frame.hands()) {
            rtrn.put(getHand(hand), getSpacePos(hand));
        }
        return rtrn;
    }

    /**
     * Get type of hand
     * @param hand Hand object
     * @return type of hand
     */
    private String getHand(Hand hand){
        return hand.isLeft() ? "left" : "right";
    }

    /**
     * Get A set of position
     * @param hand current hand
     * @return set
     */
    private Set<Position> getSpacePos(Hand hand){
        HashSet<Position> set = new HashSet<>();

        if(hand == null){
            set.add(Position.NONE);
            return set;
        }

        if (hand.palmPosition().getX() > 0) {
            set.add(Position.RIGHT);
        }else {
            set.add(Position.LEFT);
        }

        if(hand.palmPosition().getZ() > 0){
            set.add(Position.BACK);
        }else{
            set.add(Position.FRONT);
        }
        return set;
    }
}
