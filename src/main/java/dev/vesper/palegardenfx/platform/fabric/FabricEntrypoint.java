package dev.vesper.palegardenfx.platform.fabric;

//? fabric {

import dev.vesper.palegardenfx.PaleGardenFX;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		PaleGardenFX.onInitialize();
	}
}
//?}
