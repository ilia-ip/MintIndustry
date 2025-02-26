package com.ilia_ip.mintindustry.core;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class ModKeybindings {
    public static final ModKeybindings INSTANCE = new ModKeybindings();

    private ModKeybindings() {
    }

    private static final String CATEGORY = "key.categories." + MintIndustry.MODID;

    public final KeyMapping controllerMenuKey = new KeyMapping("key." + MintIndustry.MODID + ".controllerMenuKey",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_U, -1), CATEGORY);
}
