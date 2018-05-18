package com.ljnic.roleplay;

import com.atherys.core.database.mongo.MongoDatabaseConfig;
import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;

public class RoleplayConfig extends PluginConfig {

    @Setting("is_default")
    public boolean IS_DEFAULT = true;

    @Setting("dialog_msg_delay")
    public long DIALOG_MESSAGE_DELAY= 2;

    @Setting("database")
    public MongoDatabaseConfig DATABASE = new MongoDatabaseConfig();

    protected RoleplayConfig(String directory, String filename) throws IOException {
        super(directory, filename);
    }
}