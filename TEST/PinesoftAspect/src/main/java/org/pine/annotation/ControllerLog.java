package org.pine.annotation;
import java.lang.annotation.*;  
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public  @interface ControllerLog {  
    String description()  default "";  
}
