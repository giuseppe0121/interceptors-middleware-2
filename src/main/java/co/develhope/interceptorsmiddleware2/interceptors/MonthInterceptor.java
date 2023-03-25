package co.develhope.interceptorsmiddleware2.interceptors;

import co.develhope.interceptorsmiddleware2.entities.Month;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MonthInterceptor implements HandlerInterceptor {

    public static List<Month> months = new ArrayList<>(Arrays.asList(
            new Month(1,"January","Gennaio","Januar"),
            new Month(2,"February","Febraio","Februar"),
            new Month(5,"May","Maggio","Mai"),
            new Month(6,"June","Giugno","Juni"),
            new Month(9,"September","Settembre","September"),
            new Month(11,"November","Novembre","November")));



    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String monthNumberString = request.getHeader("monthNumber");
        if(monthNumberString == null || monthNumberString == ""){
            response.setStatus(400);
            return  false;
        }else {
            int monthNumber = Integer.parseInt(monthNumberString);
            Optional<Month> month = months.stream().filter(singleMonth ->{
                return singleMonth.getMonthNumber() == monthNumber;
            }).findFirst();

            if(month.isPresent()){
                request.setAttribute("MonthInterceptor-month",month.get());
                response.setStatus(200);
                return true;
            }else {
                request.setAttribute("MonthInterceptor-month",new Month(0,"nope","nope","nope"));

            }
            response.setStatus(200);
            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

}