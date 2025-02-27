package vesper.paleworldfx;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config  extends MidnightConfig {

    public static boolean horrorMode = false;

    public enum horrorVals {FALSE, TRUE}

    @Entry public static horrorVals horrorModeSelect = horrorVals.FALSE;
}

