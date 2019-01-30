package es.upm.fi.catering.service.backendapp.repository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.upm.fi.catering.service.backendapp.model.Client;

import static java.util.Collections.emptyList;

import java.util.Optional;

@Service
public class ClientDetailsServiceImpl implements UserDetailsService {
	
	private ClientRepository clientRepository;

    public ClientDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findById(username);
        if (!client.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(client.get().getUsuario(), client.get().getPassword(), emptyList());
    }
}
