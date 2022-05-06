package de.hsrm.mi.web.projekt.validierung;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD }) // Worauf ist Annotation anwendbar?
@Retention(RetentionPolicy.RUNTIME) // zur Laufzeit vorhanden
@Constraint(validatedBy=BuntValidator.class)
public @interface Bunt {
    String message() default "Diese Farbe enthält zwei gleiche R/G/B-Anteile und ist daher nicht bunt genug.";
    Class<? extends Payload>[] payload() default { };
    Class<?>[] groups() default { };
}
