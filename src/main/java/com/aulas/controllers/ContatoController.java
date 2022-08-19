package com.aulas.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aulas.entidades.Contato;
import com.aulas.services.ContatoServices;
import com.aulas.services.dto.ContatoDTO;

@RestController
@RequestMapping("/")
public class ContatoController{
	@Autowired
	ContatoServices service;
	
	@GetMapping
	public String xpto() {
		return "Index de contato";
	}
	@GetMapping("contatos/email/{email}")
	public ResponseEntity<List<ContatoDTO>> getContatoPorEmail(@PathVariable("email") String email){
		return ResponseEntity.ok(service.consultarContatoPorEmail(email));
	}
	
	@PostMapping("/contatos")
	public ResponseEntity<ContatoDTO> saveContato(@Valid @RequestBody Contato contato) {
		ContatoDTO ct = service.salvar(contato);
		System.out.print(ct.getNome());
		return ResponseEntity.status(HttpStatus.CREATED).body(ct);
	}
	
	@GetMapping("/contatos")
	public ResponseEntity<List<ContatoDTO>> getContatos(){
		List<ContatoDTO> contatos = service.consultarContatos();
		return ResponseEntity.status(HttpStatus.OK).body(contatos);
	}
		
	@GetMapping("/contatos/{idcontato}")
	public ResponseEntity<ContatoDTO> getContatoById(@PathVariable("idcontato") Long idcontato) {
		ContatoDTO ct = service.consultarContatoPorUmId(idcontato);
		return ResponseEntity.ok(ct);
	}
	
	@DeleteMapping("contatos/{idcontato}")
	public ResponseEntity<Void> deleteContato(@PathVariable("idcontato") Long idcontato) {
		service.excluirContato(idcontato);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("contatos/{idcontato}")
	public ResponseEntity<ContatoDTO> alterarContato(@PathVariable("idcontato") Long idcontato, 
			@RequestBody Contato contato ) {
		 return ResponseEntity.ok(service.alterarContato(idcontato,contato));
	}
	
		
}