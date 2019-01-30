package es.upm.fi.catering.service.backendapp.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import es.upm.fi.catering.service.backendapp.exceptions.ResourceAlreadyExistException;
import es.upm.fi.catering.service.backendapp.exceptions.ResourceNotFoundException;
import es.upm.fi.catering.service.backendapp.exceptions.ResourceUnauthorized;
import es.upm.fi.catering.service.backendapp.model.CantidadProductos;
import es.upm.fi.catering.service.backendapp.model.Client;
import es.upm.fi.catering.service.backendapp.model.Email;
import es.upm.fi.catering.service.backendapp.model.FacturaCliente;
import es.upm.fi.catering.service.backendapp.model.Ingrediente;
import es.upm.fi.catering.service.backendapp.model.PedidoCliente;
import es.upm.fi.catering.service.backendapp.model.Producto;
import es.upm.fi.catering.service.backendapp.model.ProductsPedidoSerializer;
import es.upm.fi.catering.service.backendapp.model.UserTypeEnum;
import es.upm.fi.catering.service.backendapp.repository.ClientRepository;
import es.upm.fi.catering.service.backendapp.repository.EmailRepository;
import es.upm.fi.catering.service.backendapp.repository.FacturaClienteRepository;
import es.upm.fi.catering.service.backendapp.repository.IngredienteRepository;
import es.upm.fi.catering.service.backendapp.repository.PedidoClienteRepository;
import es.upm.fi.catering.service.backendapp.repository.ProductRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/")
public class ClientController {

	private static final String DIRECTORIO_IMG = "/assets/img/";

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PedidoClienteRepository pedidoClienteRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private FacturaClienteRepository facturaClienteRepository;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Método para recuperar todos los clientes
	 * 
	 * @return devuelve todos los clientes
	 */
	@GetMapping("/v1/clients")
	public MappingJacksonValue retrieveAllClients() {
		List<Client> clientList = clientRepository.findAll();

		MappingJacksonValue mapping = filterListClientPass(clientList,
				new HashSet<String>(Arrays.asList("password", "cif")));

		return mapping;
	}

	/**
	 * Método para recuperar los detalles de un cliente
	 * 
	 * @param user
	 * @return devuelve los detalles del usuario pasado como parámetro
	 */
	@GetMapping("/v1/clients/{user}")
	public MappingJacksonValue retrieveClient(@PathVariable String user) {
		Optional<Client> client = clientRepository.findById(user);
		if (!client.isPresent())
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");

		MappingJacksonValue mapping = filterListClientPass(client.get(), new HashSet<String>());

		return mapping;
	}

