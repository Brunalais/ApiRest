package com.aulas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.aulas.entidades.Compromisso;
import com.aulas.repository.CompromissoRepository;

@Service
public class CompromissoService {
	@Autowired
	CompromissoRepository repo;
	
	public Compromisso salvarCompromisso(Compromisso compromisso) {
		return repo.save(compromisso);
		
	}
	
	public List<Compromisso> consultarTodosCompromissos() {
		return repo.findAll();
		
	}
	public Compromisso consultarCompromissoPorId(Long id) {
		return repo.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	
	


}
