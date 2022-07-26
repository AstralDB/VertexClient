

package vertex.client.helper.event.events;

import vertex.client.helper.event.events.base.NonCancellableEvent;

public class KeyboardEvent extends NonCancellableEvent {

    final int kc;
    final int t;

    public KeyboardEvent(int keycode, int type) {
        this.kc = keycode;
        this.t = type;
    }

    public int getKeycode() {
        return kc;
    }

    /**
     * @return the type of the event<br>0 = key released<br>1 = key pressed<br>2 = key event repeated
     */
    public int getType() {
        return t;
    }
}
