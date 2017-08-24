package com.udemy.component;

import org.springframework.stereotype.Component;

import com.udemy.entity.Contact;
import com.udemy.model.ContactModel;

@Component("contactConverter")
public class ContactConverter {
	
	public Contact convertContactModel2Contact(ContactModel contactModel){
		Contact contact = new Contact();
		contact.setId(contactModel.getId());
		contact.setCity(contactModel.getCity());
		contact.setFirstname(contactModel.getFirstname());
		contact.setLastname(contactModel.getLastname());
		contact.setTelephone(contactModel.getTelephone());
		return contact;
	}
	
	public ContactModel convertContact2ContactModel(Contact contact){
		ContactModel contactModel = new ContactModel();
		contactModel.setId(contact.getId());
		contactModel.setCity(contact.getCity());
		contactModel.setFirstname(contact.getFirstname());
		contactModel.setLastname(contact.getLastname());
		contactModel.setTelephone(contact.getTelephone());
		return contactModel;
	}

}
