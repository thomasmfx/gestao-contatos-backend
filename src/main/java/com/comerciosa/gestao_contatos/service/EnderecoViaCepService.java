package com.comerciosa.gestao_contatos.service;

import com.comerciosa.gestao_contatos.dto.response.EnderecoResponseDTO;
import com.comerciosa.gestao_contatos.exception.BadRequestException;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.mapper.EnderecoViaCepMapper;
import com.comerciosa.gestao_contatos.model.EnderecoViaCep;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class EnderecoViaCepService {
    private final RestTemplate restTemplate;
    private final EnderecoViaCepMapper enderecoViaCepMapper;

    public EnderecoResponseDTO getEnderecoByCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            EnderecoViaCep enderecoViaCep = restTemplate.getForObject(url, EnderecoViaCep.class);

            if (enderecoViaCep == null || Boolean.TRUE.equals(enderecoViaCep.getErro())) {
                throw new ResourceNotFoundException("Endereço não encontrado com o CEP " + cep);
            }

            return enderecoViaCepMapper.toEnderecoResponseDTO(enderecoViaCep);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException("CEP informado possui um formato inválido");
        } catch (Exception e) {
            throw e;
        }
    }
}
