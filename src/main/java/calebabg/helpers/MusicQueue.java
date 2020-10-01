package calebabg.helpers;

import java.util.Map;
import controlP5.ControlP5;
import controlP5.ScrollableList;

public class MusicQueue extends ScrollableList {
    public MusicQueue(ControlP5 controlP5, String s) {
        super(controlP5, s);
    }

    public Map<String, Object> peek() {
        Map<String, Object> returnMap = null;

        if (!items.isEmpty()) {
            returnMap = getItem(0);
        }

        return returnMap;
    }

    public <T> T dequeue() {
        Object returnObj = null;

        Map<String, Object> peekItem = peek();

        if (peekItem != null) {
            returnObj = peekItem.get("value");

            // remove item
            String itemName = (String) peekItem.get("name");
            removeItem(itemName);
        }

        return returnObj != null ? (T) returnObj : null;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public boolean isNotEmpty(){
        return !isEmpty();
    }

    public int size(){
        return items.size();
    }
}
