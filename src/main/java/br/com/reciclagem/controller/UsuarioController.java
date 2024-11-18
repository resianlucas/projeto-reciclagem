package br.com.reciclagem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.reciclagem.model.Usuario;
import br.com.reciclagem.repository.EntregaRepository;
import br.com.reciclagem.repository.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntregaRepository entregaRepository;

    @GetMapping("/")
    public ModelAndView home() {
        Long qtdUsuarios = usuarioRepository.count();
        Long qtdEntregas = entregaRepository.count();
        ModelAndView modelAndView = new ModelAndView("base");
        modelAndView.addObject("qtdUsuarios", qtdUsuarios);
        modelAndView.addObject("qtdEntregas", qtdEntregas);
        return modelAndView;
    }

    @GetMapping("/usuarios")
    public ModelAndView index() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("usuarios");
        modelAndView.addObject("usuarios", usuarios);
        return modelAndView;
    }

    @GetMapping("/usuarios/novo")
    public String create(Usuario usuario) {
        return "usuario-form";
    }

    @PostMapping("/usuarios")
    public String save(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "usuario-form";
        }
        usuarioRepository.save(usuario);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "redirect:/usuarios";
    }
}
