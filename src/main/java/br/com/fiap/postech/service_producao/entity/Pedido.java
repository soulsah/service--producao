package br.com.fiap.postech.service_producao.entity;


import javax.persistence.*;
import java.util.Date;

import br.com.fiap.postech.service_producao.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pedidos")
public class Pedido {

    @Id
    private String id;

    @NotEmpty(message = "O status do pedido não pode ser vazio.")
    private StatusPedido status;
}
