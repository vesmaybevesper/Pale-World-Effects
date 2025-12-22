package dev.vesper.paleworldfx.neoforge;

//? neoforge {
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.vesper.paleworldfx.PaleWorldFX;
import dev.vesper.paleworldfx.common.Config;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(PaleWorldFX.MOD_ID)
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        PaleWorldFX.init();
    }

    @EventBusSubscriber(modid = PaleWorldFX.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            PaleWorldFX.LOG.info("Initializing {} Client", PaleWorldFX.MOD_ID);
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (client, parent) -> Config.config(parent)
            );
            Config.getHandler().load();
        }
    }
}
//?}