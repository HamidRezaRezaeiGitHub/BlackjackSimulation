package dev.hrrezaei.blackjack.configuration;

import org.springframework.core.env.Environment;

public class EnvConfig {

    public static <T> T getProperty(Environment env, String propertyName, Class<T> classType, T defaultValue) {
        try {
            return getProperty(env, propertyName, classType);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static <T> T getProperty(Environment env, String propertyName, Class<T> classType) {
        try {
            String propertyValue = env.getProperty(propertyName);

            if (propertyValue == null) {
                throw new IllegalArgumentException("Property '" + propertyName + "' not found in the environment.");
            }

            return castProperty(propertyValue, classType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse property '" + propertyName + "' to type " + classType.getName(), e);
        }
    }

    private static <T> T castProperty(String propertyValue, Class<T> classType) {
        return switch (classType.getSimpleName()) {
            case "String" -> classType.cast(propertyValue);
            case "Integer" -> classType.cast(Integer.parseInt(propertyValue));
            case "Long" -> classType.cast(Long.parseLong(propertyValue));
            case "Boolean" -> classType.cast(Boolean.parseBoolean(propertyValue));
            case "Double" -> classType.cast(Double.parseDouble(propertyValue));
            case "Float" -> classType.cast(Float.parseFloat(propertyValue));
            default -> throw new UnsupportedOperationException("Unsupported class type: " + classType.getName());
        };
    }

}
