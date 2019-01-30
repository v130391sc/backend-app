package es.upm.fi.catering.service.backendapp.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PedidoClienteSerializer extends StdSerializer<PedidoCliente> {
	
	public PedidoClienteSerializer() {
        this(null);
    }
   
    public PedidoClienteSerializer(Class<PedidoCliente> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      PedidoCliente value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeObjectField("fechaPedido", value.getFechaPedido());
        jgen.writeObjectField("fechaEntrega", value.getFechaEntrega());
        jgen.writeStringField("direccion", value.getDireccion());
        jgen.writeStringField("codigoPostal", value.getCodigoPostal());
        jgen.writeStringField("ciudad", value.getCiudad());
        jgen.writeStringField("provincia", value.getProvincia());
        jgen.writeNumberField("precio", value.getPrecio());
        jgen.writeNumberField("numComensales", value.getNumComensales());
        jgen.writeStringField("tEvento", value.gettEvento());
        jgen.writeStringField("tServicio", value.gettServicio());
        jgen.writeStringField("requisitos", value.getRequisitos());
        jgen.writeStringField("comentarios", value.getComentarios());
        
        Iterator<ProductosPedido> it = value.getListaProductosPedido().iterator(); 
        List<CantidadProductos> listaCantidadProductos = new ArrayList<>();
        
        while(it.hasNext()) {
        	ProductosPedido productosPedido = it.next();
        	CantidadProductos cantidadProductos = new CantidadProductos();
        	cantidadProductos.setProducto(productosPedido.getProducto());
        	cantidadProductos.setCantidad(productosPedido.getCantidad());
        	listaCantidadProductos.add(cantidadProductos);
        }
        jgen.writeObjectField("listaProductosPedido", listaCantidadProductos);
        jgen.writeObjectField("facturaCliente", value.getFacturaCliente());

        
        jgen.writeEndObject();
    }
}
