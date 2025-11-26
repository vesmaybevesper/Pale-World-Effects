package dev.vesper.paleworldfx.fabric;

//? fabric {
import dev.vesper.paleworldfx.ModTemplate;
import net.fabricmc.api.ModInitializer;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        ModTemplate.init();
    }

}
//?}