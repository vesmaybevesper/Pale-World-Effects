package vesper.paleworldfx.Utils;

import net.fabricmc.loader.api.FabricLoader;
import net.irisshaders.iris.Iris;

public class UsefulBools {
    public static boolean areShaders;
    public static boolean isIris;

    public static void setBools(){
        // Check if Iris is loaded
        isIris = FabricLoader.getInstance().isModLoaded("iris");
        // Check if Iris is loaded and shaders are enabled
        areShaders = FabricLoader.getInstance().isModLoaded("iris") && Iris.isPackInUseQuick();
    }
}
