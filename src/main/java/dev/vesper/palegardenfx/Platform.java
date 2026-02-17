package dev.vesper.palegardenfx;

//? fabric {
import dev.vesper.palegardenfx.fabric.FabricPlatformImpl;
//?}
//? neoforge {
/*import dev.vesper.palegardenfx.neoforge.NeoforgePlatformImpl;
*///?}


public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}


    boolean isModLoaded(String modid);
    String loader();

}
