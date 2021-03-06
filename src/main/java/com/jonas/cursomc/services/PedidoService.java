package com.jonas.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonas.cursomc.domain.ItemPedido;
import com.jonas.cursomc.domain.PagamentoComBoleto;
import com.jonas.cursomc.domain.Pedido;
import com.jonas.cursomc.domain.Produto;
import com.jonas.cursomc.domain.enuns.EstadoPagamento;
import com.jonas.cursomc.repositories.ItemPedidoRepository;
import com.jonas.cursomc.repositories.PagamentoRepository;
import com.jonas.cursomc.repositories.PedidoRepository;
import com.jonas.cursomc.repositories.ProdutoRepository;
import com.jonas.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired 
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);			
			Optional<Produto> produto = produtoRepository.findById(ip.getProduto().getId());
			ip.setProduto(produto.get());
			ip.setPreco(produto.get().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		//emailService.sendOrderConfirmationEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

}
