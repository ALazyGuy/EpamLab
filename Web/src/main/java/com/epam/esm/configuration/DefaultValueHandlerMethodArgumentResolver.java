package com.epam.esm.configuration;

import com.epam.esm.configuration.annotation.DefaultDto;
import com.epam.esm.configuration.annotation.InjectString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class DefaultValueHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(DefaultDto.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        BufferedReader reader = servletRequest.getReader();
        String body = reader.lines().collect(Collectors.joining());
        Class<?> clazz = methodParameter.getParameterType();
        Object dto = new ObjectMapper().readValue(body, clazz);

        for(Field field : clazz.getDeclaredFields()){
            InjectString annotation = field.getAnnotation(InjectString.class);
            if(annotation != null){
                field.setAccessible(true);
                if(ReflectionUtils.getField(field, dto) == null) {
                    ReflectionUtils.setField(field, dto, annotation.value());
                }
            }
        }

        return dto;
    }
}
