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
	
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;
		
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
