package com.jesus.voice.config

import com.jesus.voice.common.annotation.ValidateAdmin
import com.jesus.voice.common.util.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ValidateAdminInterceptor(
    @Value("\${app.service-key}") var appAdminToken: String,
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            handler.getMethodAnnotation(ValidateAdmin::class.java)?.let { _ ->
                if (request.getHeader(ADMIN_TOKEN_HEADER) != appAdminToken) {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    log.info("\uD83D\uDEA8 admin token is available")
                    return false
                }
            }
        }
        return true
    }

    companion object {
        private const val ADMIN_TOKEN_HEADER = "X-Service-Key"
        private val log by logger()
    }
}