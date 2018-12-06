package com;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

	private static Dotenv env;
	private static Path resourcesDir = Paths.get("")
			.resolve(Paths.get("src", "main", "resources"))
			.toAbsolutePath();

	static {
		env = Dotenv.configure().directory(resourcesDir.toString()).load();
	}

	public static Dotenv getEnv() {
		return env;
	}

	public static Path getResourceDir() {
		return resourcesDir;
	}
}