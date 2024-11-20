package com.example.MercadoFIPP.restcontroller.security.Filter;

import com.example.MercadoFIPP.restcontroller.security.JWTTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Configuração de CORS
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        res.setHeader("Access-Control-Allow-Credentials", "true");

        // Verifica se é uma requisição OPTIONS
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Processar requisições normais (autenticação com JWT)
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Remove o prefixo "Bearer "

            if (JWTTokenProvider.verifyToken(token)) {
                Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);

                // Extrair o nível do token
                Object nivelObj = claims.get("nivel");
                Integer nivel = null;

                if (nivelObj != null) {
                    if (nivelObj instanceof Number) {
                        nivel = ((Number) nivelObj).intValue();
                    } else if (nivelObj instanceof String) {
                        try {
                            nivel = Integer.parseInt((String) nivelObj);
                        } catch (NumberFormatException e) {
                            nivel = null; // Valor inválido
                        }
                    }
                }

                String requestURI = req.getRequestURI();

                // Regras de acesso baseadas no nível numérico
                if (requestURI.startsWith("/api/admin") && (nivel == null || nivel > 1)) {
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    res.getWriter().write("Acesso negado: necessário nível de administrador");
                    return;
                } else if (requestURI.startsWith("/api/vendedor") && (nivel == null || nivel > 2)) {
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    res.getWriter().write("Acesso negado: necessário nível de vendedor ou superior");
                    return;
                } else if (requestURI.startsWith("/api/user") && (nivel == null || nivel > 3)) {
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    res.getWriter().write("Acesso negado: necessário nível de usuário ou superior");
                    return;
                }

                // Se o nível for suficiente, permite a requisição continuar
                chain.doFilter(request, response);
                return;
            }
        }

        // Token ausente ou inválido
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("Não autorizado");
    }
}

    
