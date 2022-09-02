package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

	private PersonRepository personRepository;
	

	private PersonMapper personMapper = PersonMapper.INSTANCE ;
	
	public MessageResponseDTO create( PersonDTO personDTO) {
		Person person = personMapper.toModel(personDTO);
		Person savedPerson = personRepository.save(person);

		return createMessageResponse(savedPerson.getId(), null);
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
	
	public MessageResponseDTO updateById(Long id, PersonDTO dto) throws PersonNotFoundException {
		verifyIfExists(id);
		
		Person personUpdate = personMapper.toModel(dto);
		Person savedPerson = personRepository.save(personUpdate);

		return createMessageResponse(savedPerson.getId(), null);
	}
	
	public void delete(Long id) throws PersonNotFoundException {
		verifyIfExists(id);
		personRepository.deleteById(id);
	}
	private Person verifyIfExists(Long id) throws PersonNotFoundException{
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}

	private MessageResponseDTO createMessageResponse(Long id, String savedPerson) {
		return MessageResponseDTO
				.builder()
				.message(savedPerson + id)
				.build();
	}
	
}
