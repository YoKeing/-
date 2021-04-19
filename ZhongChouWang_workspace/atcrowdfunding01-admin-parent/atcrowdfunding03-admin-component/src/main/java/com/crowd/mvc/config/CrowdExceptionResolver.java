package com.crowd.mvc.config;

import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowd.util.constant.CrowdConstant;
import com.crowd.util.exception.LoginAcctAlreadyInUseException;
import com.crowd.util.exception.LoginAcctAlreadyInUseForUpdateException;
import com.crowd.util.exception.LoginFailedException;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException( LoginAcctAlreadyInUseForUpdateException exception,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws IOException {

        String viewName = "system-error";
        return commResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException( LoginAcctAlreadyInUseException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws IOException {

        String viewName = "admin-add";
        return commResolve(viewName, exception, request, response);
    }


    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException( NullPointerException exception,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws IOException {
        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        if (judgeResult){
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            response.getWriter().write(json);
            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("system-error");
        return modelAndView;

    }


    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException( LoginFailedException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commResolve(viewName, exception, request, response);
    }


    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException( LoginFailedException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commResolve(viewName, exception, request, response);
    }


    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(
            NullPointerException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

//        boolean judgeResult = CrowdUtil.judgeRequestType(request);
//
//        if (judgeResult){
//            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
//            Gson gson = new Gson();
//            String json = gson.toJson(resultEntity);
//            response.getWriter().write(json);
//            return null;
//        }
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", exception);
//        modelAndView.setViewName("system-error");

        String viewName = "system-error";

        return commResolve(viewName, exception, request, response);

    }

    private ModelAndView commResolve(String viewName,
                                     Exception exception,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        if (judgeResult){
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            response.getWriter().write(json);
            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}