	/**
	 * Método para modificar un cliente
	 * 
	 * @param user
	 * @param client
	 * @return noContent
	 */
	@PutMapping("/v1/clients/{user}")
	public ResponseEntity<Object> updateClient(@PathVariable String user, @Valid @RequestBody Client client) {

		Optional<Client> updatedClient = clientRepository.findById(user);

		if (updatedClient.isPresent() && (user.equals(client.getUsuario())
				|| updatedClient.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN")))) {

			Optional<Client> savedClient = clientRepository.findById(client.getUsuario());
			
			if (!savedClient.isPresent()) {
				throw new ResourceNotFoundException("El usuario " + client.getUsuario() + " no se ha encontrado");
			}
			if(client.getPassword()!=null) {
				client.setPedidoCliente(savedClient.get().getPedidoCliente());
				client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
				
				clientRepository.save(client);			
			} else {
				clientRepository.modificarCliente(client.getUsuario(), client.getNombre(), client.getCorreo(),
						client.getFechaNacimiento(), client.getRazonSocial(), client.getCif(), client.getTelefono(),
						client.getAlergenos(), client.getNecesidadesAlimentacion());
			}

			return ResponseEntity.noContent().build();

		} else {
			if (!updatedClient.isPresent()) {
				throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
			} else {
				throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
			}
		}
	}

	/**
	 * Método para crear un cliente
	 * 
	 * @param client
	 * @return created
	 */
	@PostMapping("/v1/clients/signUp")
	public ResponseEntity<Object> createClient(@Valid @RequestBody Client client) {
		Optional<Client> savedClient = clientRepository.findById(client.getUsuario());
		Client clientAux = null;
		if (!savedClient.isPresent()) {
			client.setPassword(bCryptPasswordEncoder.encode((CharSequence)client.getPassword()));
			clientAux = clientRepository.save(client);
		} else {
			throw new ResourceAlreadyExistException("El usuario " + client.getUsuario() + " ya existe");
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{user}")
				.buildAndExpand(clientAux.getUsuario()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Método para borrar un cliente
	 * 
	 * @param user
	 */
	@DeleteMapping("/v1/clients/{user}")
	public void deleteClient(@PathVariable String user) {
		Optional<Client> client = clientRepository.findById(user);
		if (!client.isPresent())
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");

		clientRepository.deleteById(user);
	}

	/**
	 * Método para que el administrador recupere todos los pedidos
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/v1/clientsAd/{user}/orders")
	public List<PedidoCliente> retrieveAllPedidos(@PathVariable String user) {
		Optional<Client> admin = clientRepository.findById(user);
		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}
		return pedidoClienteRepository.findAllActivos();
	}

	/**
	 * Método para recuperar todos los pedidos de un cliente
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/v1/clients/{user}/orders")
	public List<PedidoCliente> retrieveAllPedidosFromClient(@PathVariable String user) {
		Optional<Client> client = clientRepository.findById(user);
		if (!client.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		return client.get().getPedidoCliente();
	}

	/**
	 * Recupera el pedido {id} para del usuario {user}
	 * 
	 * @param user
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@GetMapping("/v1/clients/{user}/orders/{id}")
	public PedidoCliente retrievePedidoFromClient(@PathVariable String user, @PathVariable Integer id) throws JsonProcessingException {
		Optional<Client> client = clientRepository.findById(user);
		if (!client.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		PedidoCliente pedidoResponse = null;
		for (PedidoCliente pedidoCliente : client.get().getPedidoCliente()) {
			if (id.equals(pedidoCliente.getId())) {
				pedidoResponse = pedidoCliente;
			}
		}

		if (pedidoResponse == null) {
			throw new ResourceNotFoundException("El pedido con id " + id + " no existe");
		}
		/*ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(PedidoCliente.class, new PedidoClienteSerializer());
		mapper.registerModule(module);
		String serialized = mapper.writeValueAsString(pedidoResponse);*/
		return pedidoResponse;
	}

	/**
	 * Método para que el administrador borre el pedido de un cliente
	 * 
	 * @param user
	 * @param id
	 */
	@DeleteMapping("/v1/clientsAd/{user}/orders/{id}")
	public void deletePedidoFromClient(@PathVariable String user, @PathVariable Integer id) {
		Optional<Client> cliente = clientRepository.findById(user);

		if (!cliente.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}

		Optional<PedidoCliente> pedidoCliente = pedidoClienteRepository.findById(id);

		if (!pedidoCliente.isPresent()) {
			throw new ResourceNotFoundException("El pedido " + id + " no se ha encontrado");
		}

		if (pedidoCliente.get().getCliente().getUsuario().equals(user) || cliente.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			pedidoClienteRepository.deleteById(id);
		} else {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}
	}

	/**
	 * Crear un pedido para un Cliente
	 * 
	 * @param user
	 * @param pedido
	 * @return
	 */
	@PostMapping("/v1/clients/{user}/orders")
	public ResponseEntity<Object> createPedidoCliente(@PathVariable String user,
			@Valid @RequestBody ProductsPedidoSerializer productosPedidoSerializer) {
		Optional<Client> clientAux = clientRepository.findById(user);

		if (!clientAux.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		
		PedidoCliente pedidoClienteAux2 = null;
		
		PedidoCliente pedidoAux = productosPedidoSerializer.getPedidoCliente();
		
		if(pedidoAux.getId() != null) {		
			Optional<PedidoCliente> pedido = pedidoClienteRepository.findById(pedidoAux.getId());
			
			if(pedido.isPresent()) {
				throw new ResourceAlreadyExistException("El pedido " + pedidoAux.getId() + " ya existe");
			}
		}
		
		List<CantidadProductos> listaCantidadProducto = productosPedidoSerializer.getCantidadProductos();
		
		pedidoAux.setCliente(clientAux.get());
		
		pedidoClienteAux2 = pedidoClienteRepository.save(pedidoAux);
		
		Iterator<CantidadProductos> it = listaCantidadProducto.iterator();
		
		while(it.hasNext()) {
			CantidadProductos cantidadProductos = it.next();
			pedidoClienteAux2.addProducto(cantidadProductos.getProducto(), cantidadProductos.getCantidad());
		}
		
		pedidoClienteAux2 = pedidoClienteRepository.save(pedidoClienteAux2);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pedidoClienteAux2.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Método para que el administrador actualice a entregada + factura
	 * 
	 * @param user
	 * @param pedidoCliente
	 * @return
	 */
	@PutMapping("/v1/clientsAd/{user}/orders/{id}")
	public ResponseEntity<Object> updatePedidoClienteAdmin(@PathVariable String user, @PathVariable Integer id,
			@Valid @RequestBody PedidoCliente pedidoCliente, @RequestParam String tipoImp, @RequestParam Double coste ) {

		Optional<Client> admin = clientRepository.findById(user);

		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}

		Optional<PedidoCliente> pedidoClienteAux = pedidoClienteRepository.findById(id);
		if (!pedidoClienteAux.isPresent()) {
			throw new ResourceNotFoundException("El pedido nº " + id + " no se ha encontrado");
		}
		FacturaCliente factura = new FacturaCliente();
		
		factura.setConcepto("Pedido Catering nº"+ id);
		factura.setImpuesto(tipoImp);
		factura.setCoste(coste);
		
		factura = facturaClienteRepository.save(factura);
		
		pedidoCliente.setCliente(pedidoClienteAux.get().getCliente());
		pedidoCliente.setId(id);
		pedidoCliente.setFacturaCliente(factura);
		pedidoCliente.setFechaEntrega(new Date());

		pedidoClienteRepository.save(pedidoCliente);

		return ResponseEntity.noContent().build();

	}


	/**
	 * Método para que el administrador cree un producto
	 * 
	 * @param user
	 * @param producto
	 * @return
	 */
	@PostMapping("/v1/clientsAd/{user}/products")
	public ResponseEntity<Object> createProductAdmin(@PathVariable String user, @Valid @RequestBody Producto producto) {
		Optional<Client> admin = clientRepository.findById(user);

		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}

		producto.setNombreImg(DIRECTORIO_IMG + producto.getNombreImg());
		
		List<Ingrediente> listaIngredientes = new ArrayList<>();
		
		for(Ingrediente ingrediente: producto.getListaIngredientes()) {
			Ingrediente ingredienteAux = ingredienteRepository.save(ingrediente);
			listaIngredientes.add(ingredienteAux);
		}

		producto.setListaIngredientes(listaIngredientes);
		
		Producto productoResponse = productRepository.save(producto);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productoResponse.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Método para que el administrador borre un producto
	 * 
	 * @param user
	 * @param id
	 */
	@DeleteMapping("/v1/clientsAd/{user}/products/{id}")
	public void deleteProductAdmin(@PathVariable String user, @PathVariable Integer id) {
		Optional<Client> admin = clientRepository.findById(user);

		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}

		productRepository.deleteById(id);
	}

	/**
	 * Método para que el administrador modifique un producto
	 * 
	 * @param user
	 * @param producto
	 * @return
	 */
	@PutMapping("/v1/clientsAd/{user}/products/{id}")
	public ResponseEntity<Object> updateProductAdmin(@PathVariable String user, @PathVariable Integer id,
			@Valid @RequestBody Producto producto) {

		Optional<Client> admin = clientRepository.findById(user);

		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}

		Optional<Producto> productoAux = productRepository.findById(id);
		if (!productoAux.isPresent()) {
			throw new ResourceNotFoundException("El producto " + producto.getNombre() + " no se ha encontrado");
		}
		
		producto.setNombreImg(DIRECTORIO_IMG + producto.getNombreImg());
		
		List<Ingrediente> listaIngredientes = new ArrayList<>();
		
		for(Ingrediente ingrediente: producto.getListaIngredientes()) {
			Ingrediente ingredienteAux = ingredienteRepository.save(ingrediente);
			listaIngredientes.add(ingredienteAux);
		}

		producto.setListaIngredientes(listaIngredientes);
		producto.setId(id);

		productRepository.save(producto);

		return ResponseEntity.noContent().build();

	}

	// /**
	// *
	// * @param user
	// * @param productosMenu
	// * @return
	// */
	// @PostMapping("/v1/clients/{user}/menus")
	// public ResponseEntity<Object> createMenuProducts(@PathVariable String user,
	// @Valid @RequestBody ) {
	// Optional<Client> admin = clientRepository.findByUsuario(user);
	//
	// if (!admin.isPresent()) {
	// throw new ResourceNotFoundException("El usuario " + user + " no se ha
	// encontrado");
	// }
	// if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
	// throw new ResourceUnauthorized("No tiene permiso para acceder a este
	// recurso");
	// }
	//
	// URI location =
	// ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand().toUri();
	//
	// return ResponseEntity.created(location).build();
	// }

	/**
	 * Método para recuperar todos los productos (con o sin filtros)
	 * 
	 * @return devuelve todos los clientes
	 */
	@GetMapping("/v1/products")
	public List<Producto> retrieveAllProducts(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) Double pMin, @RequestParam(required = false) Double pMax) {
		List<Producto> productsList = null;
		if (nombre != null && pMin == null && pMax == null) {
			productsList = productRepository.findAllByNom(nombre);
		} else if (nombre != null && pMin == null && pMax != null) {
			productsList = productRepository.findAllByNombreAndPrecioMax(nombre, pMax);
		} else if (nombre != null && pMin != null && pMax == null) {
			productsList = productRepository.findAllByNombreAndPrecioMin(nombre, pMin);
		} else if (nombre != null && pMin != null && pMax != null) {
			productsList = productRepository.findAllByNombreAndPrecioMinAndPrecioMax(nombre, pMin, pMax);
		} else if (nombre == null && pMin != null && pMax != null) {
			productsList = productRepository.findAllByPrecioMinAndPrecioMax(pMin, pMax);
		} else if (nombre == null && pMin == null && pMax != null) {
			productsList = productRepository.findAllByPrecioMax(pMax);
		} else if (nombre == null && pMin != null && pMax == null) {
			productsList = productRepository.findAllByPrecioMin(pMin);
		} else {
			productsList = productRepository.findAllDisp();
		}
		return productsList;
	}

	/**
	 * Método para que el administrador recupere todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/v1/clientsAd/{user}/products")
	public List<Producto> retrieveAllProductAdmin(@PathVariable String user, @RequestParam(required = false) String nombre) {
		Optional<Client> admin = clientRepository.findById(user);

		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}
		List<Producto> productsList = null;
		if (nombre != null) {
			productsList = productRepository.findAllByNombre(nombre);
		} else {
			productsList = productRepository.findAll();
		}
		
		return productsList;
	}
	
	/**
	 * Recupera el pedido {id} para del usuario {user}
	 * 
	 * @param user
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@GetMapping("/v1/clientsAd/{user}/products/{id}")
	public Producto retrieveProductAdmin(@PathVariable String user, @PathVariable Integer id) {
		Optional<Client> admin = clientRepository.findById(user);
		if (!admin.isPresent()) {
			throw new ResourceNotFoundException("El usuario " + user + " no se ha encontrado");
		}
		if (!admin.get().getTipoCliente().equals(UserTypeEnum.valueOf("ADMIN"))) {
			throw new ResourceUnauthorized("No tiene permiso para acceder a este recurso");
		}
		return productRepository.findById(id).get();
	}
	
	/**
	 * Método para enviar correos
	 * 
	 * @return
	 */
	@PostMapping("/v1/contact")
	public ResponseEntity<Object> createEmailContact(@Valid @RequestBody Email email) {

		Email emailResponse = null;
		
		if(enviarMail(email)) {
			
			emailResponse = emailRepository.save(email);
			
		} else {
			
			throw new ResourceNotFoundException("Error al enviar el email");
			
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(emailResponse.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Método para quitar algún campo en la respuesta de la lista de clientes
	 * 
	 * @param clientList
	 * @param fieldToRemove
	 * @return
	 */
	private MappingJacksonValue filterListClientPass(Object clientList, Set<String> fieldToRemove) {

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(fieldToRemove);

		FilterProvider filters = new SimpleFilterProvider().addFilter("client-filter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(clientList);
		mapping.setFilters(filters);

		return mapping;

	}
	
	private boolean enviarMail(Email email) {
		
		  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		  // Get a Properties object
		     Properties props = System.getProperties();
		     props.setProperty("mail.smtp.host", "smtp.gmail.com");
		     props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		     props.setProperty("mail.smtp.socketFactory.fallback", "false");
		     props.setProperty("mail.smtp.port", "465");
		     props.setProperty("mail.smtp.socketFactory.port", "465");
		     props.put("mail.smtp.auth", "true");
		     props.put("mail.debug", "true");
		     props.put("mail.store.protocol", "pop3");
		     props.put("mail.transport.protocol", "smtp");
		     final String username = "11sergiocf1c@gmail.com";//
		     final String password = "febrero3";
		     try{
		     Session session = Session.getDefaultInstance(props, 
		                          new Authenticator(){
		                             protected PasswordAuthentication getPasswordAuthentication() {
		                                return new PasswordAuthentication(username, password);
		                             }});


		// Recipient's email ID needs to be mentioned.
	      String to = "11sergiocf1c@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = email.getEmail();

	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(email.getAsunto());
	         
	         message.setSentDate(new Date());
	         
	         
	         StringBuilder cadena = new StringBuilder();
	         
	         cadena.append("<h2>Nombre: "+email.getNombre()+"</h2>");
	         cadena.append("<h2>Email: "+email.getEmail()+"</h2>");
	         cadena.append("<h4>Teléfono: "+email.getTelefono()+"</h4>");
	         if(email.getCompania()!=null) {
	        	 cadena.append("<h4>Compañia: "+email.getCompania()+"</h4>");	        	 
	         }
	         cadena.append("<h4>Mensaje:</h4>");
	         cadena.append("<p>"+email.getMensaje()+"</p>");
	         // Now set the actual message
	         message.setContent(cadena.toString(),
	                 "text/html");

	         // Send message

				Transport.send(message);
			} catch (MessagingException e) {
				return false;
			}
		
		return true;
	}
	

}
