package isen.projet_dp_api.utils;

import java.io.InputStream;
import java.util.Objects;

public class ResourceLoaderUtil {

    // Load a file as an InputStream (Works both locally AND inside a JAR)
    public static InputStream getResourceAsStream(String relativePath) {
        return Objects.requireNonNull(
                ResourceLoaderUtil.class.getClassLoader().getResourceAsStream(relativePath)
        );
    }
}