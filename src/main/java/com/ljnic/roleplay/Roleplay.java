package com.ljnic.roleplay;

import com.atherys.core.command.CommandService;
import com.google.inject.Inject;
import com.ljnic.roleplay.commands.card.MasterCardCommand;
import com.ljnic.roleplay.commands.misc.RollCommand;
import com.ljnic.roleplay.listeners.PlayerListener;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.IOException;

@Plugin( id = Roleplay.ID, name = Roleplay.NAME, description = Roleplay.DESCRIPTION, version = Roleplay.VERSION )
public class Roleplay {
    static final String ID = "roleplay";
    static final String NAME = "Roleplay";
    static final String DESCRIPTION = "Character cards, dice, and more";
    static final String VERSION = "1.0.0";

    @Inject
    private Logger logger;

    @Inject
    private Game game;

    private static RoleplayConfig config;
    private static Roleplay instance;
    private static boolean init = false;

    private void init () {
        instance = this;
        try{
            config = new RoleplayConfig(getDirectory(), "config.conf");
            config.init();
        }catch(IOException e){
            init = false;
            e.printStackTrace();
            return;
        }

        if(config.IS_DEFAULT){
            logger.error("The Roleplay config is set to default. Edit the default config settings and change 'isDefault' to false.");
        }

        init = true;
    }

    private void start() {
        Sponge.getEventManager().registerListeners(this, new PlayerListener());
        CardManager.getInstance().loadAll();
        try{
            CommandService.getInstance().register(new MasterCardCommand(), this);
            CommandService.getInstance().register(new RollCommand(), this);
        }catch(CommandService.AnnotatedCommandException e){
            e.printStackTrace();
        }
    }

    private void stop() {
        CardManager.getInstance().saveAll();
    }

    @Listener
    public void onInit (GameInitializationEvent event) {
        init();
    }

    @Listener
    public void onStart (GameStartingServerEvent event) {
        if ( init ) start();
    }

    @Listener
    public void onStop (GameStoppingServerEvent event) {
        if ( init ) stop();
    }

    public static Roleplay getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return getInstance().logger();
    }

    public static RoleplayConfig getConfig(){
        return config;
    }

    public static Game getGame() {
        return getInstance().game();
    }

    public Logger logger() {
        return logger;
    }

    public Game game() {
        return game;
    }

    public String getDirectory(){
        return "config/" + ID;
    }
}
