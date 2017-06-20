package it.uniroma3.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.spring.model.Artista;
import it.uniroma3.spring.model.User;
import it.uniroma3.spring.service.ArtistaService;
import it.uniroma3.spring.service.OperaService;

@Controller
public class ArtistaController {
	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	} 		//github dà problemi

		
	@Autowired
	ArtistaService artistaService;
	OperaService operaService;
	@GetMapping("/artisti")
	public String showAutori(Model model){
		List<Artista> artisti = (List<Artista>) artistaService.findAll();
		model.addAttribute("autori", artisti);
		return "artista/autori";
	}
	
	@GetMapping("/artistiAdmin")
	public String showAutoriAdmin(Model model){
		List<Artista> artisti = (List<Artista>) artistaService.findAll();
		model.addAttribute("artisti", artisti);
		return "artista/artistiAdmin";
	}

	@GetMapping("/artista")
	public String showForm(Artista artista) {
		return "artista/formartista";
	}
	@GetMapping("/artista/resultsArtista")
	public String showartista(@RequestParam("id")long id, Model model){
		Artista a = artistaService.findbyId(id);
		model.addAttribute("artista", a);
		return "artista/resultsArtista";
	}

	@GetMapping("/artista/resultatiAU")
	public String showartistaPerUtente(@RequestParam("id")long id, Model model){
		Artista a = artistaService.findbyId(id);
		model.addAttribute("artista", a);
		return "artista/resultatiAU";
	}


	@GetMapping("artista/cancella")
	public ModelAndView deleteartista(@RequestParam("id")long id, Model model){
		artistaService.delete(id);
		return new ModelAndView("redirect:/artisti");
	}
	

	@PostMapping("/artista")
	public String checkCustomerInfo(@Valid @ModelAttribute Artista artista, 
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "artista/formartista";
		} else {
			model.addAttribute(artista);
			artistaService.addArtista(artista); 
		}
		return "artista/resultsArtista";
	}
	@GetMapping("/artista/modificaArtista")
	public String modificaartista(Model model,@RequestParam("id")Long id) {

		Artista artista=artistaService.findbyId(id);
		model.addAttribute("artista",artista);
		return "artista/modificaArtista";
	}

	@PostMapping("/artista/modificaArtista")
	public String modificaAutor(@Valid @ModelAttribute Artista artista, 
			BindingResult bindingResult, Model model ){
		if (bindingResult.hasErrors()) {
			return "artista/modificaArtista";
		}
		else {
			model.addAttribute(artista);
			try{
				artistaService.addArtista(artista);
			}catch(Exception e){
				return"artista/modificaArtista";

			}
		}
		return "artista/resultsartista";
	}
}
