package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/clientes")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;

	// Obter um cliente pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Cliente>> obterCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, id);
		if (cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			adicionadorLink.adicionarLink(cliente);
			EntityModel<Cliente> entityModel = EntityModel.of(cliente,
					linkTo(methodOn(ClienteControle.class).obterCliente(id)).withSelfRel(),
					linkTo(methodOn(ClienteControle.class).obterClientes()).withRel("todos-clientes"));
			return ResponseEntity.ok(entityModel);
		}
	}

	// Obter todos os clientes
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Cliente>>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		if (clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			clientes.forEach(adicionadorLink::adicionarLink);
			List<EntityModel<Cliente>> clientesModel = clientes.stream()
					.map(cliente -> EntityModel.of(cliente,
							linkTo(methodOn(ClienteControle.class).obterCliente(cliente.getId())).withSelfRel()))
					.toList();
			CollectionModel<EntityModel<Cliente>> collectionModel = CollectionModel.of(clientesModel,
					linkTo(methodOn(ClienteControle.class).obterClientes()).withSelfRel());
			return ResponseEntity.ok(collectionModel);
		}
	}

	// Cadastrar um novo cliente
	@PostMapping("/cadastro")
	public ResponseEntity<EntityModel<Cliente>> cadastrarCliente(@RequestBody Cliente cliente) {
		if (cliente.getId() != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		Cliente clienteSalvo = repositorio.save(cliente);
		EntityModel<Cliente> entityModel = EntityModel.of(clienteSalvo,
				linkTo(methodOn(ClienteControle.class).obterCliente(clienteSalvo.getId())).withSelfRel(),
				linkTo(methodOn(ClienteControle.class).obterClientes()).withRel("todos-clientes"));
		return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
	}

	// Atualizar um cliente existente
	@PutMapping("/atualizar")
	public ResponseEntity<EntityModel<Cliente>> atualizarCliente(@RequestBody Cliente atualizacao) {
		if (atualizacao.getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		Cliente clienteExistente = repositorio.findById(atualizacao.getId())
				.orElse(null);
		if (clienteExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(clienteExistente, atualizacao);
		repositorio.save(clienteExistente);
		EntityModel<Cliente> entityModel = EntityModel.of(clienteExistente,
				linkTo(methodOn(ClienteControle.class).obterCliente(clienteExistente.getId())).withSelfRel(),
				linkTo(methodOn(ClienteControle.class).obterClientes()).withRel("todos-clientes"));
		return ResponseEntity.ok(entityModel);
	}

	// Excluir um cliente
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirCliente(@PathVariable Long id) {
		Cliente cliente = repositorio.findById(id)
				.orElse(null);
		if (cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		repositorio.delete(cliente);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
