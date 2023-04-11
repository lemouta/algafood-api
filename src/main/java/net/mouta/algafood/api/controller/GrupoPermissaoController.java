package net.mouta.algafood.api.controller;

import net.mouta.algafood.api.assembler.GrupoInputDisassembler;
import net.mouta.algafood.api.assembler.GrupoModelAssembler;
import net.mouta.algafood.api.assembler.PermissaoModelAssembler;
import net.mouta.algafood.api.model.GrupoModel;
import net.mouta.algafood.api.model.PermissaoModel;
import net.mouta.algafood.api.model.input.GrupoInput;
import net.mouta.algafood.domain.model.Grupo;
import net.mouta.algafood.domain.repository.GrupoRepository;
import net.mouta.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
	}

	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
	}

	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}

}
