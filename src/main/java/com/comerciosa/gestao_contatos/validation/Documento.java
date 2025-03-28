package com.comerciosa.gestao_contatos.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Documento {
    String message() default "Documento inválido (CPF: 11 dígitos, CNPJ: 14 dígitos)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}