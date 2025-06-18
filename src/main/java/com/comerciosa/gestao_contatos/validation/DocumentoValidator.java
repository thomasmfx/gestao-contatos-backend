package com.comerciosa.gestao_contatos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentoValidator implements ConstraintValidator<Documento, String> {

    @Override
    public boolean isValid(String doc, ConstraintValidatorContext context) {
        if (doc == null || doc.trim().isEmpty()) {
            return false;
        }

        String numeros = doc.replaceAll("[\\D]", ""); // Remove não-numéricos

        return numeros.length() == 11 || numeros.length() == 14;
    }
}
