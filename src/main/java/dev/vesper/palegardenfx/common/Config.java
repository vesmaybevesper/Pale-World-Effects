package dev.vesper.palegardenfx.common;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.platform.YACLPlatform;
import dev.vesper.eveningstarlib.common.serializers.fastjson.FastJsonConfigSerializerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;

public class Config {
    public static ConfigClassHandler<Config> HANDLER;

    public static ConfigClassHandler<Config> getHandler() {
        if (HANDLER == null) {
            HANDLER = ConfigClassHandler.<Config>createBuilder(Config.class)
                    .id(Identifier.fromNamespaceAndPath("paleworldfx", "config"))
                    .serializer(configConfigClassHandler -> FastJsonConfigSerializerBuilder.create(configConfigClassHandler)
                            .setPath(YACLPlatform.getConfigDir().resolve("paleworldfx.json"))
                            .build())
                    .build();

        }
        return HANDLER;
    }

    public static Screen config(Screen parent){
        return getHandler().generateGui().generateScreen(parent);
    }

    public enum FogType {VANILLA, SHADER, OFF};

    @AutoGen(category = "Main")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean horrorMode = false;

    @AutoGen(category = "Main")
    @EnumCycler
    @SerialEntry
    public static FogType fogType = FogType.VANILLA;

    @AutoGen(category = "Main")
    @FloatField
    @SerialEntry
    public static float fogStart = 0.5f;

    @AutoGen(category = "Main")
    @FloatField
    @SerialEntry
    public static float fogEnd = 20F;

    @AutoGen(category = "Main")
    @FloatField
    @SerialEntry
    public static float fogTransparency = 0.7F;
}