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

import com.aulas.entidades.Compromisso;
import com.aulas.repository.CompromissoRepository;
import com.aulas.services.CompromissoService;

@ExtendWith(SpringExtension.class)
public class CompromissoServiceTestes {
	private Long idExistente;
	private Long idNaoExistente;
	private Compromisso compromissoValido;
	private Compromisso compromissoInvalido;
	
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;	
		compromissoValido = new Compromisso();
		compromissoInvalido = new Compromisso();
		
		Mockito.when(repository.save(compromissoValido)).thenReturn(compromissoValido);
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(compromissoInvalido);
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Compromisso()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
	}
	@InjectMocks
	CompromissoService service;
	
	@Mock
	CompromissoRepository repository;
	
	@Test
	public void retornaCompromissoQuandoSalvarComSucesso() {
		Compromisso compromisso = service.salvarCompromisso(compromissoValido);
		Assertions.assertNotNull(compromisso);
		Mockito.verify(repository).save(compromissoValido);
		
	}
	@Test
	public void retornaExececaoQuandoSalvarSemSucesso() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> service.salvarCompromisso(compromissoInvalido));
		Mockito.verify(repository).save(compromissoInvalido);

	}
	@Test
	public void retornaCompromissoQuandoIdExiste() {
		Compromisso cp = service.consultarCompromissoPorId(idExistente);
		Assertions.assertNotNull(cp);
		Mockito.verify(repository).findById(idExistente);
	}
	@Test
	public void lancaEntidadeQuandoIdNaoExiste() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.consultarCompromissoPorId(idNaoExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).findById(idNaoExistente);
	}
}
