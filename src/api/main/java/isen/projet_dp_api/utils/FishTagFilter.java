package isen.projet_dp_api.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.UUID;

@Component
public class FishTagFilter implements Filter {

    private static final ThreadLocal<String> fishTagHolder = new ThreadLocal<>();

    public static String getFishTag() {
        return fishTagHolder.get();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Generate a fishTag (e.g., random 10-character string)
        var fishTag = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toLowerCase();
        fishTagHolder.set(fishTag);

        try {
            // Add the fishTag to the request for logging
            if (request instanceof HttpServletRequest) {
                ((HttpServletRequest) request).setAttribute("fishTag", fishTag);
            }

            chain.doFilter(request, response);
        } finally {
            // Clean up after the request
            fishTagHolder.remove();
        }
    }
}
