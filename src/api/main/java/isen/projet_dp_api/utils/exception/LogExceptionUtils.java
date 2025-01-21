package isen.projet_dp_api.utils.exception;

import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import isen.projet_dp_api.utils.FishTagFilter;

@Log4j2
public class LogExceptionUtils {

    public static void logException(Class<?> clazz, String message, Throwable e, Object... params) {
        String stackTrace = (e != null) ? stackTraceToString(e.getStackTrace()) : "No stack trace available";
        String errorMessage = (e != null) ? e.getMessage() : "No error message available";
        String errorCause = (e != null && e.getCause() != null) ? e.getCause().toString() : "No cause available";

        log.error(
                toString(clazz.getSimpleName(),
                        message,
                        concatenerParams(params),
                        errorMessage,
                        errorCause,
                        stackTrace
                )
        );
    }

    private static String concatenerParams(final Object... params) {
        final var sb = new StringBuilder();
        if (params != null) {
            for (final var obj : params) {
                var string = new StringBuilder();
                if (obj.getClass().isArray()) {
                    Object[] array = (Object[]) obj;
                    string.append("[");
                    string.append(parametersToString(array));
                    string.append("]");
                } else {
                    string.append(obj.toString());
                }
                sb.append(string);
                sb.append(" | ");
            }
        }
        if (sb.length() >= 2) {
            sb.setCharAt(sb.length() - 2, ' '); //Enleve a dernière barre
        }
        return sb.toString().trim();
    }

    private static String parametersToString(Object[] array) {
        final var string = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            var object = array[i];
            if (i != 0) {
                string.append(" , ");
            }
            string.append(object.toString());
        }
        return string.toString();
    }

    private static String stackTraceToString(StackTraceElement[] stackTrace) {
        final var sb = new StringBuilder();
        sb.append("\n");
        for (final var element : stackTrace) {
            sb.append("\t");
            sb.append(element);
            sb.append("\t");
        }
        return sb.toString();
    }

    public static String toString(final String erreurType, final String message, final String parametre, final String errorMessage, final String errorCause, final String stacktrace) {
        return "\n\tOrigine Erreur : " + erreurType +
                "\n\tMessage : " + message +
                "\n\tFishTag : " + FishTagFilter.getFishTag() +
                "\n\tParametres : " + parametre +
                "\n\tErreur : " + errorMessage +
                "\n\tCause : " + errorCause +
                "\n\tStacktrace : " + stacktrace;
    }
}
