package dev.vesper.paleworldfx;

//? fabric {
/*import dev.vesper.paleworldfx.fabric.FabricPlatformImpl;
*///?}
//? neoforge {
import dev.vesper.paleworldfx.neoforge.NeoforgePlatformImpl;
//?}

public interface Platform {

    //? fabric {
    /*Platform INSTANCE = new FabricPlatformImpl();
    *///?}
    //? neoforge {
    Platform INSTANCE = new NeoforgePlatformImpl();
    //?}


    boolean isModLoaded(String modid);
    String loader();

}
