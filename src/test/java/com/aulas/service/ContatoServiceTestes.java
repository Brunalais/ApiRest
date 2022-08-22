package com.aulas.service;


import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aulas.entidades.Contato;
import com.aulas.repository.ContatoRepository;
import com.aulas.services.ContatoServices;
import com.aulas.services.dto.ContatoDTO;

@ExtendWith(SpringExtension.class)
public class ContatoServiceTestes {
	private Long idExistente;
	private Long idNaoExistente;
	private Contato contato;
	private Contato ContatoInvalido;
	
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;
		contato = new Contato("maria", "maria@gmail.com", "(47)99999-9999");
		ContatoInvalido = new Contato("maria", "mariagmail.com", "(47)99999-99");
		
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(ContatoInvalido);
		Mockito.when(repository.save(contato)).thenReturn(contato);
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Contato()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
	}
	
	@InjectMocks
	ContatoServices service;
	
	@Mock
	ContatoRepository repository;
	
	@Test
	public void retornaExececaoQuandoSalvarSemSucesso() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvar(ContatoInvalido));
		Mockito.verify(repository).save(ContatoInvalido);
		
	}
	
	@Test
	public void retornaContatoDTOQuandoSalvarComSucesso() {
		ContatoDTO contatoDTO = service.salvar(contato);
		Assertions.assertNotNull(contatoDTO);
		Mockito.verify(repository).save(contato);
	}
	
	@Test
	public void retornaNadaAoExcluirComIdExistente() {
		Assertions.assertDoesNotThrow(() -> {
			service.excluirContato(idExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(idExistente);
	}
	@Test
	public void lancaEntidadeNaoEncontradaAoExcluirIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.excluirContato(idNaoExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	@Test
	public void retornarContatoQuandoIdExiste() {
		ContatoDTO ct = service.consultarContatoPorUmId(idExistente); 
		Assertions.assertNotNull(ct);
		Mockito.verify(repository).findById(idExistente);
	}
	@Test
	public void lancaEntidadeNaoEncontrarIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.consultarContatoPorUmId(idNaoExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).findById(idNaoExistente);
	}
	
}
