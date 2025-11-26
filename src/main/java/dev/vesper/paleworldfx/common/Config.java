package dev.vesper.paleworldfx.common;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.<Config>createBuilder(Config.class)
                .id(Identifier.fromNamespaceAndPath("paleworldfx", "config"))
            .serializer(configConfigClassHandler -> GsonConfigSerializerBuilder.create(configConfigClassHandler)
                    .setPath(YACLPlatform.getConfigDir().resolve("paleworldfx.json"))
                    .build())
            .build();


    public static Screen config(Screen parent){
        return HANDLER.generateGui().generateScreen(parent);
    }

    @AutoGen(category = "Fog Config")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean horrorMode = false;

    @AutoGen(category = "Fog Config")
    @FloatField
    @SerialEntry
    public static float fogStart = 0.5f;

    @AutoGen(category = "Fog Config")
    @FloatField
    @SerialEntry
    public static float fogEnd = 20F;

    @AutoGen(category = "Fog Config")
    @FloatField
    @SerialEntry
    public static float fogTransparency = 0.7F;

    @AutoGen(category = "Fog Config")
    @FloatField
    @SerialEntry
    public static float fogFade = 0.002F;
}
