package dev.vesper.paleworldfx.neoforge;

//? neoforge {
/*import dev.vesper.paleworldfx.PaleWorldFX;
// sample_content
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(PaleWorldFX.MOD_ID)
@EventBusSubscriber // sample_content
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        PaleWorldFX.init();
    }

    @EventBusSubscriber(modid = PaleWorldFX.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            PaleWorldFX.LOG.info("Initializing {} Client", PaleWorldFX.MOD_ID);
        }
    }
}
*///?}