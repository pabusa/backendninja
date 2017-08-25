package com.udemy.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@GetMapping("/contactform")
	private String redirectContactForm(Model model){
		
		model.addAttribute("contactModel", new ContactModel());
		return ViewConstant.CONTACT_FORM;
	}
	
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
		modelAndView.addObject("contacts", contactService.listAllContacts());
		return modelAndView;
	}
	
	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name="id", required=true) int id){
		contactService.removeContact(id);
		
		return showContacts();
	}
	

}
