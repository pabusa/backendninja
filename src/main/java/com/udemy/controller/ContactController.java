package com.udemy.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udemy.constant.ViewConstant;
import com.udemy.model.ContactModel;
import com.udemy.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	
	private static final Log LOG = LogFactory.getLog(ContactController.class);
	
	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;

	@GetMapping("/cancel")
	private ModelAndView cancel(RedirectAttributes redirectAttributes){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/contacts/showcontacts");
		return mav;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/contactform")
	private String redirectContactForm(
			@RequestParam(name="id", required=false) int id,
			Model model){
		ContactModel contactModel = new ContactModel();
		if (id != 0){
			contactModel = contactService.findContactByIdModel(id);
		}
		
		model.addAttribute("contactModel", contactModel);
		return ViewConstant.CONTACT_FORM;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/addcontact")
	public ModelAndView addContact(
			@ModelAttribute(name="contactModel") ContactModel contactModel, 
			Model model, 
			RedirectAttributes redirectAttributes){
		
		LOG.info("METHOD: addcontact() -- PARAMS: "+ contactModel.toString());
		
		if(contactService.addContact(contactModel) != null){
			redirectAttributes.addFlashAttribute("result", 1);
		} else {
			redirectAttributes.addFlashAttribute("result", 0);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/contacts/showcontacts");
		return mav;
	}
	
	@GetMapping("/showcontacts")
	public ModelAndView showContacts(){
		ModelAndView modelAndView = new ModelAndView(ViewConstant.CONTACTS);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		modelAndView.addObject("username", user.getUsername());
		modelAndView.addObject("contacts", contactService.listAllContacts());
		return modelAndView;
	}
	
	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name="id", required=true) int id){
		contactService.removeContact(id);
		
		return showContacts();
	}
	
}
