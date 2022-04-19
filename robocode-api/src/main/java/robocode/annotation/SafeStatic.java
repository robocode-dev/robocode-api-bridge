package robocode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for marking a static field as being safe so that Robocode should
 * not print out warnings at runtime when this robocode.annotation is being used.
 * For example, Robocode will print out warnings if a static field to a robot is found.
 * But not when the @SafeStatic is declared for the static field.
 *
 * @author Flemming N. Larsen (original)
 * @since 1.7.2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused") // API
public @interface SafeStatic {
}