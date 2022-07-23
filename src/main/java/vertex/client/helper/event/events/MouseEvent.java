

package vertex.client.helper.event.events;

import vertex.client.helper.event.events.base.Event;

public class MouseEvent extends Event {

    final int button;
    final int type;

    public MouseEvent(int button, int action) {
        this.button = button;
        type = action;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return type;
    }
}
