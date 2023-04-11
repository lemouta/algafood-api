package net.mouta.algafood.api.controller;

import net.mouta.algafood.api.assembler.GrupoModelAssembler;
import net.mouta.algafood.api.assembler.UsuarioInputDisassembler;
import net.mouta.algafood.api.assembler.UsuarioModelAssembler;
import net.mouta.algafood.api.model.GrupoModel;
import net.mouta.algafood.api.model.UsuarioModel;
import net.mouta.algafood.api.model.input.SenhaInput;
import net.mouta.algafood.api.model.input.UsuarioComSenhaInput;
import net.mouta.algafood.api.model.input.UsuarioInput;
import net.mouta.algafood.domain.model.Usuario;
import net.mouta.algafood.domain.repository.UsuarioRepository;
import net.mouta.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		return grupoModelAssembler.toCollectionModel(usuario.getGrupos());
	}

	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
	}

}
