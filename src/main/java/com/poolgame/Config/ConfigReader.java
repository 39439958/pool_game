package com.poolgame.Config;

public interface ConfigReader<T> {
    T readConfig(String configPath);
}
