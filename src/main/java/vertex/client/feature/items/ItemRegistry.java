

package vertex.client.feature.items;


import vertex.client.feature.items.impl.*;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    public static final ItemRegistry instance = new ItemRegistry();
    final List<Item> items = new ArrayList<>();

    private ItemRegistry() {
        init();
    }

    void init() {
        items.clear();
        items.add(new AutoOp());
        items.add(new Nuke());
        items.add(new Plague());
        items.add(new Poof());
        items.add(new Backdoor());
        items.add(new Fireball());
        items.add(new InfiniteEntity());
        items.add(new InfiniSculk());
    }

    public List<Item> getItems() {
        return items;
    }
}
