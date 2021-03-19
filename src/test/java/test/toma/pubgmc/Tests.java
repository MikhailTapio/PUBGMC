package test.toma.pubgmc;

import com.google.common.base.Stopwatch;
import dev.toma.pubgmc.DevUtil;
import dev.toma.pubgmc.client.animation.AnimationElement;
import dev.toma.pubgmc.client.animation.AnimationSpec;
import dev.toma.pubgmc.client.animation.Elements;
import dev.toma.pubgmc.client.animation.impl.ConnectedAnimation;
import dev.toma.pubgmc.client.animation.interfaces.KeyFrame;
import dev.toma.pubgmc.common.items.attachment.AttachmentType;
import net.minecraft.util.math.Vec3d;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Tests {

    @Test
    public void testAttachmentTypeSlots() {
        AttachmentType<?>[] types = AttachmentType.allTypes;
        for (AttachmentType<?> type : types) {
            for (AttachmentType<?> type1 : types) {
                if(type == type1)
                    continue;
                int x1 = type.getX();
                int y1 = type.getY();
                int x2 = type1.getX();
                int y2 = type1.getY();
                int diffX = Math.abs(x1 - x2);
                int diffY = Math.abs(y1 - y2);
                assertTrue(diffX > 16 || diffY > 16);
            }
        }
    }

    @Test
    public void testAnimationConnection() {
        Map<AnimationElement, List<KeyFrame>> def0 = DevUtil.make(new HashMap<>(), map -> {
            map.put(Elements.ITEM, DevUtil.make(new ArrayList<>(), list -> {
                list.add(KeyFrame.move(0.5F, new Vec3d(0, 5, 0)));
                list.add(KeyFrame.move(1.0F, new Vec3d(2, 0, 0)));
            }));
            map.put(Elements.RIGHT_HAND, DevUtil.make(new ArrayList<>(), list -> {
                list.add(KeyFrame.move(0.75F, new Vec3d(1, 0, 1)));
                list.add(KeyFrame.move(1.0F, new Vec3d(-1, 0, -1)));
            }));
        });
        Map<AnimationElement, List<KeyFrame>> def1 = DevUtil.make(new HashMap<>(), map -> {
            map.put(Elements.ITEM, DevUtil.make(new ArrayList<>(), list -> {
                list.add(KeyFrame.move(0.25F, new Vec3d(0, 0, 2)));
                list.add(KeyFrame.move(1.0F, new Vec3d(-2, -5, -2)));
            }));
        });
        ConnectedAnimation animation = new ConnectedAnimation.Builder().length(15)
                .addSpec(0.5F, new AnimationSpec(def0))
                .addSpec(1.0F, new AnimationSpec(def1))
                .build();
        Map<AnimationElement, List<KeyFrame>> compiled = animation.getSpec().getFrameDefs();
        List<KeyFrame> rightHandFrames = compiled.get(Elements.RIGHT_HAND);
        List<KeyFrame> itemFrames = compiled.get(Elements.ITEM);
        assertEquals(4, itemFrames.size());
        assertEquals(2, rightHandFrames.size());
        assertEquals(0.375F, rightHandFrames.get(0).endPoint(), 0.00001F);
        assertEquals(0.5F, rightHandFrames.get(1).endPoint(), 0.00001F);
        assertEquals(0.25F, itemFrames.get(0).endPoint(), 0.00001F);
        assertEquals(0.5F, itemFrames.get(1).endPoint(), 0.00001F);
        assertEquals(0.625F, itemFrames.get(2).endPoint(), 0.00001F);
        assertEquals(1.0F, itemFrames.get(3).endPoint(), 0.0001F);
    }

    @Test
    public void testConnectedRepeatingAnimation() {
        Map<AnimationElement, List<KeyFrame>> def = DevUtil.make(new HashMap<>(), map -> {
            map.put(Elements.ITEM, DevUtil.make(new ArrayList<>(), list -> {
                list.add(KeyFrame.move(1.0F, new Vec3d(0, 1, 0)));
            }));
        });
        Map<AnimationElement, List<KeyFrame>> repeat = DevUtil.make(new HashMap<>(), map -> {
            map.put(Elements.ITEM, DevUtil.make(new ArrayList<>(), list -> list.add(KeyFrame.move(1.0F, new Vec3d(1, 0, 0)))));
        });
        ConnectedAnimation animation = new ConnectedAnimation.Builder()
                .length(10)
                .addSpec(0.5F, new AnimationSpec(def))
                .addSpec(1.0F, 4, new AnimationSpec(repeat))
                .build();
        List<KeyFrame> frames = animation.getSpec().getFrameDefs().get(Elements.ITEM);
        assertEquals(5, frames.size());
        assertEquals(0.5F, frames.get(0).endPoint(), 0.001F);
        assertEquals(0.625F, frames.get(1).endPoint(), 0.001F);
        assertEquals(0.75F, frames.get(2).endPoint(), 0.001F);
        assertEquals(0.875F, frames.get(3).endPoint(), 0.001F);
        assertEquals(1.0F, frames.get(4).endPoint(), 0.001F);
    }
}
