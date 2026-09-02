package dev.vesper.palegardenfx.platform.fabric;

//? fabric {

import dev.vesper.palegardenfx.PaleGardenFX;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		PaleGardenFX.onInitializeClient();
	}

}
//?}
