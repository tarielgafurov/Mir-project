package kg.mir.Mirproject.config.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.mir.Mirproject.entities.User;
import kg.mir.Mirproject.exception.NotFoundException;
import kg.mir.Mirproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            if (StringUtils.hasText(token)) {
                try {
                    String username = jwtService.verifyToken(token);
                    User user = userRepository.getUserByEmail(username)
                            .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(username)));
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities())
                    );
                } catch (ExpiredJwtException e) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token expired");
                    return;
                } catch (MalformedJwtException | JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
