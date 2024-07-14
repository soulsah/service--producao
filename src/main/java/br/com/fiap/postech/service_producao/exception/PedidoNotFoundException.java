package br.com.fiap.postech.service_producao.exception;

public class PedidoNotFoundException extends Exception{

    public PedidoNotFoundException(){
        super("Pedido nao encontrado");
    }

}
