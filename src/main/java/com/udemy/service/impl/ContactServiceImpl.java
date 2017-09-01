package com.udemy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.component.ContactConverter;
import com.udemy.entity.Contact;
import com.udemy.model.ContactModel;
import com.udemy.repository.ContactRepository;
import com.udemy.service.ContactService;


@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService{
	
	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;
	
	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;

	@Override
	public ContactModel addContact(ContactModel contactModel) {
		/* 1º Se guarda en el repositorio el Contacto, lo que nos devuelve el contacto guardado.
		 * Como al servicio le llega un Model (contactModel) desde el Controller
		 * Hay que crear y usar el componente Converter para pasar del Model a Entity (ContactModel -> Contact)
		*/
		Contact contact = contactRepository.save(contactConverter.convertContactModel2Contact(contactModel));
		
		/* 2º como el servicio debe devolver un Model al Controller, hay que usar 
		 * el converter en el otro sentido para devolverselo
		 */
		return contactConverter.convertContact2ContactModel(contact);
	}

	@Override
	public List<ContactModel> listAllContacts() {
		List<Contact> contacts = contactRepository.findAll();
		List<ContactModel> contactModels= new ArrayList<ContactModel>();
		
		for(Contact contact : contacts){
			contactModels.add(contactConverter.convertContact2ContactModel(contact));
		}
		
		return contactModels;
	}

	@Override
	public Contact findContactyId(int id) {
		return contactRepository.findById(id);
	}
	
	@Override
	public ContactModel findContactByIdModel(int id) {
		return contactConverter.convertContact2ContactModel(findContactyId(id));
	}

	@Override
	public void removeContact(int id) {
		Contact contact = findContactyId(id);
		if (contact != null){
			contactRepository.delete(contact);
		}
	}
}
