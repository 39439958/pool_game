package com.poolgame.Config;

public class ConfigReaderFactory {
    public ConfigReader createConfigReader(String configType) {
        if ("ball".equalsIgnoreCase(configType)) {
            return new BallConfigReader();
        }
        else if ("table".equalsIgnoreCase(configType)) {
            return new TableConfigReader();
        }
        return null;
    }
}
