package com.comerciosa.gestao_contatos.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class DocumentoValidatorTest {
    
    private DocumentoValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new DocumentoValidator();
    }

    @DisplayName("Deve retornar TRUE para documentos válidos")
    @ParameterizedTest(name = "Documento: ${0}")
    @ValueSource(strings = {
        "99999999999",          // CPF sem pontuação
        "999.999.999-99",       // CPF com pontuação
        "11222333000144",       // CNPJ sem pontuação
        "11.222.333/0001-44"    // CNPJ com pontuação
    })
    void deveValidarDocumentosInvalidos(String documentoValido) {
        boolean isValid = validator.isValid(documentoValido, null);
        assertTrue(isValid);
    }

    @DisplayName("Deve retornar FALSO para documentos inválidos")
    @ParameterizedTest(name = "Documento: ${0}")
    @ValueSource(strings = {
        "9999999999",           // Menos de 11 dígitos
        "112223330001",         // Entre 11 e 14 dígitos
        "            ",         // Apenas espaços em branco
        "abd.def.ghi-jk"        // Sem números
    })
    void deveInvalidarDocumentosInvalidos(String documentoInvalido) {
        boolean isValid = validator.isValid(documentoInvalido, null);
        assertFalse(isValid);
    }

    @DisplayName("Deve retornar FALSO para documentos nulos ou vazios")
    @ParameterizedTest(name = "Documento: {0}")
    @NullAndEmptySource 
    void deveInvalidarDocumentosNulosOuVazios(String documentoNuloOuVazio) {
        boolean isValid = validator.isValid(documentoNuloOuVazio, null);
        assertFalse(isValid);
    }
}
