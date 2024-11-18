package br.com.reciclagem.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.reciclagem.model.Entrega;
import br.com.reciclagem.model.Usuario;
import br.com.reciclagem.repository.EntregaRepository;
import br.com.reciclagem.repository.UsuarioRepository;

@Controller
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    EntregaRepository entregaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping()
    public ModelAndView index() {
        List<Entrega> entregas = entregaRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("entregas");
        modelAndView.addObject("entregas", entregas);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable Long id) {
        List<Entrega> entregas = entregaRepository.findByUsuario_Id(id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        ModelAndView modelAndView = new ModelAndView("usuario-entregas");
        modelAndView.addObject("usuario", usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        modelAndView.addObject("entregas", entregas);
        return modelAndView;
    }

    @GetMapping("/new/{id}")
    public ModelAndView create(@PathVariable Long id, Entrega entrega) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        entrega.setDataEntrega(new Date());
        entrega.setQuantidade(0);
        entrega.setPontosGerados(0);
        ModelAndView modelAndView = new ModelAndView("entrega-form");
        modelAndView.addObject("usuarioId", usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado")).getId());
        modelAndView.addObject("nomeUsuario", usuario.get().getName());
        modelAndView.addObject("dataAtual", new Date());
        return modelAndView;
    }

    @PostMapping("/new/{id}")
    public String save(@PathVariable Long id, @Valid Entrega entrega, BindingResult result, Model model) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        Usuario usuarioEncontrado = usuario.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        entrega.setUsuario(usuarioEncontrado);

        if (result.hasErrors()) {
            model.addAttribute("usuarioId", usuarioEncontrado.getId());
            model.addAttribute("nomeUsuario", usuarioEncontrado.getName());
            return "entrega-form";
        }

        entrega.setId(null);

        // Calcular os pontos gerados com base no material e quantidade.
        entrega.setPontosGerados(entrega.getQuantidade() * 10);

        // Atualizar os pontos acumulados do usuário.
        usuarioEncontrado.setPontosAcumulados(usuarioEncontrado.getPontosAcumulados() + entrega.getPontosGerados());
        usuarioRepository.save(usuarioEncontrado);

        entregaRepository.save(entrega);
        return "redirect:/entregas/" + id;
    }
}
