package br.com.aceleragep.biblioteca.controllers;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.aceleragep.biblioteca.configs.ControllerConfig;
import br.com.aceleragep.biblioteca.converts.AutorConvert;
import br.com.aceleragep.biblioteca.converts.LivroConvert;
import br.com.aceleragep.biblioteca.dtos.inputs.AutorInput;
import br.com.aceleragep.biblioteca.dtos.outputs.AutorOutput;
import br.com.aceleragep.biblioteca.dtos.outputs.LivroAutorOutput;
import br.com.aceleragep.biblioteca.entities.AutorEntity;
import br.com.aceleragep.biblioteca.entities.LivroEntity;
import br.com.aceleragep.biblioteca.services.AutorService;
import br.com.aceleragep.biblioteca.services.LivroService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/autores")
public class AutorController {

	@Autowired
	private AutorService autorService;

	@Autowired
	private AutorConvert autorConvert;

	@Autowired
	private LivroService livroService;

	@Autowired
	private LivroConvert livroConvert;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public AutorOutput cria(@RequestBody @Valid AutorInput autorInput) {
		AutorEntity autorEntity = autorConvert.inputToNewEntity(autorInput);
		AutorEntity autorCriado = autorService.cria(autorEntity);
		return autorConvert.entityToOutput(autorCriado);
	}

	@PutMapping("/{id}")
	public AutorOutput atualiza(@PathVariable Long id, @RequestBody @Valid AutorInput autorInput) {
		AutorEntity autorEncontrado = autorService.buscaPeloId(id);
		autorConvert.copyInputToEntity(autorEncontrado, autorInput);
		AutorEntity autorAtualizado = autorService.atualiza(autorEncontrado);
		return autorConvert.entityToOutput(autorAtualizado);
	}

	@GetMapping
	public Page<AutorOutput> listaTodos(
			@ParameterObject @PageableDefault(page = 0, size = 5, sort = "nome", direction = Direction.ASC) Pageable paginacao) {
		Page<AutorEntity> listaTodos = autorService.listaTodos(paginacao);
		return autorConvert.listPageEntityToListPageOutput(listaTodos);
	}

	@GetMapping("/{id}")
	public AutorOutput buscaPeloId(@PathVariable Long id) {
		AutorEntity autorEncontrado = autorService.buscaPeloId(id);
		return autorConvert.entityToOutput(autorEncontrado);

	}

	@GetMapping("/{id}/livros")
	public Page<LivroAutorOutput> buscaLivrosPeloIdDoAutor(@PathVariable Long id,
			@ParameterObject @PageableDefault(page = 0, size = 5, sort = "titulo", direction = Direction.ASC) Pageable paginacao) {
		autorService.buscaPeloId(id);
		Page<LivroEntity> livros = livroService.buscaLivrosPeloIdAutor(id, paginacao);
		return livroConvert.listPageEntityToListPageOutputCopy(livros);
	}

}
