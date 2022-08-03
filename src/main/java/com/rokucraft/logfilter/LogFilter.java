package com.rokucraft.logfilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.RegexFilter;
import org.bukkit.plugin.java.JavaPlugin;

public final class LogFilter extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        getConfig().getStringList("deny-patterns").forEach(pattern -> {
            try {
                context.addFilter(
                        RegexFilter.createFilter(pattern, null, false, Filter.Result.DENY, Filter.Result.NEUTRAL)
                );
            } catch (IllegalAccessException e) {
                getSLF4JLogger().warn("Unable to create filter for pattern {}", pattern, e);
            }
        });
    }
}
