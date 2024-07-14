package br.com.fiap.postech.service_producao.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.service_producao.entity.Pedido;
import br.com.fiap.postech.service_producao.enums.StatusPedido;
import br.com.fiap.postech.service_producao.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_producao.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    public void testGetAllPedidos() {
        Pedido pedido1 = new Pedido();
        pedido1.setId("1");
        Pedido pedido2 = new Pedido();
        pedido2.setId("2");

        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido1, pedido2));

        List<Pedido> pedidos = pedidoService.getAllPedidos();

        assertNotNull(pedidos);
        assertEquals(2, pedidos.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    public void testGetPedidoById() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();
        pedido.setId("1");

        when(pedidoRepository.findById("1")).thenReturn(Optional.of(pedido));

        Pedido found = pedidoService.getPedidoById("1");

        assertNotNull(found);
        assertEquals("1", found.getId());
        verify(pedidoRepository, times(1)).findById("1");
    }

    @Test
    public void testGetPedidoByIdNotFound() {
        when(pedidoRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> {
            pedidoService.getPedidoById("999");
        });

        verify(pedidoRepository, times(1)).findById("999");
    }

    @Test
    public void testReceberPedido() {
        Pedido pedido = new Pedido();
        pedido.setId("1");
        pedido.setStatus(StatusPedido.RECEBIDO);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido created = pedidoService.receberPedido("1");

        assertNotNull(created);
        assertEquals("1", created.getId());
        assertEquals(StatusPedido.RECEBIDO, created.getStatus());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void testAtualizarStatusPedido() throws PedidoNotFoundException {
        Pedido pedido = new Pedido();
        pedido.setId("1");
        pedido.setStatus(StatusPedido.EM_PREPARACAO);

        when(pedidoRepository.findById("1")).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido updated = pedidoService.atualizarStatusPedido("1", StatusPedido.EM_PREPARACAO);

        assertNotNull(updated);
        assertEquals("1", updated.getId());
        assertEquals(StatusPedido.EM_PREPARACAO, updated.getStatus());
        verify(pedidoRepository, times(1)).findById("1");
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void testAtualizarStatusPedidoNotFound() {
        when(pedidoRepository.findById("9999")).thenReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> {
            pedidoService.atualizarStatusPedido("9999", StatusPedido.EM_PREPARACAO);
        });

        verify(pedidoRepository, times(1)).findById("9999");
    }
}
