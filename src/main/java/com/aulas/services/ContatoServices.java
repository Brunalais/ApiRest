package com.aulas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aulas.entidades.Contato;
import com.aulas.repository.ContatoRepository;
import com.aulas.services.dto.ContatoDTO;

@Service
public class ContatoServices {
	@Autowired
	ContatoRepository repo;
	
	public ContatoDTO salvar(Contato contato) {
		/*if(contato.getFone().length() !=14) {
		}
		if (!contato.getEmail().contains("@")) {
				throw new EmailInvalidoExeception("Email inválido!");
		}*/
		Contato ct = repo.save(contato);
		ContatoDTO ctDto = new ContatoDTO(ct);
		System.out.print(ctDto.getNome());
		return ctDto;
	}
	
	public List<ContatoDTO> consultarContatos(){
		List<Contato> contatos = repo.findAll();
		List<ContatoDTO> contatosDTO = new ArrayList<>();
		for (Contato ct: contatos) {
			ContatoDTO ctDTO = new ContatoDTO(ct);
			contatosDTO.add(ctDTO);
		}
		return contatosDTO;
	}
	
	public ContatoDTO consultarContatoPorUmId(Long idcontato) {
		Optional<Contato>opContato = repo.findById(idcontato);
		Contato contato = opContato.orElseThrow(( )-> new EntityNotFoundException("Contato não encontrado"));
		return new ContatoDTO(contato);
	}
	
	public void excluirContato(Long idcontato) {
		//Contato ct = consultarContatoPorUmId(idcontato);
		//repo.delete(ct);
		repo.deleteById(idcontato);
	}
	
	public ContatoDTO alterarContato(Long idcontato, Contato contato) {
		//Contato ct = consultarContatoPorId(idcotato);
		//BeanUtils.copyProperties(contato, ct, "id");
		Contato ct = repo.findById(idcontato).get();
		ct.setEmail(contato.getEmail());
		ct.setNome(contato.getNome());
		ct.setFone(contato.getFone());
		ContatoDTO ctDto = new ContatoDTO(repo.save(ct));
		return ctDto;
	}
	
	public List<ContatoDTO> consultarContatoPorEmail(String email){
		return ContatoDTO.converteParaDTO(repo.findByEmail(email));
	}

}
