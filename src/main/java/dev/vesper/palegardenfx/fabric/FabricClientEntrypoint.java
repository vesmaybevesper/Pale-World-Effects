package dev.vesper.palegardenfx.fabric;

//? fabric {
import dev.vesper.palegardenfx.PaleGardenFX;
import dev.vesper.palegardenfx.common.Config;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PaleGardenFX.LOG.info("Initializing {} Client", PaleGardenFX.MOD_ID);
        Config.getHandler().load();
    }

}
//?}