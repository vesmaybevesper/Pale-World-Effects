package vesper.paleworldfx;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaleWorldEffects implements ModInitializer {
	public static final String MOD_ID = "paleworldfx";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MidnightConfig.init(PaleWorldEffects.MOD_ID, Config.class);
	}
}