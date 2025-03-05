package vesper.paleworldfx;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config  extends MidnightConfig {

    public static boolean horrorMode = false;

    public enum horrorVals {FALSE, TRUE}

    @Entry public static horrorVals horrorModeSelect = horrorVals.FALSE;
    @Entry public static float fogStart = 0.5F;
    @Entry public static float fogEnd = 20F;
    @Entry public static float fogTransparency = 0.7F;
}

