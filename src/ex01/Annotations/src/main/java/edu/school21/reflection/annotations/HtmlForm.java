package edu.school21.reflection.annotations;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.CLASS)
public @interface HtmlForm {
    String fileName();
    String action();
    String method();
}
