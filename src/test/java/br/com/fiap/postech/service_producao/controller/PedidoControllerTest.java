package br.com.fiap.postech.service_producao.controller;

import br.com.fiap.postech.service_producao.entity.Pedido;
import br.com.fiap.postech.service_producao.enums.StatusPedido;
import br.com.fiap.postech.service_producao.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_producao.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @Test
    public void testGetPedidoById() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId("1");

        when(pedidoService.getPedidoById("1")).thenReturn(pedido);

        Pedido found = pedidoController.getPedidoById("1");

        assertNotNull(found);
        assertEquals("1", found.getId());
        verify(pedidoService, times(1)).getPedidoById("1");
    }

    @Test
    public void testGetAllPedidos() {
        Pedido pedido1 = new Pedido();
        pedido1.setId("1");
        Pedido pedido2 = new Pedido();
        pedido2.setId("2");

        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

        when(pedidoService.getAllPedidos()).thenReturn(pedidos);

        List<Pedido> found = pedidoController.getAllPedidos();

        assertNotNull(found);
        assertEquals(2, found.size());
        verify(pedidoService, times(1)).getAllPedidos();
    }

    @Test
    public void testRecebePedido() {
        Pedido pedido = new Pedido();
        pedido.setId("1");

        when(pedidoService.receberPedido("1")).thenReturn(pedido);

        Pedido created = pedidoController.recebePedido("1");

        assertNotNull(created);
        assertEquals("1", created.getId());
        verify(pedidoService, times(1)).receberPedido("1");
    }

    @Test
    public void testAtualizaPedido() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();
        pedido.setId("1");
        pedido.setStatus(StatusPedido.EM_PREPARACAO);

        when(pedidoService.atualizarStatusPedido("1", StatusPedido.EM_PREPARACAO)).thenReturn(pedido);

        Pedido updated = pedidoController.atualizaPedido("1", "EM_PREPARACAO");

        assertNotNull(updated);
        assertEquals("1", updated.getId());
        assertEquals(StatusPedido.EM_PREPARACAO, updated.getStatus());
        verify(pedidoService, times(1)).atualizarStatusPedido("1", StatusPedido.EM_PREPARACAO);
    }

    @Test
    public void testAtualizaPedidoComStatusInvalido() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            pedidoController.atualizaPedido("1", "INVALIDO");
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Status inválido!", exception.getReason());
    }

    @Test
    public void testGetPedidoByIdNotFound() throws PedidoNotFoundException {
        when(pedidoService.getPedidoById("9999")).thenThrow(new PedidoNotFoundException());

        PedidoNotFoundException exception = assertThrows(PedidoNotFoundException.class, () -> {
            pedidoController.getPedidoById("9999");
        });

        assertEquals("Pedido nao encontrado", exception.getMessage());
    }
}