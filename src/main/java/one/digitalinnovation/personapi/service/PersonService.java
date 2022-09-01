package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	private PersonMapper personMapper = PersonMapper.INSTANCE ;
	
	public MessageResponseDTO create( PersonDTO personDTO) {
		Person person = personMapper.toModel(personDTO);
		Person savedPerson = personRepository.save(person);

		return MessageResponseDTO
				.builder()
				.message("Created person with ID " + savedPerson.getId())
				.build();
	}

	public List<PersonDTO> listAll() {
		List<Person> allpeople = personRepository.findAll();
		return allpeople.stream()
				.map(personMapper::toDTO)
				.collect(Collectors.toList());
	}

	public PersonDTO findById(Long id) throws PersonNotFoundException{
		Person person = verifyIfExists(id);
		return personMapper.toDTO(person);
	}
	
	public void delete(Long id) throws PersonNotFoundException {
		verifyIfExists(id);
		personRepository.deleteById(id);
	}
	
	private Person verifyIfExists(Long id) throws PersonNotFoundException{
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}
	
}