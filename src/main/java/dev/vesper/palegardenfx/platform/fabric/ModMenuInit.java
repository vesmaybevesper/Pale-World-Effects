package dev.vesper.palegardenfx.platform.fabric;

//? if fabric {
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.vesper.palegardenfx.common.Config;

public class ModMenuInit implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return Config::config;
	}
}
//?}
