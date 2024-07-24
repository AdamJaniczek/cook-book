package pl.com.itsystems.cookbook.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomErrorService {
    private static final String ERROR_403 = "Dostęp zabroniowy";
    private static final String ERROR_404 = "Nie można odnaleźć zasobu";
    private static final String ERROR_500 = "Wewnętrzny błąd serwera";
    private static final String ERROR_418 = "Ups coś poszło nie tak";

    public String generateErrorCode(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorCode = ERROR_418;
        if (status != null) {
            errorCode = switch (status.toString()) {
                case "403" -> {
                    yield ERROR_403;
                }
                case "404" -> {
                    yield ERROR_404;
                }
                default -> {
                    yield ERROR_500;
                }
            };
        }
        return errorCode;
    }
}
